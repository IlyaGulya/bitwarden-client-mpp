import com.github.ajalt.clikt.core.CliktCommand
import io.ktor.client.engine.okhttp.*
import kotlinx.coroutines.runBlocking
import me.gulya.bitwarden.sdk.BitwardenSdk
import kotlin.system.exitProcess

class BitwardenCli : CliktCommand() {

    override fun run() {
        val email = System.getenv("EMAIL") ?: prompt(
            text = "Enter email"
        )
        if (email.isNullOrBlank()) {
            throw IllegalArgumentException("Please enter email")
        }
        val password = prompt(
            text = "Enter $email password",
            hideInput = true,
        )

        if (password.isNullOrBlank()) {
            throw IllegalArgumentException("Please enter password")
        }

        val sdk = BitwardenSdk(OkHttp)

        runBlocking {
            sdk.login(email, password)
        }

        exitProcess(0)
    }
}


fun main(args: Array<String>) {
    BitwardenCli().main(args)
}