package me.gulya.bitwarden.app.common.login.store

import com.arkivanov.mvikotlin.core.store.Store
import me.gulya.bitwarden.app.common.login.store.BitwardenLoginStore.*

interface BitwardenLoginStore : Store<Intent, State, Label> {

    sealed class Intent {
        data class EmailChanged(val email: String) : Intent()
        data class PasswordChanged(val password: String) : Intent()
        data class CustomServerCheckedChanged(val checked: Boolean) : Intent()
        data class CustomServerAddressChanged(val serverAddress: String) : Intent()
        object Login : Intent()
    }

    data class State(
        val email: String,
        val password: String,
        val serverConfig: ServerConfig,
        val loading: Boolean,
        val error: String?,
    )

    sealed class ServerConfig {
        object Default : ServerConfig()
        data class Custom(
            val serverAddress: String,
        ) : ServerConfig()
    }

    sealed class Label

}