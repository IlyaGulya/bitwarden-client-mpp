package me.gulya.bitwarden.utilities.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import me.gulya.bitwarden.enums.Valued

abstract class IntegerEnumTypeSerializer<T : Valued<Int>>(
    valueFactory: (Int) -> T,
) : EnumValueSerializer<T, Int>(
    valueFactory = valueFactory,
    decoder = Decoder::decodeInt,
    encoder = Encoder::encodeInt,
    primitiveKind = PrimitiveKind.INT,
)