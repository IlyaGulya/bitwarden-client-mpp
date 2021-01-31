package me.gulya.bitwarden.app.desktop.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.RouterState
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import me.gulya.bitwarden.app.common.desktop.main.BitwardenDesktopMain
import me.gulya.bitwarden.app.common.login.BitwardenLogin
import me.gulya.bitwarden.app.desktop.root.integration.BitwardenDesktopRootImpl
import me.gulya.bitwarden.sdk.BitwardenSdk
import kotlin.coroutines.CoroutineContext

interface BitwardenDesktopRoot {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Main(val component: BitwardenDesktopMain) : Child()
        data class Login(val component: BitwardenLogin) : Child()
    }

    interface Dependencies {
        val storeFactory: StoreFactory
        val sdk: BitwardenSdk
        val mainContext: CoroutineContext
        val ioContext: CoroutineContext
    }

    companion object {
        operator fun invoke(
            componentContext: ComponentContext,
            dependencies: Dependencies
        ): BitwardenDesktopRoot =
            BitwardenDesktopRootImpl(componentContext, dependencies)
    }
}