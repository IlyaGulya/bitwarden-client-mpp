package me.gulya.bitwarden.server.response

data class ProfileResponse(
    val id: String?,
    val name: String?,
    val email: String?,
    val emailVerified: Boolean,
    val premium: Boolean,
    val masterPasswordHint: String?,
    val culture: String?,
    var twoFactorEnabled: Boolean,
    val key: String?,
    val privateKey: String?,
    val securityStamp: String?,
    val organizations: List<ProfileOrganizationResponse>?,
)