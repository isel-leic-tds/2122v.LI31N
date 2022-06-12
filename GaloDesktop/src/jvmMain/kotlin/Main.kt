// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import isel.leic.tds.galo.storage.FileStorage
import tds.galo.ui.GaloApp


fun main() { // = MongoDriver().use { drv ->
    application(exitProcessOnExit = false) {
        Window(
            title = "TDS Galo",
            onCloseRequest = ::exitApplication,
            state = WindowState(
                position = WindowPosition(Alignment.Center),
                size = DpSize.Unspecified
            )
        ) {
            GaloApp(FileStorage(), onExit=::exitApplication) //MongoStorage(drv))
        }
    }
}
