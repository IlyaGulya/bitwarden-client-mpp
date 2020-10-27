package me.gulya.bitwarden.domain.login

class InMemoryTokenStorage() : TokenStorage {

    private var accessToken: String? = null
    private var refreshToken: String? = null

    override fun saveTokens(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

    override fun getAccessToken(): String? = accessToken

    override fun getRefreshToken(): String? = refreshToken

}