package me.gulya.bitwarden.domain

import me.gulya.bitwarden.data.PolicyData
import me.gulya.bitwarden.enums.PolicyType

data class Policy(
    val id: String?,
    val organizationId: String?,
    val type: PolicyType,
    val data: Map<String, Any>,
    val enabled: Boolean,
) {
    constructor(obj: PolicyData) : this(
        id = obj.id,
        organizationId = obj.organizationId,
        type = obj.type,
        data = obj.data,
        enabled = obj.enabled,
    )
}