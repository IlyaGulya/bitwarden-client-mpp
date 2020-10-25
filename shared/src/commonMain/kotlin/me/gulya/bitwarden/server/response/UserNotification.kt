package me.gulya.bitwarden.server.response

import com.soywiz.klock.DateTime

data class UserNotification(
    val userId: String?,
    var date: DateTime,
)