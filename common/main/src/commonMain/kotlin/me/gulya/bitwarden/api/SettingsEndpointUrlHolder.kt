package me.gulya.bitwarden.api

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class SettingsEndpointUrlHolder(
    private val endpointSettings: Settings
) : EndpointUrlHolder {

    companion object {
        private const val KEY_ENDPOINT_URL = "endpoint_url"

        const val DEFAULT_ENDPOINT_URL = "https://vault.bitwarden.com"
    }

    override fun getEndpointUrl(): String {
        return endpointSettings[KEY_ENDPOINT_URL] ?: DEFAULT_ENDPOINT_URL
    }

    override fun setEndpointUrl(url: String) {
        endpointSettings[KEY_ENDPOINT_URL] = url
    }

    override fun isEndpointUrlDefault(): Boolean {
        return getEndpointUrl() == DEFAULT_ENDPOINT_URL
    }

}