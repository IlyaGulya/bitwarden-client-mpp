package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.data.OrganizationData
import me.gulya.bitwarden.enums.OrganizationUserStatusType
import me.gulya.bitwarden.enums.OrganizationUserType

class Organization(
    var id: String?,
    var name: String?,
    var status: OrganizationUserStatusType,
    var type: OrganizationUserType,
    var enabled: Boolean,
    var useGroups: Boolean,
    var useDirectory: Boolean,
    var useEvents: Boolean,
    var useTotp: Boolean,
    var use2fa: Boolean,
    var useApi: Boolean,
    var selfHost: Boolean,
    var usersGetPremium: Boolean,
    var seats: Int,
    var maxCollections: Int,
    val maxStorageGb: Short?,
) {
    constructor(obj: OrganizationData) : this(
        id = obj.id,
        name = obj.name,
        status = obj.status,
        type = obj.type,
        enabled = obj.enabled,
        useGroups = obj.useGroups,
        useDirectory = obj.useDirectory,
        useEvents = obj.useEvents,
        useTotp = obj.useTotp,
        use2fa = obj.use2fa,
        useApi = obj.useApi,
        selfHost = obj.selfHost,
        usersGetPremium = obj.usersGetPremium,
        seats = obj.seats,
        maxCollections = obj.maxCollections,
        maxStorageGb = obj.maxStorageGb,
    )
}