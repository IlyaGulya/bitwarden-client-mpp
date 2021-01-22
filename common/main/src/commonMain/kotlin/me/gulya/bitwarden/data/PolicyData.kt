package me.gulya.bitwarden.data

import me.gulya.bitwarden.enums.PolicyType
import me.gulya.bitwarden.server.response.PolicyResponse

data class PolicyData(
    val id: String,
    val organizationId: String?,
    val type: PolicyType,
    val data: Map<String, Any>,
    val enabled: Boolean = false,
) {
    constructor(response: PolicyResponse) : this(
        id = response.id,
        organizationId = response.organizationId,
        type = response.type,
        data = response.data,
        enabled = response.enabled,
    )


}