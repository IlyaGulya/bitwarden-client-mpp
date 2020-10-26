package me.gulya.bitwarden.domain.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.data.PasswordHistoryData
import me.gulya.bitwarden.presentation.PasswordHistoryView

data class PasswordHistory(
    val password: EncryptedString,
    val lastUsedDate: DateTime,
) {
    constructor(obj: PasswordHistoryData) : this(
        password = obj.password.toCipherString(),
        lastUsedDate = obj.lastUsedDate,
    )

    suspend fun decrypt(orgId: String?): PasswordHistoryView {
        return PasswordHistoryView(
            password = password.decrypt(orgId),
            lastUsedDate = lastUsedDate,
        )
    }

    fun toPasswordHistoryData(): PasswordHistoryData {
        return PasswordHistoryData(
            password = password.encryptedString,
            lastUsedDate = lastUsedDate
        )
    }

}