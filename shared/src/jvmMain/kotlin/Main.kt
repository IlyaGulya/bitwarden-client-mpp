import com.github.ajalt.clikt.core.CliktCommand
import io.ktor.client.*
import me.gulya.bitwarden.api.BitwardenApi
import me.gulya.bitwarden.api.KtorBitwardenApiImpl

class BitwardenCli : CliktCommand() {

    override fun run() {
        val login = "***REMOVED***"
        val password = prompt(
            text = "Enter ***REMOVED*** password",
            hideInput = true,
        )
        val client = HttpClient {

        }
        val api: BitwardenApi = KtorBitwardenApiImpl(client)

//        val cryptoPrimitives: CryptoPrimitives = CryptoPrimitives
//        val cryptoFunctions: CryptoFunctions = CryptoFunctionsImpl(cryptoPrimitives)
//        val crypto: Crypto = CryptoImpl(
//            cryptoFunctions = CryptoFunctionsImpl(
//                primitives = cryptoPrimitives
//            )
//        )
//
//        GlobalScope.launch {
//            val key = crypto.makeKey()
//            val passwordHash = crypto.hashPassword(password, )
//
//
//            api.verifyAccountPassword(PasswordVerificationRequest())
//        }
    }
}


fun main(args: Array<String>) = BitwardenCli().main(args)