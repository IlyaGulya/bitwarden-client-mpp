import com.github.ajalt.clikt.core.CliktCommand
import io.ktor.client.engine.okhttp.*
import kotlinx.coroutines.runBlocking
import me.gulya.bitwarden.sdk.BitwardenSdk
import kotlin.system.exitProcess

class BitwardenCli : CliktCommand() {

    override fun run() {
        val endpointUrl = System.getenv("ENDPOINT_URL") ?: prompt(
            text = "Enter endpoint URL (empty for https://vault.bitwarden.com)"
        ) ?: "https://valut.bitwarden.com"
        val email = System.getenv("EMAIL") ?: prompt(
            text = "Enter email"
        )
        if (email.isNullOrBlank()) {
            throw IllegalArgumentException("Please enter email")
        }
        val password = System.getenv("PASSWORD") ?: prompt(
            text = "Enter $email password",
            hideInput = true,
        )

        if (password.isNullOrBlank()) {
            throw IllegalArgumentException("Please enter password")
        }

        val sdk = BitwardenSdk(OkHttp, endpointUrl)

        runBlocking {
            sdk.login(email, password)

            println(sdk.sync())
        }

        exitProcess(0)
    }
}


fun main(args: Array<String>) {
    BitwardenCli().main(args)
}