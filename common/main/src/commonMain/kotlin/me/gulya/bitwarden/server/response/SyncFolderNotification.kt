package me.gulya.bitwarden.server.response

data class SyncFolderNotification(
    val id: String?,
    val userId: String?,
    val revisionDate: DateTimeContainer,
)