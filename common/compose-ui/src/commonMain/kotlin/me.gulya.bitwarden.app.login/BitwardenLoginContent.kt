package me.gulya.bitwarden.app.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.asState
import me.gulya.bitwarden.app.common.login.BitwardenLogin
import me.gulya.bitwarden.crypto.normalizePassword

@Composable
fun BitwardenLoginContent(component: BitwardenLogin) {
    val state by component.state.asState()

    Column(
        Modifier.fillMaxSize().then(Modifier.padding(16.dp)),
        Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        TextField(
            value = state.email,
            onValueChange = { newValue -> component.emailChanged(newValue) },
            label = {
                Text("Email")
            }
        )
        TextField(
            value = state.password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { newValue -> component.passwordChanged(normalizePassword(newValue)) },
            label = {
                Text("Password")
            }
        )
        Row(
            Modifier.semantics(mergeDescendants = true) {}
        ) {
            Checkbox(
                checked = state.customServerCheckboxChecked,
                onCheckedChange = { checked -> component.customServerCheckedChanged(checked) },
            )
            Text("Use custom server address")
        }
        if (state.customServerAddressVisible) {
            TextField(
                value = state.customServerAddress,
                onValueChange = { newValue -> component.customServerAddressChanged(newValue) },
                label = {
                    Text("Server Address")
                }
            )
        }

        val error = state.error
        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 20.sp,
            )
        }

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                component.loginButtonPressed()
            }) {
            Text("Login")
        }
    }
    if (state.loading) {
        Box(
            Modifier.fillMaxSize(),
        ) {
            CircularProgressIndicator(
                Modifier.size(48.dp).then(Modifier.align(Alignment.Center))
            )
        }
    }
}