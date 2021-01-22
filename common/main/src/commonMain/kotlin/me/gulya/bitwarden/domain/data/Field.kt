package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.data.FieldData
import me.gulya.bitwarden.enums.FieldType

class Field(
    val name: EncryptedString?,
    val value: EncryptedString?,
    val type: FieldType,
) {
    constructor(obj: FieldData) : this(
        name = obj.name.toCipherString(),
        value = obj.value.toCipherString(),
        type = obj.type,
    )

    fun toFieldData(): FieldData {
        return FieldData(
            name = name?.encryptedString,
            type = type,
            value = value?.encryptedString
        )
    }
}