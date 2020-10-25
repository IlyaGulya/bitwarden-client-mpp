package me.gulya.bitwarden.server.response

import me.gulya.bitwarden.enums.NotificationType

class NotificationResponse(
    val contextId: String?,
    val type: NotificationType,
    val payload: String?,
    val payloadObject: Any?,
)