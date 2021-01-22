package me.gulya.bitwarden.domain.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.data.FolderData

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

}