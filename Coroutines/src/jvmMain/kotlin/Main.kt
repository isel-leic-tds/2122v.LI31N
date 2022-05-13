// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import java.awt.EventQueue
import javax.swing.SwingUtilities
import kotlin.concurrent.thread

@Composable
@Preview
fun App() {
    var value by remember { mutableStateOf(0) }
    MaterialTheme {
        Column( horizontalAlignment = Alignment.CenterHorizontally ) {
            log("App Column")
            Text("Counter = $value")
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    log("Increment")
                    value++
                }) { Text("Increment") }
                Button(onClick = {
                    log("Compute")
                    computeValue(value) {
                        EventQueue.invokeLater {
                            log("Apply computed value")
                            value += it
                        }
                    }
                }) { Text("Compute value")}
            }
        }
    }
}

fun computeValue(n: Int, oper: (Int)->Unit) {
    log("Compute value start")
    thread(name = "Compute $n") {
        log("Compute thread")
        Thread.sleep(5000)
        oper( (1..10).random() )
    }
    log("ComputeValue end")
}

fun log(message: String) =
    println("LOG: $message in thread=${Thread.currentThread().name}")

fun main() {
    log("Start App")
    application(exitProcessOnExit = false) {
        Window(
            state = WindowState(height = Dp.Unspecified, width = 300.dp),
            onCloseRequest = ::exitApplication
        ) {
            App()
        }
    }
    log("End App")
}
