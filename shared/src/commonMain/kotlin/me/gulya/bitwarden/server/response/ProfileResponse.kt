package me.gulya.bitwarden.server.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("Id") val id: String?,
    @SerialName("Name") val name: String?,
    @SerialName("Email") val email: String?,
    @SerialName("EmailVerified") val emailVerified: Boolean,
    @SerialName("Premium") val premium: Boolean,
    @SerialName("MasterPasswordHint") val masterPasswordHint: String?,
    @SerialName("Culture") val culture: String?,
    @SerialName("TwoFactorEnabled") var twoFactorEnabled: Boolean,
    @SerialName("Key") val key: String?,
    @SerialName("PrivateKey") val privateKey: String?,
    @SerialName("SecurityStamp") val securityStamp: String?,
    @SerialName("Organizations") val organizations: List<ProfileOrganizationResponse>?,
)