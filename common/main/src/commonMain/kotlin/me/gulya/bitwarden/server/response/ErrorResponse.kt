package me.gulya.bitwarden.server.response

import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

class ErrorResponse {
    val message: String?
    val validationErrors: Map<String, List<String>>
    val statusCode: HttpStatusCode?

    constructor(decoder: JsonDecoder, status: HttpStatusCode) : this(decoder, status, false)

    constructor(decoder: JsonDecoder, status: HttpStatusCode, identityResponse: Boolean) {
        val response = runCatching {
            decoder.decodeJsonElement().jsonObject
        }.getOrNull()
        var errorModel: JsonObject? = null
        if (response != null) {
            val responseErrorModel = response["ErrorModel"]
            errorModel = if (responseErrorModel != null && identityResponse) {
                responseErrorModel.jsonObject
            } else {
                response
            }
        }
        when {
            errorModel != null -> {
                val model: ErrorModel = decoder.json.decodeFromJsonElement(ErrorModel.serializer(), errorModel)
                message = model.message
                validationErrors = model.validationErrors
            }
            status == HttpStatusCode.TooManyRequests -> {
                message = "Rate limit exceeded. Try again later."
                validationErrors = emptyMap()
            }
            else -> {
                message = null
                validationErrors = emptyMap()
            }
        }
        statusCode = status
    }

    fun getSingleMessage(): String? {
        return validationErrors.values.firstOrNull { error -> error.any() }?.get(0) ?: message
    }

    @Serializable
    data class ErrorModel(
        @SerialName("message") val message: String?,
        @SerialName("ValidationErrors") val validationErrors: Map<String, List<String>> = emptyMap(),
    )
}
