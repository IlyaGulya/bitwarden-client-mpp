package me.gulya.bitwarden.server.request

data class DeviceTokenRequest(
    val pushToken: String
)