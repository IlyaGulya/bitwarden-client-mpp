package me.gulya.bitwarden.server.response

import com.soywiz.klock.DateTime

class PasswordHistoryResponse(
    val password: String,
    val lastUsedDate: DateTime,
)