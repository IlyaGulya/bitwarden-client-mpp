package me.gulya.bitwarden.app.desktop

import androidx.compose.desktop.DesktopTheme
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.rootComponent
import com.arkivanov.decompose.lifecycle.LifecycleRegistry
import com.arkivanov.decompose.lifecycle.resume
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.badoo.reaktive.coroutinesinterop.asScheduler
import com.badoo.reaktive.scheduler.overrideSchedulers
import io.ktor.client.engine.okhttp.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.gulya.bitwarden.app.desktop.root.BitwardenDesktopRoot
import me.gulya.bitwarden.app.desktop.root.BitwardenDesktopRootContent
import me.gulya.bitwarden.sdk.BitwardenSdk
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
fun main() {
    overrideSchedulers(main = Dispatchers.Main::asScheduler)
    val lifecycle = LifecycleRegistry()
    lifecycle.resume()

    val sdk = BitwardenSdk(OkHttp)

    Window(title = "Bitwarden Desktop") {
        Surface(modifier = Modifier.fillMaxSize()) {
            MaterialTheme {
                DesktopTheme {
                    BitwardenDesktopRootContent(rootComponent { componentContext ->
                        bitwardenRoot(
                            componentContext = componentContext,
                            sdk = sdk,
                            mainContext = Dispatchers.Main,
                            ioContext = Dispatchers.IO,
                        )
                    })
                }
            }
        }
    }
}

private fun bitwardenRoot(
    componentContext: ComponentContext,
    sdk: BitwardenSdk,
    mainContext: CoroutineContext,
    ioContext: CoroutineContext,
): BitwardenDesktopRoot =
    BitwardenDesktopRoot(
        componentContext = componentContext,
        dependencies = object : BitwardenDesktopRoot.Dependencies {
            override val storeFactory = DefaultStoreFactory
            override val sdk: BitwardenSdk = sdk
            override val mainContext: CoroutineContext = mainContext
            override val ioContext: CoroutineContext = ioContext
        }
    )
