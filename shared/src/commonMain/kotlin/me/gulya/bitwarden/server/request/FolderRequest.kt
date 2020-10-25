package me.gulya.bitwarden.server.request

import me.gulya.bitwarden.domain.Folder

class FolderRequest(
    val name: String?
) {

    constructor(folder: Folder) : this(
        name = folder.name?.encryptedString
    )
}