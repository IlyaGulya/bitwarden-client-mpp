package me.gulya.bitwarden.server.request

data class CipherCollectionsRequest(
    val collectionIds: MutableList<String>
)