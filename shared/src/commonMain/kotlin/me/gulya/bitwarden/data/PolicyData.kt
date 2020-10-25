package me.gulya.bitwarden.data

import me.gulya.bitwarden.enums.PolicyType
import me.gulya.bitwarden.server.response.PolicyResponse

data class PolicyData(
    var id: String,
    var organizationId: String?,
    var type: PolicyType,
    var data: Map<String, Any>,
    var enabled: Boolean = false,
) {
    constructor(response: PolicyResponse) : this(
        id = response.id,
        organizationId = response.organizationId,
        type = response.type,
        data = response.data,
        enabled = response.enabled,
    )


}