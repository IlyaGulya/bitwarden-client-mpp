package me.gulya.bitwarden.presentation

import com.soywiz.klock.DateTime

data class PasswordHistoryView(
    val password: String?,
    val lastUsedDate: DateTime,
)