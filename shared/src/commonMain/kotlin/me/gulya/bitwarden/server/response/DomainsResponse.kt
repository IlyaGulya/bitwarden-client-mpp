package me.gulya.bitwarden.server.response

data class DomainsResponse(
    val equivalentDomains: List<List<String>>,
    val globalEquivalentDomains: List<GlobalDomainResponse>
)