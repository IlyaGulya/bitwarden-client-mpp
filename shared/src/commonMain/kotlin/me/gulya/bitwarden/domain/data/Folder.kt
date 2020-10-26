package me.gulya.bitwarden.domain.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.data.FolderData
import me.gulya.bitwarden.presentation.FolderView

class Folder(
    val id: String? = null,
    val name: EncryptedString? = null,
    val revisionDate: DateTime = DateTime.EPOCH,
) {

    constructor(obj: FolderData) : this(
        id = obj.id,
        name = obj.name.toCipherString(),
        revisionDate = obj.revisionDate,
    )

    suspend fun decryptAsync(): FolderView {
        return FolderView(
            id = id,
            name = name?.decrypt(),
            revisionDate = revisionDate,
        )
    }
}