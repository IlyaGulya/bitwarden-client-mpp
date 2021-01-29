package me.gulya.bitwarden.app.desktop.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.asState
import me.gulya.bitwarden.app.common.desktop.main.BitwardenDesktopMain

@Composable
fun BitwardenDesktopMainContent(component: BitwardenDesktopMain) {
    val state by component.state.asState()

    LazyColumn {
        items(state.items) {
            Row {
                Text("Id = ${it.id}")
                Text("Name = ${it.name}")
            }
        }
    }
}