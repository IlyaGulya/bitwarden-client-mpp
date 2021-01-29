package me.gulya.bitwarden.app.common.login.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.badoo.reaktive.base.invoke
import kotlinx.coroutines.withContext
import me.gulya.bitwarden.app.common.login.BitwardenLogin
import me.gulya.bitwarden.app.common.login.store.BitwardenLoginStore.Intent.*

internal class BitwardenLoginStoreProvider(
    private val dependencies: BitwardenLogin.Dependencies,
) : BitwardenLogin.Dependencies by dependencies {

    private sealed class Result {
        data class EmailChanged(val email: String) : Result()
        data class PasswordChanged(val password: String) : Result()
        data class ServerConfigChanged(val serverConfig: BitwardenLoginStore.ServerConfig) : Result()
        data class LoginUnsuccessful(val error: Exception) : Result()
        object LoginStarted : Result()
        object LoginSuccessful : Result()
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
                    error = null,
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private inner class ExecutorImpl :
        SuspendExecutor<BitwardenLoginStore.Intent, Unit, BitwardenLoginStore.State, Result, BitwardenLoginStore.Label>() {

        override suspend fun executeIntent(
            intent: BitwardenLoginStore.Intent,
            getState: () -> BitwardenLoginStore.State
        ) {
            when (intent) {
                is EmailChanged -> setEmail(intent.email)
                is PasswordChanged -> setPassword(intent.password)
                is CustomServerCheckedChanged -> changeCustomServerChecked(intent.checked)
                is CustomServerAddressChanged -> changeCustomServerAddress(intent.serverAddress)
                is Login -> login(getState())
            }
        }

        private fun setEmail(login: String) {
            dispatch(Result.EmailChanged(login))
        }

        private fun setPassword(password: String) {
            dispatch(Result.PasswordChanged(password))
        }

        private fun changeCustomServerChecked(checked: Boolean) {
            dispatch(
                if (checked) {
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

        private fun changeCustomServerAddress(serverAddress: String) {
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

                val loginInteractor = loginInteractorFactory.get(serverUrl)

                dispatch(Result.LoginStarted)

                try {
                    val result =
                        loginInteractor
                            .login(
                                email = state.email,
                                masterPassword = state.password
                            )

                    when {
                        result.twoFactor -> TODO("Two factor auth is not supported yet")
                        result.resetMasterPassword -> TODO("Master password reset is not supported yet")
                        else -> {
                            dispatch(Result.LoginSuccessful)
                            loginOutput(BitwardenLogin.Output.Finished)
                        }
                    }
                } catch (error: Exception) {
                    dispatch(Result.LoginUnsuccessful(error))
                }

            }
        }
    }

    private object ReducerImpl : Reducer<BitwardenLoginStore.State, Result> {
        override fun BitwardenLoginStore.State.reduce(result: Result): BitwardenLoginStore.State =
            when (result) {
                is Result.EmailChanged -> copy(email = result.email)
                is Result.PasswordChanged -> copy(password = result.password)
                is Result.ServerConfigChanged -> copy(serverConfig = result.serverConfig)
                is Result.LoginStarted -> copy(loading = true, error = null)
                is Result.LoginSuccessful -> copy(loading = false)
                is Result.LoginUnsuccessful -> copy(
                    loading = false,
                    error = result.error.message.toString(),
                )
            }
    }

}