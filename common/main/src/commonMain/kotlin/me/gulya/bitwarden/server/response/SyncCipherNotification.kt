package me.gulya.bitwarden.server.response

data class SyncCipherNotification(
    val id: String?,
    val userId: String?,
    val organizationId: String?,
    val collectionIds: Set<String>?,
    val revisionDate: DateTimeContainer,
)