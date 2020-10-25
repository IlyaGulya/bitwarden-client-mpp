package me.gulya.bitwarden.server.response

import me.gulya.bitwarden.enums.PolicyType

data class PolicyResponse(
    val id: String,
    val organizationId: String?,
    val type: PolicyType,
    val data: Map<String, Any>,
    val enabled: Boolean,
)