// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App2() {
    var text by remember{ mutableStateOf("Hello, World!") }
    Button(onClick = { text += " Desktop!" }) {
        Text(text)
    }
}

@Composable
fun Hello(name: String) {
    Text("Hello $name")
}

fun main() = application(exitProcessOnExit = false) {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Yellow)
                .padding(80.dp)
                .background(Color.Cyan),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =Arrangement.SpaceBetween
            ) {
                App2()
                Hello("ISEL")
            }
        }
    }
}
