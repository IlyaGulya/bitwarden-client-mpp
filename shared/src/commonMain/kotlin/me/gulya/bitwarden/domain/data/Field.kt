package me.gulya.bitwarden.domain.data

import me.gulya.bitwarden.data.FieldData
import me.gulya.bitwarden.enums.FieldType
import me.gulya.bitwarden.presentation.FieldView

class Field(
    val name: EncryptedString? = null,
    val value: EncryptedString? = null,
    val type: FieldType = FieldType.values()[0],
) {
    constructor(obj: FieldData) : this(
        name = obj.name.toCipherString(),
        value = obj.value.toCipherString(),
        type = obj.type,
    )

    suspend fun decrypt(orgId: String?): FieldView {
        return FieldView(
            name = name?.decrypt(orgId),
            value = value?.decrypt(orgId),
            type = type,
        )
    }

    fun toFieldData(): FieldData {
        return FieldData(
            name = name?.encryptedString,
            type = type,
            value = value?.encryptedString
        )
    }
}