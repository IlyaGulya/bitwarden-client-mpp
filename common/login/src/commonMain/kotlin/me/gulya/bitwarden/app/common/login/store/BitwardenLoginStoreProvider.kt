package me.gulya.bitwarden.app.common.login.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import kotlinx.coroutines.withContext
import me.gulya.bitwarden.app.common.login.store.BitwardenLoginStore.Intent.*
import me.gulya.bitwarden.domain.login.LoginInteractor
import kotlin.coroutines.CoroutineContext

internal class BitwardenLoginStoreProvider(
    private val storeFactory: StoreFactory,
    private val loginInteractorFactory: (serverUrl: String) -> LoginInteractor,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
) {

    private sealed class Result {
        data class EmailChanged(val email: String) : Result()
        data class PasswordChanged(val password: String) : Result()
        data class ServerConfigChanged(val serverConfig: BitwardenLoginStore.ServerConfig) : Result()
    }


    fun provide(): BitwardenLoginStore =
        object : BitwardenLoginStore,
            Store<BitwardenLoginStore.Intent, BitwardenLoginStore.State, BitwardenLoginStore.Label> by storeFactory.create(
                name = "BitwardenLoginStore",
                initialState = BitwardenLoginStore.State(
                    email = "",
                    password = "",
                    serverConfig = BitwardenLoginStore.ServerConfig.Default,
                    loading = false,
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            )

    private inner class ExecutorImpl :
        SuspendExecutor<BitwardenLoginStore.Intent, Unit, BitwardenLoginStore.State, Result, BitwardenLoginStore.Label>() {

        override suspend fun executeIntent(
            intent: BitwardenLoginStore.Intent,
            getState: () -> BitwardenLoginStore.State
        ) {
            when (intent) {
                is SetLogin -> setLogin(intent.login)
                is SetPassword -> setPassword(intent.password)
                is ChangeServerSettingsVisible -> changeServerSettingsVisible(intent.visible)
                is ChangeServerAddress -> changeServerAddress(intent.serverAddress)
                is Login -> login(getState())
            }
        }

        private fun setLogin(login: String) {
            dispatch(Result.EmailChanged(login))
        }

        private fun setPassword(password: String) {
            dispatch(Result.PasswordChanged(password))
        }

        private fun changeServerSettingsVisible(visible: Boolean) {
            dispatch(
                if (visible) {
                    Result.ServerConfigChanged(
                        BitwardenLoginStore.ServerConfig.Custom(
                            serverAddress = ""
                        )
                    )
                } else {
                    Result.ServerConfigChanged(BitwardenLoginStore.ServerConfig.Default)
                }
            )
        }

        private fun changeServerAddress(serverAddress: String) {
            dispatch(
                Result.ServerConfigChanged(
                    BitwardenLoginStore.ServerConfig.Custom(
                        serverAddress = serverAddress
                    )
                )
            )
        }

        private suspend fun login(state: BitwardenLoginStore.State) {
            withContext(ioContext) {
                val serverUrl =
                    when (state.serverConfig) {
                        is BitwardenLoginStore.ServerConfig.Default -> "https://valut.bitwarden.com"
                        is BitwardenLoginStore.ServerConfig.Custom -> state.serverConfig.serverAddress
                    }

                val loginInteractor = loginInteractorFactory(serverUrl)
                val result =
                    loginInteractor
                        .login(
                            email = state.email,
                            masterPassword = state.password
                        )


            }
        }
    }

    private object ReducerImpl : Reducer<BitwardenLoginStore.State, Result> {
        override fun BitwardenLoginStore.State.reduce(result: Result): BitwardenLoginStore.State =
            when (result) {
                is Result.EmailChanged -> copy(email = result.email)
                is Result.PasswordChanged -> copy(password = result.password)
                is Result.ServerConfigChanged -> copy(serverConfig = result.serverConfig)
            }
    }

}