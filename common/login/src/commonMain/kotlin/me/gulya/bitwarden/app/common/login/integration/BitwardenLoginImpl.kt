package me.gulya.bitwarden.app.common.login.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.operator.map
import me.gulya.bitwarden.app.common.login.BitwardenLogin
import me.gulya.bitwarden.app.common.login.BitwardenLogin.Dependencies
import me.gulya.bitwarden.app.common.login.store.BitwardenLoginStore
import me.gulya.bitwarden.app.common.login.store.BitwardenLoginStoreProvider
import me.gulya.bitwarden.app.common.utils.asValue
import me.gulya.bitwarden.app.common.utils.getStore

class BitwardenLoginImpl(
    private val componentContext: ComponentContext,
    private val dependencies: Dependencies,
) : BitwardenLogin, ComponentContext by componentContext, Dependencies by dependencies {

    private val store = instanceKeeper.getStore {
        BitwardenLoginStoreProvider(
            storeFactory = storeFactory,
        ).provide()
    }

    override val state = store.asValue().map {
        it.run {
            BitwardenLogin.ViewModel(
                email = email,
                password = password,
                customServerCheckboxEnabled = serverConfig is BitwardenLoginStore.ServerConfig.Custom,
                customServerAddressVisible = serverConfig is BitwardenLoginStore.ServerConfig.Custom,
                customServerAddress = (serverConfig as? BitwardenLoginStore.ServerConfig.Custom)?.serverAddress ?: "",
            )
        }
    }

}