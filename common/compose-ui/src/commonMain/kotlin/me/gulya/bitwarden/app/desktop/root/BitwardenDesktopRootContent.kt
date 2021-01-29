package me.gulya.bitwarden.app.desktop.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import me.gulya.bitwarden.app.desktop.main.BitwardenDesktopMainContent
import me.gulya.bitwarden.app.desktop.ui.crossfade
import me.gulya.bitwarden.app.login.BitwardenLoginContent

@Composable
fun BitwardenDesktopRootContent(component: BitwardenDesktopRoot) {
    Children(routerState = component.routerState, animation = crossfade()) { child, _ ->
        when (child) {
            is BitwardenDesktopRoot.Child.Login -> BitwardenLoginContent(child.component)
            is BitwardenDesktopRoot.Child.Main -> BitwardenDesktopMainContent(child.component)
        }
    }
}
