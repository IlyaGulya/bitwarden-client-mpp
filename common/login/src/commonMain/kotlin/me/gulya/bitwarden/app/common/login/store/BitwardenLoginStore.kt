package me.gulya.bitwarden.app.common.login.store

import com.arkivanov.mvikotlin.core.store.Store
import me.gulya.bitwarden.app.common.login.store.BitwardenLoginStore.*

interface BitwardenLoginStore : Store<Intent, State, Label> {

    sealed class Intent {
        data class SetLogin(val login: String) : Intent()
        data class SetPassword(val password: String) : Intent()
        data class ChangeServerSettingsVisible(val visible: Boolean) : Intent()
        data class ChangeServerAddress(val serverAddress: String) : Intent()
        object Login : Intent()
    }

    data class State(
        val email: String,
        val password: String,
        val serverConfig: ServerConfig,
        val loading: Boolean,
    )

    sealed class ServerConfig {
        object Default : ServerConfig()
        data class Custom(
            val serverAddress: String,
        ) : ServerConfig()
    }

    sealed class Label

}