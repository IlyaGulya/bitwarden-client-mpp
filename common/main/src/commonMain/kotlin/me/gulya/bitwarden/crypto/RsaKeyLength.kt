package me.gulya.bitwarden.crypto

enum class RsaKeyLength(val keyLength: Int) {
    RSA1024(1024),
    RSA2048(2048),
    RSA4096(4096);
}