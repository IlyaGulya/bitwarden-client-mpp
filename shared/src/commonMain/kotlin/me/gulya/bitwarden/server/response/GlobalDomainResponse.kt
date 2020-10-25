package me.gulya.bitwarden.server.response

class GlobalDomainResponse(
    val type: Int,
    val domains: List<String>?,
    val excluded: Boolean,
)