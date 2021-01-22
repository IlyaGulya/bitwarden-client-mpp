package me.gulya.bitwarden.server.response

import com.soywiz.klock.DateException
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = DateTimeContainerSerializer::class)
data class DateTimeContainer(
    val dateTime: DateTime
) {
    companion object {
        val EPOCH = DateTimeContainer(DateTime.EPOCH)
    }
}

@Serializer(forClass = DateTimeContainer::class)
class DateTimeContainerSerializer : KSerializer<DateTimeContainer> {
    private val formats = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSSSZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ",
    ).map { DateFormat(it) }

    override fun deserialize(decoder: Decoder): DateTimeContainer {
        val str = decoder.decodeString()
        val dateTime = formats.asSequence().mapNotNull { it.tryParse(str) }.firstOrNull()
        if (dateTime != null) {
            return DateTimeContainer(dateTime.local)
        } else {
            throw DateException("Not a valid format: '$str' for $formats")
        }
    }

    override fun serialize(encoder: Encoder, value: DateTimeContainer) {
        encoder.encodeString(value.dateTime.format(DateFormat.FORMAT2))
    }

}