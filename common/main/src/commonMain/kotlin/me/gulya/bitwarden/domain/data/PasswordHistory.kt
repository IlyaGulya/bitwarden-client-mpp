package me.gulya.bitwarden.domain.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.data.PasswordHistoryData

data class PasswordHistory(
    val password: EncryptedString,
    val lastUsedDate: DateTime,
) {
    constructor(obj: PasswordHistoryData) : this(
        password = obj.password.toCipherString(),
        lastUsedDate = obj.lastUsedDate,
    )

    fun toPasswordHistoryData(): PasswordHistoryData {
        return PasswordHistoryData(
            password = password.encryptedString,
            lastUsedDate = lastUsedDate
        )
    }

}