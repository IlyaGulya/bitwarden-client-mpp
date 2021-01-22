package me.gulya.bitwarden.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.server.ServerLogin

data class LoginData(
    val username: String?,
    val password: String?,
    val passwordRevisionDate: DateTime?,
    val totp: String?,
    val uris: List<LoginUriData>,
) {
    constructor(data: ServerLogin) : this(
        username = data.username,
        password = data.password,
        passwordRevisionDate = data.passwordRevisionDate?.dateTime,
        totp = data.totp,
        uris = (data.uris ?: emptyList()).map { u -> LoginUriData(u) },
    )

}