package me.gulya.bitwarden.app.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginComponent() {
    val textFieldValue = remember { mutableStateOf("") }
    Column(
        Modifier.fillMaxSize().then(Modifier.padding(16.dp)),
        Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        TextField(
            value = textFieldValue.value,
            onValueChange = { newValue -> textFieldValue.value = newValue },
            label = {
                Text("")
            }
        )
        Button(modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
//                count.value++
            }) {
//            Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
        }
        Button(modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
//                count.value = 0
            }) {
            Text("Reset")
        }
    }
}