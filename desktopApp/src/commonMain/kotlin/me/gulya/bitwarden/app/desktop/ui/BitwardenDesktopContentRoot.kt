package me.gulya.bitwarden.app.desktop.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import me.gulya.bitwarden.app.common.login.BitwardenLogin
import me.gulya.bitwarden.app.desktop.ui.integration.BitwardenDesktopContentRootImpl

interface BitwardenDesktopContentRoot {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Main(val component: BitwardenDesktopMain) : Child()
        data class Login(val component: BitwardenLogin) : Child()
    }

    interface Dependencies {
        val storeFactory: StoreFactory
    }

    companion object {
        operator fun invoke(
            componentContext: ComponentContext,
            dependencies: Dependencies
        ): BitwardenDesktopContentRoot =
            BitwardenDesktopContentRootImpl(componentContext, dependencies)
    }
}