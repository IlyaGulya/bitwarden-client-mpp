package me.gulya.bitwarden.server

import me.gulya.bitwarden.enums.FieldType

data class ServerField(
    val type: FieldType,
    val name: String?,
    val value: String?,
)