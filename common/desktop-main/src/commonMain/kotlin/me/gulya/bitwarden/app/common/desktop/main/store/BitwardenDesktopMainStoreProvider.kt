package me.gulya.bitwarden.app.common.desktop.main.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import kotlinx.coroutines.withContext
import me.gulya.bitwarden.app.common.desktop.main.BitwardenDesktopMain
import me.gulya.bitwarden.app.common.desktop.main.store.BitwardenDesktopMainStore.*
import me.gulya.bitwarden.presentation.CipherView

internal class BitwardenDesktopMainStoreProvider(
    private val dependencies: BitwardenDesktopMain.Dependencies,
) : BitwardenDesktopMain.Dependencies by dependencies {

    private sealed class Result {
        object LoadingStarted : Result()
        data class LoadingSuccessful(
            val ciphers: List<CipherView>,
        ) : Result()

        data class LoadingUnsuccessful(
            val error: Exception,
        ) : Result()
    }


    fun provide(): BitwardenDesktopMainStore =
        object : BitwardenDesktopMainStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "BitwardenLoginStore",
                initialState = State(
                    ciphers = emptyList(),
                    loading = false,
                    error = null,
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private inner class ExecutorImpl :
        SuspendExecutor<Intent, Unit, State, Result, Label>() {

        override suspend fun executeAction(action: Unit, getState: () -> State) {
            loadCiphers()
        }

        override suspend fun executeIntent(
            intent: Intent,
            getState: () -> State
        ) {
            when (intent) {

            }
        }

        private suspend fun loadCiphers() {
            dispatch(Result.LoadingStarted)
            withContext(ioContext) {
                try {
                    val cipherViews = sdk.syncAndDecryptCiphers()
                    dispatch(Result.LoadingSuccessful(cipherViews))
                } catch (error: Exception) {
                    dispatch(Result.LoadingUnsuccessful(error))
                }
            }
        }

    }

    private object ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result): State =
            when (result) {
                is Result.LoadingStarted -> copy(loading = true)
                is Result.LoadingSuccessful -> copy(ciphers = result.ciphers, loading = false)
                is Result.LoadingUnsuccessful -> copy(error = result.error.message, loading = false)
            }
    }

}