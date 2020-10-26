package me.gulya.bitwarden.domain.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.data.LoginData
import me.gulya.bitwarden.presentation.LoginView
import kotlin.jvm.JvmOverloads

class Login(
    val username: EncryptedString?,
    val password: EncryptedString?,
    val totp: EncryptedString?,
    val passwordRevisionDate: DateTime?,
    val uris: List<LoginUri>,
) {
    @JvmOverloads
    constructor(obj: LoginData) : this(
        username = obj.username.toCipherString(),
        password = obj.password.toCipherString(),
        totp = obj.totp.toCipherString(),
        passwordRevisionDate = obj.passwordRevisionDate,
        uris = obj.uris.map { LoginUri(it) },
    )


    suspend fun decrypt(orgId: String?): LoginView {
        return LoginView(
            username = username?.decrypt(orgId),
            password = password?.decrypt(orgId),
            passwordRevisionDate = passwordRevisionDate,
            totp = totp?.decrypt(orgId),
            uris = uris.map { uri -> uri.decrypt(orgId) }
        )
    }

    fun toLoginData(): LoginData {
        return LoginData(
            username = username?.encryptedString,
            password = password?.encryptedString,
            totp = totp?.encryptedString,
            passwordRevisionDate = passwordRevisionDate,
            uris = uris.map { uri -> uri.toLoginUriData() }
        )
    }
}