package me.gulya.bitwarden.app.common.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.base.Consumer
import me.gulya.bitwarden.app.common.login.integration.BitwardenLoginImpl
import me.gulya.bitwarden.domain.login.LoginInteractor
import kotlin.coroutines.CoroutineContext

interface BitwardenLogin {

    val state: Value<ViewModel>

    fun emailChanged(email: String)
    fun passwordChanged(password: String)
    fun customServerAddressChanged(serverAddress: String)
    fun customServerCheckedChanged(checked: Boolean)

    fun loginButtonPressed()

    data class ViewModel(
        val email: String,
        val password: String,
        val customServerCheckboxChecked: Boolean,
        val customServerAddressVisible: Boolean,
        val customServerAddress: String,
        val loading: Boolean,
        val error: String?,
    )

    interface Dependencies {
        val storeFactory: StoreFactory
        val loginInteractorFactory: LoginInteractorFactory
        val loginOutput: Consumer<Output>
        val mainContext: CoroutineContext
        val ioContext: CoroutineContext
    }

    fun interface LoginInteractorFactory {
        fun get(serverUrl: String): LoginInteractor
    }

    sealed class Output {
        object Finished : Output()
    }

    companion object {
        operator fun invoke(componentContext: ComponentContext, dependencies: Dependencies): BitwardenLogin =
            BitwardenLoginImpl(componentContext, dependencies)
    }
}