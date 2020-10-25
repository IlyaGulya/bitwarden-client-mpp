package me.gulya.bitwarden.server.response

import com.soywiz.klock.DateTime

data class SyncFolderNotification(
    val id: String?,
    val userId: String?,
    val revisionDate: DateTime,
)