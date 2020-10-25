package me.gulya.bitwarden.server.response

import com.soywiz.klock.DateTime

data class FolderResponse(
    val id: String,
    val name: String,
    val revisionDate: DateTime
)