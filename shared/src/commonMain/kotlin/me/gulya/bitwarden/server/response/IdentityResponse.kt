package me.gulya.bitwarden.server.response

sealed class IdentityResponse {
    data class Token(
        val identityTokenResponse: IdentityTokenResponse
    ): IdentityResponse()

    data class TwoFactor(
        val twoFactorResponse: IdentityTwoFactorResponse
    ): IdentityResponse()
}