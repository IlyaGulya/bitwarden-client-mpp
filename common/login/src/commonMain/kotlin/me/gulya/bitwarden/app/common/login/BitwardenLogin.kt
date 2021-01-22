package me.gulya.bitwarden.app.common.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.base.Consumer
import me.gulya.bitwarden.app.common.login.integration.BitwardenLoginImpl
import me.gulya.bitwarden.domain.login.LoginInteractor

interface BitwardenLogin {

    val state: Value<ViewModel>

    data class ViewModel(
        val email: String,
        val password: String,
        val customServerCheckboxEnabled: Boolean,
        val customServerAddressVisible: Boolean,
        val customServerAddress: String,
    )

    interface Dependencies {
        val storeFactory: StoreFactory
        val loginInteractor: LoginInteractor
        val loginOutput: Consumer<Output>
    }

    sealed class Output {
        object Finished : Output()
    }

    companion object {
        operator fun invoke(componentContext: ComponentContext, dependencies: Dependencies): BitwardenLogin =
            BitwardenLoginImpl(componentContext, dependencies)
    }
}