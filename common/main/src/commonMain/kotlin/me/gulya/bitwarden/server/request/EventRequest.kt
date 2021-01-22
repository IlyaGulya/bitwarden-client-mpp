package me.gulya.bitwarden.server.request

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.enums.EventType

data class EventRequest(
    val type: EventType,
    val cipherId: String,
    val date: DateTime,
)