package me.gulya.bitwarden.server.request

import me.gulya.bitwarden.enums.TwoFactorProviderType

class TokenRequest constructor(
    val email: String?,
    val masterPasswordHash: String?,
    val code: String?,
    val codeVerifier: String?,
    val redirectUri: String?,
    val token: String?,
    val provider: TwoFactorProviderType?,
    val remember: Boolean?,
    val device: DeviceRequest?,
) {

    fun toIdentityToken(clientId: String): Map<String, String> {
        val obj: MutableMap<String, String> = mutableMapOf(
            "scope" to "api offline_access",
            "client_id" to clientId,
        )
        if (masterPasswordHash != null && email != null) {
            obj += mapOf(
                "grant_type" to "password",
                "username" to email,
                "password" to masterPasswordHash,
            )
        } else if (code != null && codeVerifier != null && redirectUri != null) {
            obj += mapOf(
                "grant_type" to "authorization_code",
                "code" to code,
                "code_verifier" to codeVerifier,
                "redirect_uri" to redirectUri,
            )
        } else {
            throw RuntimeException("must provide credentials or codes")
        }
        if (device != null) {
            obj += mapOf(
                "deviceType" to (device.type.value).toString(),
                "deviceIdentifier" to device.identifier,
                "deviceName" to device.name,
            )
            if (device.pushToken != null) {
                obj += "devicePushToken" to device.pushToken
            }
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

    companion object {
        operator fun invoke(
            credentials: List<String>,
            codes: List<String>,
            provider: TwoFactorProviderType?,
            token: String?,
            remember: Boolean?,
            device: DeviceRequest? = null
        ): TokenRequest {
            var email: String? = null
            var masterPasswordHash: String? = null
            var code: String? = null
            var codeVerifier: String? = null
            var redirectUri: String? = null
            if (credentials.size > 1) {
                email = credentials[0]
                masterPasswordHash = credentials[1]
            } else if (codes.size > 2) {
                code = codes[0]
                codeVerifier = codes[1]
                redirectUri = codes[2]
            }

            return TokenRequest(
                email = email,
                masterPasswordHash = masterPasswordHash,
                code = code,
                codeVerifier = codeVerifier,
                redirectUri = redirectUri,
                token = token,
                provider = provider,
                remember = remember,
                device = device,
            )
        }
    }

}