package me.gulya.bitwarden.domain.login

import com.soywiz.klock.DateTime
import com.soywiz.krypto.encoding.fromBase64
import io.ktor.utils.io.core.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okio.ByteString
import okio.ByteString.Companion.decodeBase64

class TokenInteractor(
    val json: Json
) {
    fun decode(accessToken: String): DecodedAccessToken {
        val parts = accessToken.split(".")
        if (parts.size != 3) {
            throw IllegalArgumentException("JWT token must contain 3 parts")
        }

        val tokenJson = parts[1].decodeBase64()?.utf8()
        return json.decodeFromString(DecodedAccessToken.serializer(), tokenJson!!)
    }

}

@Serializable
data class DecodedAccessToken(
    @SerialName("sub") val userId: String?,
    @SerialName("email") val email: String?,
    @SerialName("email_verified") val emailVerified: Boolean?,
    @SerialName("name") val name: String?,
    @SerialName("premium") val premium: Boolean?,
    @SerialName("iss") val issuer: String?,
    @SerialName("exp") private val expirationDateEpochSeconds: Double?,
) {
    val expirationDate: DateTime? by lazy {
        expirationDateEpochSeconds?.let { DateTime(expirationDateEpochSeconds) }
    }
}