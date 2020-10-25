package me.gulya.bitwarden.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.server.response.FolderResponse

data class FolderData(
    val id: String?,
    val userId: String?,
    val name: String?,
    val revisionDate: DateTime,
) {
    constructor(response: FolderResponse, userId: String?) : this(
        userId = userId,
        id = response.id,
        name = response.name,
        revisionDate = response.revisionDate,
    )
}