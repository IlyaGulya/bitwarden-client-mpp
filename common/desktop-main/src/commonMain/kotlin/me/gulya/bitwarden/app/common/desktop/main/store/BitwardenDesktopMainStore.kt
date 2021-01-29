package me.gulya.bitwarden.app.common.desktop.main.store

import com.arkivanov.mvikotlin.core.store.Store
import me.gulya.bitwarden.app.common.desktop.main.store.BitwardenDesktopMainStore.*
import me.gulya.bitwarden.presentation.CipherView

interface BitwardenDesktopMainStore : Store<Intent, State, Label> {

    sealed class Intent

    data class State(
        val ciphers: List<CipherView>,
        val loading: Boolean,
        val error: String?,
    )

    sealed class Label
}