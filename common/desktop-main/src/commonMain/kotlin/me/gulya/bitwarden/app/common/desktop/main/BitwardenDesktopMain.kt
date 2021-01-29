package me.gulya.bitwarden.app.common.desktop.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import me.gulya.bitwarden.app.common.desktop.main.integration.BitwardenDesktopMainImpl
import me.gulya.bitwarden.presentation.CipherView
import me.gulya.bitwarden.sdk.BitwardenSdk
import kotlin.coroutines.CoroutineContext

interface BitwardenDesktopMain {

    val state: Value<ViewModel>

    data class ViewModel(
        val items: List<CipherView>,
        val loaderVisible: Boolean,
        val error: String?,
    )

    interface Dependencies {
        val storeFactory: StoreFactory
        val sdk: BitwardenSdk
        val mainContext: CoroutineContext
        val ioContext: CoroutineContext
    }

    companion object {
        operator fun invoke(componentContext: ComponentContext, dependencies: Dependencies): BitwardenDesktopMain =
            BitwardenDesktopMainImpl(componentContext, dependencies)
    }
}