package me.gulya.bitwarden.server.request

import me.gulya.bitwarden.enums.TwoFactorProviderType

class TokenRequest constructor(
    var email: String?,
    var masterPasswordHash: String?,
    var code: String?,
    var codeVerifier: String?,
    var redirectUri: String?,
    val token: String?,
    val provider: TwoFactorProviderType?,
    val remember: Boolean?,
    val device: DeviceRequest?,
) {

    constructor(
        credentials: List<String>,
        codes: List<String>,
        provider: TwoFactorProviderType?,
        token: String?,
        remember: Boolean?,
        device: DeviceRequest? = null
    ) : this(
        email = null,
        masterPasswordHash = null,
        code = null,
        codeVerifier = null,
        redirectUri = null,
        token = token,
        provider = provider,
        remember = remember,
        device = device,
    ) {
        if (credentials.size > 1) {
            email = credentials[0]
            masterPasswordHash = credentials[1]
        } else if (codes.size > 2) {
            code = codes[0]
            codeVerifier = codes[1]
            redirectUri = codes[2]
        }
    }

    fun ToIdentityToken(clientId: String): Map<String, String> {
        val obj: MutableMap<String, String> = mutableMapOf(
            "scope" to "api offline_access",
            "client_id" to clientId,
        )
        if (masterPasswordHash != null && email != null) {
            obj += mapOf(
                "grant_type" to "password",
                "username" to email!!,
                "password" to masterPasswordHash!!,
            )
        } else if (code != null && codeVerifier != null && redirectUri != null) {
            obj += mapOf(
                "grant_type" to "authorization_code",
                "code" to code!!,
                "code_verifier" to codeVerifier!!,
                "redirect_uri" to redirectUri!!,
            )
        } else {
            throw RuntimeException("must provide credentials or codes")
        }
        if (device != null) {
            obj += mapOf(
                "deviceType" to (device.type.value).toString(),
                "deviceIdentifier" to device.identifier!!,
                "deviceName" to device.name!!,
                "devicePushToken" to device.pushToken!!,
            )
        }
        if (!token.isNullOrBlank() && provider != null && remember != null) {
            obj += mapOf(
                "twoFactorToken" to token,
                "twoFactorProvider" to (provider.value).toString(),
                "twoFactorRemember" to if (remember == true) "1" else "0",
            )
        }
        return obj
    }

}