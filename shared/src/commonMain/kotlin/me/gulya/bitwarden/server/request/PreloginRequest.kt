package me.gulya.bitwarden.server.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreloginRequest(
    @SerialName("Email") val email: String
)