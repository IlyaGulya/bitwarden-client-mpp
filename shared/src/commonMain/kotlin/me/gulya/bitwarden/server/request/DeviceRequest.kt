package me.gulya.bitwarden.server.request

import bit.core.abstractions.IPlatformUtilsService
import me.gulya.bitwarden.enums.DeviceType

data class DeviceRequest(
    val type: DeviceType,
    val name: String?,
    val identifier: String?,
    val pushToken: String?,
) {

    constructor(
        appId: String?, platformUtilsService: IPlatformUtilsService
    ) : this(
        type = platformUtilsService.GetDevice(),
        name = platformUtilsService.GetDeviceString(),
        identifier = appId,
        pushToken = null,
    )
}