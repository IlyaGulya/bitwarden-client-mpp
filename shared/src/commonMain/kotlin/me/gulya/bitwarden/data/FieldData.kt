package me.gulya.bitwarden.data

import me.gulya.bitwarden.enums.FieldType
import me.gulya.bitwarden.server.ServerField

class FieldData(
    val type: FieldType,
    val name: String?,
    val value: String?,
) {
    constructor(data: ServerField) : this(
        type = data.type,
        name = data.name,
        value = data.value,
    )
}