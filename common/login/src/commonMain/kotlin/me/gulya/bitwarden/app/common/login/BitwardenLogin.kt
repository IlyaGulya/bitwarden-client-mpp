package me.gulya.bitwarden.app.common.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import me.gulya.bitwarden.app.common.login.integration.BitwardenLoginImpl
import me.gulya.bitwarden.app.common.utils.Consumer
import me.gulya.bitwarden.sdk.BitwardenSdk
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
        val sdk: BitwardenSdk
        val loginOutput: Consumer<Output>
        val mainContext: CoroutineContext
        val ioContext: CoroutineContext
    }

    sealed class Output {
        object Finished : Output()
    }

    companion object {
        operator fun invoke(componentContext: ComponentContext, dependencies: Dependencies): BitwardenLogin =
            BitwardenLoginImpl(componentContext, dependencies)
    }
}