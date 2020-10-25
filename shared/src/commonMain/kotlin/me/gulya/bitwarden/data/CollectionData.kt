package me.gulya.bitwarden.data

import me.gulya.bitwarden.server.response.CollectionDetailsResponse

class CollectionData(
    val id: String?,
    val organizationId: String?,
    val name: String?,
    val externalId: String?,
    val readOnly: Boolean = false,
) {
    constructor(response: CollectionDetailsResponse) : this(
        id = response.id,
        organizationId = response.organizationId,
        name = response.name,
        externalId = response.externalId,
        readOnly = response.readOnly,
    )
}