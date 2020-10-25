package me.gulya.bitwarden.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.enums.EventType

data class EventData(
    val type: EventType,
    val cipherId: String?,
    val date: DateTime,
)