package me.gulya.bitwarden.utilities.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.gulya.bitwarden.enums.Valued

abstract class EnumValueSerializer<T : Valued<ValueType>, ValueType : Any>(
    private val valueFactory: (ValueType) -> T,
    private val decoder: (Decoder) -> ValueType,
    private val encoder: (Encoder, ValueType) -> Unit,
    primitiveKind: PrimitiveKind,
) : KSerializer<T> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(this::class.simpleName ?: "UNKNOWN_CLASS_NAME", primitiveKind)

    override fun deserialize(decoder: Decoder): T {
        return valueFactory(this.decoder(decoder))
    }

    override fun serialize(encoder: Encoder, value: T) {
        this.encoder(encoder, value.value)
    }

}