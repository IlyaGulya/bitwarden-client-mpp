package me.gulya.bitwarden.api

interface EndpointUrlHolder {
    fun getEndpointUrl(): String
    fun setEndpointUrl(url: String)
    fun isEndpointUrlDefault(): Boolean
}