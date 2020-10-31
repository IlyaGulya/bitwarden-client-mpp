package me.gulya.bitwarden.domain.data

import com.soywiz.klock.DateTime
import me.gulya.bitwarden.data.LoginData

class Login(
    val username: EncryptedString?,
    val password: EncryptedString?,
    val totp: EncryptedString?,
    val passwordRevisionDate: DateTime?,
    val uris: List<LoginUri>,
) {
    constructor(obj: LoginData) : this(
        username = obj.username.toCipherString(),
        password = obj.password.toCipherString(),
        totp = obj.totp.toCipherString(),
        passwordRevisionDate = obj.passwordRevisionDate,
        uris = obj.uris.map { LoginUri(it) },
    )

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