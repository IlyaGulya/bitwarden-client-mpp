package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.data.CollectionData
import me.gulya.bitwarden.presentation.CollectionView

class Collection(
    val id: String? = null,
    val organizationId: String? = null,
    val name: EncryptedString? = null,
    val externalId: String? = null,
    val readOnly: Boolean = false,
) {
    constructor(obj: CollectionData) : this(
        id = obj.id,
        organizationId = obj.organizationId,
        name = obj.name.toCipherString(),
        externalId = obj.externalId,
        readOnly = obj.readOnly
    )

    suspend fun decrypt(): CollectionView {
        return CollectionView(
            id = id,
            name = name?.decrypt(organizationId),
            organizationId = organizationId,
            externalId = externalId,
            readOnly = readOnly,
        )
    }
}