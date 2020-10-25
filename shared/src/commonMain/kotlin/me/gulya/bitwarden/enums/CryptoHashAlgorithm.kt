package me.gulya.bitwarden.enums

enum class CryptoHashAlgorithm(
    override val value: Byte,
): Valued<Byte> {
    Sha1(0),
    Sha256(1),
    Sha512(2),
    Md5(3);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }

}