package me.gulya.bitwarden.domain

import me.gulya.bitwarden.data.LoginUriData
import me.gulya.bitwarden.enums.UriMatchType
import me.gulya.bitwarden.presentation.LoginUriView

data class LoginUri(
    val uri: EncryptedString?,
    val match: UriMatchType?,
) {
    constructor(obj: LoginUriData) : this(
        uri = obj.uri.toCipherString(),
        match = obj.match,
    )

    suspend fun decrypt(orgId: String?): LoginUriView {
        return LoginUriView(
            this
        ).also { view ->
            view.uri = uri?.decrypt(orgId)
        }
    }

    fun toLoginUriData(): LoginUriData {
        return LoginUriData(
            uri = uri?.encryptedString,
            match = match,
        )
    }
}