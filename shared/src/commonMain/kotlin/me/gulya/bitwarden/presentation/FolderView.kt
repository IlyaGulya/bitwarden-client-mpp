package me.gulya.bitwarden.presentation

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.domain.data.Folder
import me.gulya.bitwarden.domain.data.TreeNodeValue

class FolderView(
    override val id: String?,
    override val name: String?,
    val revisionDate: DateTime = DateTime.EPOCH,
) : TreeNodeValue {
    constructor(f: Folder) : this(
        id = f.id,
        name = null,
        revisionDate = f.revisionDate,
    )

}