package me.gulya.bitwarden.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.server.response.PasswordHistoryResponse

data class PasswordHistoryData(
    val password: String,
    val lastUsedDate: DateTime,
) {
    constructor(data: PasswordHistoryResponse): this(
        password = data.password,
        lastUsedDate = data.lastUsedDate.dateTime,
    )
}