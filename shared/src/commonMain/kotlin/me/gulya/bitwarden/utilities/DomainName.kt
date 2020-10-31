package me.gulya.bitwarden.utilities

expect fun publicSuffixList(): List<String>

// ref: https://github.com/danesparza/domainname-parser
class DomainName(
    val tld: String,
    val sld: String,
    val subDomain: String,
    val tldRule: TLDRule?
) {
    val domain = sld

    val baseDomain by lazy {
        "$domain.$tld"
    }

    class TLDRule(
        var name: String,
        var type: RuleType,
    ) : Comparable<TLDRule> {

        override operator fun compareTo(other: TLDRule): Int {
            return name.compareTo(other.name)
        }

        enum class RuleType {
            Normal,
            Wildcard,
            Exception,
        }

        companion object {
            operator fun invoke(ruleInfo: String): TLDRule {
                //  Parse the rule and set properties accordingly:
                return when {
                    ruleInfo.startsWith("*") -> TLDRule(
                        type = RuleType.Wildcard,
                        name = ruleInfo.substring(2),
                    )
                    ruleInfo.startsWith("!") -> TLDRule(
                        type = RuleType.Exception,
                        name = ruleInfo.substring(1),
                    )
                    else -> TLDRule(
                        type = RuleType.Normal,
                        name = ruleInfo,
                    )
                }
            }
        }

    }

    private class TLDRulesCache private constructor() {
        private var _lstTLDRules: Map<TLDRule.RuleType, Map<String, TLDRule>>
        fun getTLDRuleLists(): Map<TLDRule.RuleType, Map<String, TLDRule>> {
            return _lstTLDRules
        }

        private fun getTLDRules(): Map<TLDRule.RuleType, Map<String, TLDRule>> {
            return publicSuffixList()
                //  Strip out comments and blank lines
                .filter { ruleString -> !ruleString.startsWith("//") && ruleString.isNotBlank() }
                .map { TLDRule(it) }
                .groupBy { it.type }
                .mapValues { (_, value) ->
                    value.associateBy { it.name }
                }
        }

        companion object {
            val instance: TLDRulesCache by lazy {
                TLDRulesCache()
            }
        }

        init {
            //  Initialize our internal list:
            _lstTLDRules = getTLDRules()
        }
    }

    companion object {
        fun tryParse(domainString: String): DomainName? {
            //  Our temporary domain parts:
            return try {
                //  Try parsing the domain name ... this might throw formatting exceptions
                parseDomainName(domainString)
            } catch (e: Exception) {
                //  Looks like something bad happened -- return 'null'
                null
            }
        }

        fun tryParseBaseDomain(domainString: String): String? {
            return tryParse(domainString)?.baseDomain
        }

        private fun parseDomainName(
            domainString: String,
        ): DomainName {
            // Make sure domain is all lowercase
            val lowercaseDomainString = domainString.toLowerCase()
            var tld = ""
            var sld = ""
            var subDomain = ""
            val matchingRule: TLDRule?

            //  If the fqdn is empty, we have a problem already
            if (lowercaseDomainString.trim { it <= ' ' } == "") {
                throw IllegalArgumentException("The domain cannot be blank")
            }

            //  Next, find the matching rule:
            matchingRule = findMatchingTLDRule(lowercaseDomainString)

            //  At this point, no rules match, we have a problem
            if (matchingRule == null) {
                throw NumberFormatException("The domain does not have a recognized TLD")
            }

            //  Based on the tld rule found, get the domain (and possibly the subdomain)
            var tempSudomainAndDomain = ""
            var tldIndex: Int
            when (matchingRule.type) {
                TLDRule.RuleType.Normal -> {
                    tldIndex = lowercaseDomainString.lastIndexOf("." + matchingRule.name)
                    tempSudomainAndDomain = lowercaseDomainString.substring(0, tldIndex)
                    tld = lowercaseDomainString.substring(tldIndex + 1)
                }
                TLDRule.RuleType.Wildcard -> {
                    //  This finds the last portion of the TLD...
                    tldIndex = lowercaseDomainString.lastIndexOf("." + matchingRule.name)
                    tempSudomainAndDomain = lowercaseDomainString.substring(0, tldIndex)

                    //  But we need to find the wildcard portion of it:
                    tldIndex = tempSudomainAndDomain.lastIndexOf(".")
                    tempSudomainAndDomain = lowercaseDomainString.substring(0, tldIndex)
                    tld = lowercaseDomainString.substring(tldIndex + 1)
                }
                TLDRule.RuleType.Exception -> {
                    tldIndex = lowercaseDomainString.lastIndexOf(".")
                    tempSudomainAndDomain = lowercaseDomainString.substring(0, tldIndex)
                    tld = lowercaseDomainString.substring(tldIndex + 1)
                }
            }

            //  See if we have a subdomain:
            val lstRemainingParts = tempSudomainAndDomain.split("[.]").toList()

            //  If we have 0 parts left, there is just a tld and no domain or subdomain
            //  If we have 1 part, it's the domain, and there is no subdomain
            //  If we have 2+ parts, the last part is the domain, the other parts (combined) are the subdomain
            if (lstRemainingParts.isNotEmpty()) {
                //  Set the domain:
                sld = lstRemainingParts[lstRemainingParts.size - 1]

                //  Set the subdomain, if there is one to set:
                if (lstRemainingParts.size > 1) {
                    //  We strip off the trailing period, too
                    subDomain = tempSudomainAndDomain.substring(0, tempSudomainAndDomain.length - sld.length - 1)
                }
            }
            return DomainName(
                tld = tld,
                sld = sld,
                subDomain = subDomain,
                tldRule = matchingRule
            )
        }

        private fun findMatchingTLDRule(domainString: String): TLDRule? {
            //  Split our domain into parts (based on the '.')
            //  ...Put these parts in a list
            //  ...Make sure these parts are in reverse order (we'll be checking rules from the
            // right -most pat of the domain)
            val lstDomainParts = domainString.split("[.]").toList().asReversed()

            //  Begin building our partial domain to check rules with:
            var checkAgainst = ""

            //  Our 'matches' collection:
            val ruleMatches: MutableList<TLDRule> = mutableListOf()
            for (domainPart in lstDomainParts) {
                //  Add on our next domain part:
                checkAgainst = "$domainPart.$checkAgainst"

                //  If we end in a period, strip it off:
                if (checkAgainst.endsWith(".")) {
                    checkAgainst = checkAgainst.substring(0, checkAgainst.length - 1)
                }

                val rules = TLDRule.RuleType.values()
                for (rule in rules) {
                    //  Try to match rule:
                    val result = TLDRulesCache.instance.getTLDRuleLists()[rule]?.get(checkAgainst)
                    if (result != null) {
                        ruleMatches.add(result)
                    }
                    //Debug.WriteLine(string.Format("Domain part {0} matched {1} {2} rules",
                    //    checkAgainst, result == null ? 0 : 1, rule));
                }
            }

            //  Sort our matches list (longest rule wins, according to :
            val results = ruleMatches.sortedBy { it.name.length }

            //  Take the top result (our primary match):
            val primaryMatch = results.firstOrNull()
            if (primaryMatch != null) {
                //Debug.WriteLine(string.Format("Looks like our match is: {0}, which is a(n) {1} rule.",
                //    primaryMatch.Name, primaryMatch.Type));
            } else {
                //Debug.WriteLine(string.Format("No rules matched domain: {0}", domainString));
            }
            return primaryMatch
        }
    }
}