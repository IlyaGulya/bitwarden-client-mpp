package me.gulya.bitwarden.server

import me.gulya.bitwarden.enums.UriMatchType

data class ServerLoginUri(
    val uri: String?,
    val match: UriMatchType?,
)