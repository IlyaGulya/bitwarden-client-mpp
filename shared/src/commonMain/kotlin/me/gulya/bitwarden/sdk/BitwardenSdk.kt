package me.gulya.bitwarden.sdk

import com.soywiz.klock.DateTime
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import me.gulya.bitwarden.api.BitwardenApi
import me.gulya.bitwarden.api.KtorBitwardenApiImpl
import me.gulya.bitwarden.crypto.*
import me.gulya.bitwarden.domain.data.AuthResult
import me.gulya.bitwarden.domain.login.InMemoryTokenStorage
import me.gulya.bitwarden.domain.login.LoginInteractor
import me.gulya.bitwarden.domain.login.SyncInteractor
import me.gulya.bitwarden.domain.login.TokenInteractor
import me.gulya.bitwarden.server.response.DateTimeContainerSerializer
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

    private val tokenStorage = InMemoryTokenStorage()

    private val tokenInteractor = TokenInteractor(json, tokenStorage)

    private val api: BitwardenApi = KtorBitwardenApiImpl(client, endpointUrl, tokenInteractor)

    private val loginInteractor = LoginInteractor(
        crypto = crypto,
        api = api,
        tokenInteractor = tokenInteractor,
        tokenStorage = tokenStorage,
    )

    private val syncInteractor = SyncInteractor(api, tokenInteractor)

    suspend fun login(email: String, password: String): AuthResult {
        return loginInteractor.login(email = email, masterPassword = password)
    }

    suspend fun sync(): SyncResponse {
        return syncInteractor.sync()
    }

}
