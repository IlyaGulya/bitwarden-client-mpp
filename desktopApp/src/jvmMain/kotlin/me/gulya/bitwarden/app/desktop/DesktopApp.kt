package me.gulya.bitwarden.app.desktop

import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import me.gulya.bitwarden.app.login.LoginComponent

fun main() {
    Window(title = "Compose for Desktop", size = IntSize(300, 300)) {
        LoginComponent()
    }
}