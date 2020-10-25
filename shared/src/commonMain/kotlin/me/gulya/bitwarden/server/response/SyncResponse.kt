package me.gulya.bitwarden.server.response

data class SyncResponse(
    val profile: ProfileResponse?,
    val folders: List<FolderResponse>,
    val collections: List<CollectionDetailsResponse>,
    val ciphers: List<CipherResponse>,
    val domains: DomainsResponse?,
    val policies: List<PolicyResponse>,
)