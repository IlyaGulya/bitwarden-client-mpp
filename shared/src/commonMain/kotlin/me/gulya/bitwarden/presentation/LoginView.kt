package me.gulya.bitwarden.presentation

import com.soywiz.klock.DateTime

data class LoginView(
    val username: String?,
    val password: String?,
    val passwordRevisionDate: DateTime?,
    val totp: String?,
    val uris: List<LoginUriView>,
) : View() {

    val uri: String?
        get() = if (hasUris) uris[0].uri else null
    val maskedPassword: String?
        get() = if (password != null) "••••••••" else null
    val subTitle: String?
        get() = username
    val canLaunch: Boolean
        get() = hasUris && uris.any { u -> u.canLaunch }
    val launchUri: String?
        get() = if (hasUris) {
            uris.firstOrNull { it.canLaunch }?.launchUri
        } else {
            null
        }
    private val hasUris: Boolean
        get() = uris.isNotEmpty()
}