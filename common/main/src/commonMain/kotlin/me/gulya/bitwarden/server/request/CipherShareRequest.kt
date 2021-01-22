package me.gulya.bitwarden.server.request

import me.gulya.bitwarden.domain.data.Cipher

class CipherShareRequest(
    val cipher: CipherRequest,
    val collectionIds: List<String>,
) {

    constructor(cipher: Cipher) : this(
        cipher = CipherRequest(cipher),
        collectionIds = cipher.collectionIds.toList(),
    )
}