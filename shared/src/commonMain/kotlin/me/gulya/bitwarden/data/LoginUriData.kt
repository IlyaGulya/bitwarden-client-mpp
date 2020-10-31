package me.gulya.bitwarden.data

import me.gulya.bitwarden.enums.UriMatchType
import me.gulya.bitwarden.server.ServerLoginUri

class LoginUriData(
    val uri: String,
    val matchType: UriMatchType,
) {
    constructor(data: ServerLoginUri) : this(
        uri = data.uri,
        matchType = data.match,
    )

}