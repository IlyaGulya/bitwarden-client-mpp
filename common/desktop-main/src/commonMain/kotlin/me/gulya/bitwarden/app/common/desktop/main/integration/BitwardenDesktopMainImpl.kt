package me.gulya.bitwarden.app.common.desktop.main.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.operator.map
import me.gulya.bitwarden.app.common.desktop.main.BitwardenDesktopMain
import me.gulya.bitwarden.app.common.desktop.main.BitwardenDesktopMain.Dependencies
import me.gulya.bitwarden.app.common.desktop.main.store.BitwardenDesktopMainStoreProvider
import me.gulya.bitwarden.app.common.utils.asValue
import me.gulya.bitwarden.app.common.utils.getStore

class BitwardenDesktopMainImpl(
    private val componentContext: ComponentContext,
    private val dependencies: Dependencies,
) : BitwardenDesktopMain, ComponentContext by componentContext, Dependencies by dependencies {

    private val store = instanceKeeper.getStore {
        BitwardenDesktopMainStoreProvider(
            dependencies = dependencies,
        ).provide()
    }

    override val state = store.asValue().map {
        it.run {
            BitwardenDesktopMain.ViewModel(
                items = ciphers,
                loaderVisible = loading,
                error = error,
            )
        }
    }

}