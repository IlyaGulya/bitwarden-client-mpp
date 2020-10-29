package me.gulya.bitwarden.sdk

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json
import me.gulya.bitwarden.api.BitwardenApi
import me.gulya.bitwarden.api.KtorBitwardenApiImpl
import me.gulya.bitwarden.crypto.*
import me.gulya.bitwarden.data.CipherData
import me.gulya.bitwarden.domain.data.AuthResult
import me.gulya.bitwarden.domain.data.Cipher
import me.gulya.bitwarden.domain.data.EncryptedString
import me.gulya.bitwarden.domain.login.*
import me.gulya.bitwarden.presentation.CipherView
import me.gulya.bitwarden.server.response.SyncResponse

class BitwardenSdk(
    httpClientEngineFactory: HttpClientEngineFactory<*>,
    endpointUrl: String,
) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }
    private val client = HttpClient(httpClientEngineFactory) {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json = json
            )
        }
    }

    private val cryptoPrimitives: CryptoPrimitives = CryptoPrimitives()
    private val cryptoFunctions: CryptoFunctions = CryptoFunctionsImpl(cryptoPrimitives)
    private val crypto: Crypto = CryptoImpl(cryptoFunctions)

    private val keyStorage = InMemoryKeyStorage()
    private val tokenStorage = InMemoryTokenStorage()

    private val tokenInteractor = TokenInteractor(json, tokenStorage)

    private val api: BitwardenApi = KtorBitwardenApiImpl(client, endpointUrl, tokenInteractor)

    private val loginInteractor = LoginInteractor(
        crypto = crypto,
        api = api,
        tokenInteractor = tokenInteractor,
        tokenStorage = tokenStorage,
        keyStorage = keyStorage,
    )

    private val syncInteractor = SyncInteractor(api, tokenInteractor)

    suspend fun login(email: String, password: String): AuthResult {
        return loginInteractor.login(email = email, masterPassword = password)
    }

    suspend fun sync(): SyncResponse {
        return syncInteractor.sync()
    }

    suspend fun syncAndDecodeCiphers(): List<CipherView> {
        return sync().run {
            ciphers.map {
                Cipher(
                    cipherData = CipherData(
                        response = it,
                        userId = tokenInteractor.accessToken()?.userId,
                        collectionIds = it.collectionIds.toSet(),
                    ),
                    localData = emptyMap() // TODO: WTF
                ).decrypt(orgId = null)
            }

        }
    }

}

class EncryptionInteractor(
    private val crypto: Crypto,
    private val keyStorage: KeyStorage,
) {
    suspend fun decrypt(encryptedString: EncryptedString, orgId: String?) {
        val key =
            if (orgId != null) {
                TODO("Organizations is not supported yet")
            } else {
                val encryptedEncryptionKey = keyStorage.encryptedKey
                if (encryptedEncryptionKey != null) {
                    val key = crypto.
                } else {
                    throw IllegalStateException("Encryption key does not exist. We have no key to decrypt.")
                }
            }
    }
}