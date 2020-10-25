package me.gulya.bitwarden.data

import me.gulya.bitwarden.enums.UriMatchType
import me.gulya.bitwarden.server.ServerLoginUri

class LoginUriData(
    val uri: String?,
    val match: UriMatchType?,
) {
    constructor(data: ServerLoginUri) : this(
        uri = data.uri,
        match = data.match,
    )

}