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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
@Preview
fun App() {
    var value by remember { mutableStateOf(0) }
    var job by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()
    MaterialTheme {
        Column( horizontalAlignment = Alignment.CenterHorizontally ) {
            Text("Counter = $value")
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    //log("Increment")
                    value++
                }) { Text("Increment") }
                Button(enabled = job==null, onClick = {
                    log("Compute")
                    job = scope.launch {
                        value += computeValue(value)
                        job = null
                    }
                }) { Text("Compute value")}
                Button(enabled = job!=null, onClick ={
                    job?.cancel()
                    job = null
                })  { Text("Cancel")}
            }
        }
    }
}

suspend fun computeValue(n: Int): Int {
    log("ComputeValue start")
    val res = withContext(Dispatchers.IO) {
        log("Compute thread")
        Thread.sleep(5000)
        log("After sleep")
        (1..10).random()
    }
    log("ComputeValue end")
    return res
}

fun log(message: String) =
    println("LOG: $message in thread=${Thread.currentThread().name}")

fun main() {
    log("Start App")
    application(exitProcessOnExit = false) {
        Window(
            state = WindowState(height = Dp.Unspecified, width = 400.dp),
            onCloseRequest = ::exitApplication
        ) {
            App()
        }
    }
    log("End App")
}
