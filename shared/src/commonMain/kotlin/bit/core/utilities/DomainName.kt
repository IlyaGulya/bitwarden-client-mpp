package bit.core.utilities

// ref: https://github.com/danesparza/domainname-parser
class DomainName(tld: String, sld: String, subDdomain: String, tldRule: TLDRule?) {
    private var subDomain = ""
    private var domain = ""
    private var tld = ""
    private var tldRule: TLDRule? = null

    fun getDomain(): String {
        return domain
    }

    fun getSld(): String {
        return domain
    }

    fun getTld(): String {
        return tld
    }

    fun getRule(): TLDRule? {
        return tldRule
    }

    fun getBaseDomain(): String {
        return "$domain.$tld"
    }

    class TLDRule(ruleInfo: String) : Comparable<TLDRule> {
        var name: String? = null
        fun getName(): String? {
            return name
        }

        private var Type = RuleType.values()[0]
        fun getType(): RuleType {
            return Type
        }

        private fun setType(value: RuleType) {
            Type = value
        }

        override operator fun compareTo(other: TLDRule): Int {
            return getName()!!.compareTo(other.getName()!!)
        }

        enum class RuleType {
            Normal, Wildcard, Exception;

            companion object {
                fun forValue(value: Int): RuleType {
                    return values()[value]
                }
            }
        }

        init {
            //  Parse the rule and set properties accordingly:
            if (ruleInfo.startsWith("*")) {
                setType(RuleType.Wildcard)
                name = ruleInfo.substring(2)
            } else if (ruleInfo.startsWith("!")) {
                setType(RuleType.Exception)
                name = ruleInfo.substring(1)
            } else {
                setType(RuleType.Normal)
                name = ruleInfo
            }
        }
    }

    private class TLDRulesCache private constructor() {
        private var _lstTLDRules: Map<TLDRule.RuleType, Map<String, TLDRule>>
        fun getTLDRuleLists(): Map<TLDRule.RuleType, Map<String, TLDRule>> {
            return _lstTLDRules
        }

        private fun getTLDRules(): Map<TLDRule.RuleType, Map<String, TLDRule>> {
//            if (CoreHelpers.InDebugMode()) {
//                Debug.WriteLine(java.lang.String.format("Loaded %1\$s rules into cache.", results.values.Sum { r -> r.Values.Count }))
//            }
            return PUBLIC_SUFFIX_LIST
                //  Strip out comments and blank lines
                .filter { ruleString -> !ruleString.startsWith("//") && ruleString.isNotBlank() }
                .map { TLDRule(it) }
                .groupBy { it.getType() }
                .mapValues { (_, value) ->
                    value.associateBy { it.name ?: "" }
                }
        }

        companion object {
            private var _uniqueInstance: TLDRulesCache? = null
            private val _syncObj = Any()
            private val _syncList = Any()
            fun getInstance(): TLDRulesCache? {
                if (_uniqueInstance == null) {
//                    synchronized(_syncObj) {
//                        if (_uniqueInstance == null) {
//                            _uniqueInstance = TLDRulesCache()
//                        }
//                    }
                    _uniqueInstance = TLDRulesCache()
                }
                return _uniqueInstance
            }

            fun reset() {
//                synchronized(_syncObj) { _uniqueInstance = null }
                _uniqueInstance = null
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
            return tryParse(domainString)?.getBaseDomain()
        }

        private fun parseDomainName(
            domainString: String,
        ): DomainName {
            // Make sure domain is all lowercase
            val domainString = domainString.toLowerCase()
            var tld = ""
            var sld = ""
            var subDomain = ""
            val matchingRule: TLDRule?

            //  If the fqdn is empty, we have a problem already
            if (domainString.trim { it <= ' ' } == "") {
                throw IllegalArgumentException("The domain cannot be blank")
            }

            //  Next, find the matching rule:
            matchingRule = findMatchingTLDRule(domainString)

            //  At this point, no rules match, we have a problem
            if (matchingRule == null) {
                throw NumberFormatException("The domain does not have a recognized TLD")
            }

            //  Based on the tld rule found, get the domain (and possibly the subdomain)
            var tempSudomainAndDomain = ""
            var tldIndex: Int
            when (matchingRule.getType()) {
                TLDRule.RuleType.Normal -> {
                    tldIndex = domainString.lastIndexOf("." + matchingRule.getName())
                    tempSudomainAndDomain = domainString.substring(0, tldIndex)
                    tld = domainString.substring(tldIndex + 1)
                }
                TLDRule.RuleType.Wildcard -> {
                    //  This finds the last portion of the TLD...
                    tldIndex = domainString.lastIndexOf("." + matchingRule.getName())
                    tempSudomainAndDomain = domainString.substring(0, tldIndex)

                    //  But we need to find the wildcard portion of it:
                    tldIndex = tempSudomainAndDomain.lastIndexOf(".")
                    tempSudomainAndDomain = domainString.substring(0, tldIndex)
                    tld = domainString.substring(tldIndex + 1)
                }
                TLDRule.RuleType.Exception -> {
                    tldIndex = domainString.lastIndexOf(".")
                    tempSudomainAndDomain = domainString.substring(0, tldIndex)
                    tld = domainString.substring(tldIndex + 1)
                }
            }

            //  See if we have a subdomain:
            val lstRemainingParts = tempSudomainAndDomain.split("[.]").toList()

            //  If we have 0 parts left, there is just a tld and no domain or subdomain
            //  If we have 1 part, it's the domain, and there is no subdomain
            //  If we have 2+ parts, the last part is the domain, the other parts (combined) are the subdomain
            if (lstRemainingParts.isNotEmpty()) {
                //  Set the domain:
                sld = lstRemainingParts.get(lstRemainingParts.size - 1)

                //  Set the subdomain, if there is one to set:
                if (lstRemainingParts.size > 1) {
                    //  We strip off the trailing period, too
                    subDomain = tempSudomainAndDomain.substring(0, tempSudomainAndDomain.length - sld.length - 1)
                }
            }
            return DomainName(
                tld = tld,
                sld = sld,
                subDdomain = subDomain,
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
                    val result = TLDRulesCache.getInstance()?.getTLDRuleLists()?.get(rule)?.get(checkAgainst)
                    if (result != null) {
                        ruleMatches.add(result)
                    }
                    //Debug.WriteLine(string.Format("Domain part {0} matched {1} {2} rules",
                    //    checkAgainst, result == null ? 0 : 1, rule));
                }
            }

            //  Sort our matches list (longest rule wins, according to :
            val results = ruleMatches.sortedBy { it.getName()?.length }

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

    init {
        this.tld = tld
        domain = sld
        subDomain = subDdomain
        this.tldRule = tldRule
    }
}