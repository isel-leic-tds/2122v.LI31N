// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    log("App")
    var text by remember{ mutableStateOf("Hello, World!") }
    Button(onClick = {
        text += " Desktop!"
        log("text = $text")
    }) {
        log("Button")
        Text(text)
    }
}

fun log(msg: String) = println("$msg: ${Thread.currentThread().name}")

fun main() {
    log("main start")
    application(exitProcessOnExit = false) {
        log("app")
        Window(onCloseRequest = ::exitApplication) {
            MaterialTheme {
                App()
            }
        }
        log("app end")
    }
    log("main end")
}
