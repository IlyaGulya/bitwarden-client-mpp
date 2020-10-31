package me.gulya.bitwarden.presentation

import me.gulya.bitwarden.domain.data.Field
import me.gulya.bitwarden.enums.FieldType

data class FieldView(
    val name: String?,
    val value: String?,
    val type: FieldType,
    val newField: Boolean = false,
) {
    constructor(f: Field) : this(
        name = null,
        value = null,
        type = f.type,
        newField = false,
    )

    val maskedValue: String?
        get() = if (value != null) "••••••••" else null
}