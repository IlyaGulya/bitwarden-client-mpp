package me.gulya.bitwarden.server.request

import me.gulya.bitwarden.enums.DeviceType

data class DeviceRequest(
    val type: DeviceType,
    val name: String,
    val identifier: String,
    val pushToken: String?,
)