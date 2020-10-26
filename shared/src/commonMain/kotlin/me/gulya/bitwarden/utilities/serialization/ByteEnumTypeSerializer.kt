package me.gulya.bitwarden.utilities.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.gulya.bitwarden.enums.Valued

abstract class ByteEnumTypeSerializer<T : Valued<Byte>>(
    valueFactory: (Byte) -> T,
) : EnumValueSerializer<T, Byte>(
    valueFactory = valueFactory,
    decoder = Decoder::decodeByte,
    encoder = Encoder::encodeByte,
    primitiveKind = PrimitiveKind.BYTE,
)