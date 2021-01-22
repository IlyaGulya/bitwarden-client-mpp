package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.data.OrganizationData
import me.gulya.bitwarden.enums.OrganizationUserStatusType
import me.gulya.bitwarden.enums.OrganizationUserType

class Organization(
    val id: String?,
    val name: String?,
    val status: OrganizationUserStatusType,
    val type: OrganizationUserType,
    val enabled: Boolean,
    val useGroups: Boolean,
    val useDirectory: Boolean,
    val useEvents: Boolean,
    val useTotp: Boolean,
    val use2fa: Boolean,
    val useApi: Boolean,
    val selfHost: Boolean,
    val usersGetPremium: Boolean,
    val seats: Int,
    val maxCollections: Int,
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