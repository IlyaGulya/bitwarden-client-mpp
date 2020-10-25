package me.gulya.bitwarden.presentation

import me.gulya.bitwarden.domain.Field
import me.gulya.bitwarden.enums.FieldType

class FieldView(
    val name: String?,
    val value: String?,
    val type: FieldType,
    var newField: Boolean = false,
) : View() {
    constructor(f: Field) : this(
        name = null,
        value = null,
        type = f.type,
        newField = false,
    )

    val maskedValue: String?
        get() = if (value != null) "••••••••" else null
}