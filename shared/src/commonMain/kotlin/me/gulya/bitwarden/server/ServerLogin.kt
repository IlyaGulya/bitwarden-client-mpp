package me.gulya.bitwarden.server

import com.soywiz.klock.DateTime

data class ServerLogin(
    val username: String?,
    val password: String?,
    val passwordRevisionDate: DateTime?,
    val totp: String?,
    val uris: List<ServerLoginUri>,
)