package me.gulya.bitwarden.server.response

import com.soywiz.klock.DateTime

data class SyncCipherNotification(
    val id: String?,
    val userId: String?,
    val organizationId: String?,
    val collectionIds: Set<String>?,
    var revisionDate: DateTime,
)