package me.gulya.bitwarden.server.response

data class CollectionDetailsResponse(
    val id: String?,
    val organizationId: String?,
    val name: String?,
    val externalId: String?,
    var readOnly: Boolean,
)