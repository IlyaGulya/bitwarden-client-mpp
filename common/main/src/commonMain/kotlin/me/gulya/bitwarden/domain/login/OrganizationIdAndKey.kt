package me.gulya.bitwarden.domain.login

import me.gulya.bitwarden.domain.data.crypto.OrganizationKey

data class OrganizationIdAndKey(
    val id: String,
    val key: OrganizationKey
)