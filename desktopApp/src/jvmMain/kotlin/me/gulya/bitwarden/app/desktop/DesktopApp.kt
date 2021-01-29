package me.gulya.bitwarden.app.desktop

import androidx.compose.desktop.DesktopTheme
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
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
import me.gulya.bitwarden.app.login.BitwardenLoginContent
import me.gulya.bitwarden.sdk.BitwardenSdk

@OptIn(ExperimentalCoroutinesApi::class)
fun main() {
    overrideSchedulers(main = Dispatchers.Main::asScheduler)
    val lifecycle = LifecycleRegistry()
    lifecycle.resume()

    val sdk = remember {
        BitwardenSdk(OkHttp, endpointUrl)
    }

    Window(title = "Bitwarden Desktop") {
        Surface(modifier = Modifier.fillMaxSize()) {
            MaterialTheme {
                DesktopTheme {
                    BitwardenDesktopRootContent(rootComponent(factory = ::bitwardenRoot))
                }
            }
        }
        BitwardenLoginContent()
    }
}

private fun bitwardenRoot(componentContext: ComponentContext): BitwardenDesktopRoot =
    BitwardenDesktopRoot(
        componentContext = componentContext,
        dependencies = object : BitwardenDesktopRoot.Dependencies {
            override val storeFactory = DefaultStoreFactory
            override val dependencies = object : BitwardenDesktopRoot.Dependencies {

            }
        }
    )
