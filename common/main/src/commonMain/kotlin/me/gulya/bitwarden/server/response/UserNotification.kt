package me.gulya.bitwarden.server.response

data class UserNotification(
    val userId: String?,
    val date: DateTimeContainer,
)