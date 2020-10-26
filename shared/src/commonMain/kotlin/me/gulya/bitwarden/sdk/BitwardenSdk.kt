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
import me.gulya.bitwarden.domain.data.AuthResult
import me.gulya.bitwarden.domain.login.InMemoryTokenStorage
import me.gulya.bitwarden.domain.login.LoginInteractor
import me.gulya.bitwarden.domain.login.TokenInteractor

class BitwardenSdk(
    httpClientEngineFactory: HttpClientEngineFactory<*>,
) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }
    private val client = HttpClient(httpClientEngineFactory) {
//        install(Logging) {
//            level = LogLevel.ALL
//            logger = object : Logger {
//                override fun log(message: String) {
//                    println(message)
//                }
//            }
//        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json = json
            )
        }
    }
    val api: BitwardenApi = KtorBitwardenApiImpl(client)

    private val cryptoPrimitives: CryptoPrimitives = CryptoPrimitives()
    private val cryptoFunctions: CryptoFunctions = CryptoFunctionsImpl(cryptoPrimitives)
    private val crypto: Crypto = CryptoImpl(cryptoFunctions)

    private val tokenStorage = InMemoryTokenStorage()

    private val tokenInteractor = TokenInteractor(json)

    private val loginInteractor = LoginInteractor(
        crypto = crypto,
        api = api,
        tokenInteractor = tokenInteractor,
        tokenStorage = tokenStorage,
    )

    suspend fun login(email: String, password: String): AuthResult {
        return loginInteractor.login(email = email, masterPassword = password)
    }

}