package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.data.LoginUriData
import me.gulya.bitwarden.enums.UriMatchType

data class LoginUri(
    val uri: EncryptedString,
    val matchType: UriMatchType,
) {
    constructor(obj: LoginUriData) : this(
        uri = obj.uri.toCipherString(),
        matchType = obj.matchType,
    )

    fun toLoginUriData(): LoginUriData {
        return LoginUriData(
            uri = uri.encryptedString,
            matchType = matchType,
        )
    }
}