import com.github.ajalt.clikt.core.CliktCommand
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import me.gulya.bitwarden.api.BitwardenApi
import me.gulya.bitwarden.api.KtorBitwardenApiImpl
import me.gulya.bitwarden.crypto.Crypto
import me.gulya.bitwarden.crypto.CryptoFunctions
import me.gulya.bitwarden.crypto.CryptoFunctionsImpl
import me.gulya.bitwarden.crypto.CryptoImpl
import me.gulya.bitwarden.crypto.CryptoPrimitives
import me.gulya.bitwarden.domain.login.LoginInteractor
import me.gulya.bitwarden.domain.login.TokenInteractor

class BitwardenCli : CliktCommand() {

    override fun run() {
        val email = "***REMOVED***"
        val password = prompt(
            text = "Enter ***REMOVED*** password",
            hideInput = true,
        )
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
        val client = HttpClient(OkHttp) {
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
        val api: BitwardenApi = KtorBitwardenApiImpl(client)

        val cryptoPrimitives: CryptoPrimitives = CryptoPrimitives()
        val cryptoFunctions: CryptoFunctions = CryptoFunctionsImpl(cryptoPrimitives)
        val crypto: Crypto = CryptoImpl(
            cryptoFunctions = CryptoFunctionsImpl(
                primitives = cryptoPrimitives
            )
        )

        val tokenInteractor = TokenInteractor(json)

        val loginInteractor = LoginInteractor(
            crypto = crypto,
            api = api,
            tokenInteractor = tokenInteractor,
        )

        runBlocking {
            val authResult = loginInteractor.login(email = email, masterPassword = password!!)

            println(authResult)
        }
    }
}


fun main(args: Array<String>) {
    BitwardenCli().main(args)
}