package me.gulya.bitwarden.presentation

import me.gulya.bitwarden.domain.data.TreeNodeValue

data class CollectionView(
    override val id: String?,
    val organizationId: String?,
    override val name: String?,
    val externalId: String?,
    val readOnly: Boolean = false,
) : TreeNodeValue