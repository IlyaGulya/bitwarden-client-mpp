package me.gulya.bitwarden.domain

import bit.core.abstractions.ICryptoService
import bit.core.utilities.ServiceContainer
import me.gulya.bitwarden.enums.EncryptionType
import kotlin.jvm.JvmOverloads

class EncryptedString {
    val encryptedString: String
    val encryptionType: EncryptionType
    var iv: String? = null
    var data: String? = null
    var mac: String? = null


    private var _decryptedValue: String? = null

    @JvmOverloads
    constructor(encryptionType: EncryptionType, data: String, iv: String? = null, mac: String? = null) {
        if (data.isBlank()) {
            throw IllegalArgumentException("Data is empty")
        }
        val encryptedString =
            if (!iv.isNullOrBlank()) {
                "${encryptionType.value}.$iv|$data"
            } else {
                "${encryptionType.value}.$data"
            }
        this.encryptedString =
            if (!mac.isNullOrBlank()) {
                "$encryptedString|$mac"
            } else {
                encryptedString
            }
        this.encryptionType = encryptionType
        this.data = data
        this.iv = iv
        this.mac = mac
    }

    constructor(encryptedString: String) {
        this.encryptedString = encryptedString
        if (encryptedString.isBlank()) {
            throw IllegalArgumentException("encryptedString is blank")
        }
        val headerPieces = encryptedString.split("[.]").dropLastWhile { it.isEmpty() }.toTypedArray()
        val encPieces: Array<String>
        val encType: EncryptionType? = if (headerPieces.size == 2) EncryptionType.valueOf(headerPieces[0]) else null
        if (encType != null) {
            encryptionType = encType
            encPieces = headerPieces[1].split("[|]").dropLastWhile { it.isEmpty() }.toTypedArray()
        } else {
            encPieces = encryptedString.split("[|]").dropLastWhile { it.isEmpty() }.toTypedArray()
            encryptionType =
                if (encPieces.size == 3) EncryptionType.AES_CBC128_HMAC_SHA256_BASE64 else EncryptionType.AES_CBC256_B64
        }
        when (encryptionType) {
            EncryptionType.AES_CBC128_HMAC_SHA256_BASE64, EncryptionType.AES_CBC256_HMAC_SHA256_BASE64 -> {
                if (encPieces.size == 3) {
                    this.iv = encPieces[0]
                    this.data = encPieces[1]
                    this.mac = encPieces[2]
                }
            }
            EncryptionType.AES_CBC256_B64 -> {
                if (encPieces.size == 2) {
                    this.iv = encPieces[0]
                    this.data = encPieces[1]
                }
            }
            EncryptionType.RSA2048_OAEP_SHA256_BASE64, EncryptionType.RSA2048_OAEP_SHA1_BASE64 -> {
                if (encPieces.size == 1) {
                    this.data = encPieces[0]
                }
            }
            else -> return
        }
    }

    suspend fun decrypt(orgId: String? = null): String {
        val currentDecryptedValue = _decryptedValue
        if (currentDecryptedValue != null) {
            return currentDecryptedValue
        }
        val cryptoService: ICryptoService = ServiceContainer.Resolve("cryptoService")
        return try {
            val orgKey: SymmetricCryptoKey = cryptoService.GetOrgKeyAsync(orgId)
            cryptoService.DecryptToUtf8Async(this, orgKey)
        } catch (e: Exception) {
            "Error: decryption failure"
        }.also {
            _decryptedValue = it
        }
    }
}

fun String?.toCipherString() = this?.let(::EncryptedString)
fun String.toCipherString() = EncryptedString(this)