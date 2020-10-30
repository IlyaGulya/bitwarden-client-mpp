package me.gulya.bitwarden.domain.login

import com.github.aakira.napier.Napier
import me.gulya.bitwarden.api.BitwardenApi
import me.gulya.bitwarden.crypto.Crypto
import me.gulya.bitwarden.domain.data.AuthResult
import me.gulya.bitwarden.domain.data.SymmetricCryptoKey
import me.gulya.bitwarden.domain.data.crypto.EncryptedKey
import me.gulya.bitwarden.domain.data.crypto.EncryptedPrivateKey
import me.gulya.bitwarden.domain.data.crypto.MasterKeyHash
import me.gulya.bitwarden.domain.data.crypto.SessionKey
import me.gulya.bitwarden.enums.DeviceType
import me.gulya.bitwarden.server.request.DeviceRequest
import me.gulya.bitwarden.server.request.KeysRequest
import me.gulya.bitwarden.server.request.PreloginRequest
import me.gulya.bitwarden.server.request.TokenRequest
import me.gulya.bitwarden.server.response.IdentityResponse

class LoginInteractor(
    private val crypto: Crypto,
    private val api: BitwardenApi,
    private val tokenStorage: TokenStorage,
    private val tokenInteractor: TokenInteractor,
    private val keyStorage: KeyStorage,
) {

    suspend fun login(email: String, masterPassword: String): AuthResult {
        val key = this.createPreloginKey(email, masterPassword)
        val hashedPassword = crypto.hashPassword(masterPassword, key)

        val appId = "b1120836-8257-4f08-a72b-3d8d040c5f04" // generate and store locally
        val deviceModel = "Linux Desktop CLI" // get device model, etc.
        val identityClientId = "CLI" // specify correct client id
        val device = DeviceRequest(
            type = DeviceType.LinuxDesktop,
            name = deviceModel,
            identifier = appId,
            pushToken = null,
        )

        val tokenRequest = TokenRequest(
            email = email,
            masterPasswordHash = hashedPassword,
            code = null,
            codeVerifier = null,
            redirectUri = null,
            token = null,
            provider = null,
            remember = null,
            device = device,
        )

        when (val response = api.identityToken(tokenRequest, identityClientId)) {
            is IdentityResponse.Token -> {
                val tokenResponse = response.identityTokenResponse
                tokenStorage.saveTokens(
                    accessToken = tokenResponse.accessToken,
                    refreshToken = tokenResponse.refreshToken
                )

                val decodedToken = tokenInteractor.accessToken()
                println("Access token (decoded): $decodedToken")

                keyStorage.saveMasterKeyHash(MasterKeyHash(hashedPassword))
                keyStorage.saveKey(SessionKey(key.keyB64))
                keyStorage.saveEncryptedKey(EncryptedKey(tokenResponse.key))

                val privateKey =
                    if (tokenResponse.privateKey != null) {
                        tokenResponse.privateKey
                    } else {
                        // This user does not have a key pair yet (old account?), so we are generating it.
                        try {
                            val keyPair = crypto.createKeyPair(key)
                            api.accountKeys(
                                KeysRequest(
                                    publicKey = keyPair.publicKey,
                                    encryptedPrivateKey = keyPair.privateKey.encryptedString
                                )
                            )
                            keyPair.privateKey.encryptedString
                        } catch (exception: Exception) {
                            Napier.e("Error during generation and submittment of key pair", exception)
                            null
                        }
                    }

                if (privateKey != null) {
                    keyStorage.saveEncryptedPrivateKey(EncryptedPrivateKey(privateKey))
                }

                return AuthResult(
                    twoFactor = false,
                    resetMasterPassword = tokenResponse.resetMasterPassword,
                    twoFactorProviders = null,
                )
            }
            is IdentityResponse.TwoFactor -> TODO("Two factor authentication is not supported yet")
        }
    }

    private suspend fun createPreloginKey(email: String, masterPassword: String): SymmetricCryptoKey {
        val preparedEmail = email.trim().toLowerCase()
        val prelogin = api.prelogin(PreloginRequest(email = preparedEmail))
        return crypto.createKey(masterPassword, preparedEmail, prelogin.kdf, prelogin.kdfIterations)
    }

}

