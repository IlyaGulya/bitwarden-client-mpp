package me.gulya.bitwarden.server.request

import com.soywiz.klock.DateTime

data class PasswordHistoryRequest(
    var password: String,
    var lastUsedDate: DateTime,
)