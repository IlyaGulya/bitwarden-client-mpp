package me.gulya.bitwarden.domain.login

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class InMemoryTokenStorage : TokenStorage {

    private var accessToken: String? = null
    private var refreshToken: String? = null

    override fun saveTokens(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

    override fun getAccessToken(): String? = accessToken

    override fun getRefreshToken(): String? = refreshToken

}

class SettingsTokenStorage(
    private val settings: Settings
) : TokenStorage {

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    override fun saveTokens(accessToken: String, refreshToken: String) {
        settings[KEY_ACCESS_TOKEN] = accessToken
        settings[KEY_REFRESH_TOKEN] = refreshToken
    }

    override fun getAccessToken(): String? {
        return settings[KEY_ACCESS_TOKEN]
    }

    override fun getRefreshToken(): String? {
        return settings[KEY_REFRESH_TOKEN]
    }

}