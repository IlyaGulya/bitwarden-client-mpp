import com.github.ajalt.clikt.core.CliktCommand
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.JvmPreferencesSettings
import io.ktor.client.engine.okhttp.*
import kotlinx.coroutines.runBlocking
import me.gulya.bitwarden.sdk.BitwardenSdk
import java.util.prefs.Preferences
import kotlin.system.exitProcess

@OptIn(ExperimentalSettingsImplementation::class)
class BitwardenCli : CliktCommand() {

    override fun run() {
        val endpointUrl = System.getenv("ENDPOINT_URL") ?: prompt(
            text = "Enter endpoint URL (empty for https://vault.bitwarden.com)"
        ) ?: "https://vault.bitwarden.com"
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

        val settings = JvmPreferencesSettings(Preferences.userNodeForPackage(BitwardenCli::class.java))
        val sdk = BitwardenSdk(OkHttp, settings)

        runBlocking {
            sdk.getEndpointUrlHolder().setEndpointUrl(endpointUrl)
            sdk.login(email, password)

            sdk.syncAndDecryptCiphers().forEach {
                println(it)
            }
        }

        exitProcess(0)
    }
}


fun main(args: Array<String>) {
    BitwardenCli().main(args)
}