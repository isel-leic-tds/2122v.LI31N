package tds.galo.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.window.FrameWindowScope
import isel.leic.tds.galo.model.Galo
import isel.leic.tds.galo.model.PlayError
import isel.leic.tds.galo.model.play
import isel.leic.tds.galo.model.startGame
import isel.leic.tds.galo.storage.Storage

@Composable
@Preview
fun FrameWindowScope.GaloApp(storage: Storage, onExit: ()->Unit ) {
    val state = remember { GameState(storage) }
    MaterialTheme {
        GaloMenu(state,
            onExit = onExit,
            onStart = { state.openDialogName = true },
        )
        if (state.openDialogName)
            DialogName(
                onCancel= { state.openDialogName=false},
                onStart= { state.start(it) }
            )
        Column {
            BoardView(state.game?.board) { state.play(it) }
            StatusView(state)
        }
    }
}
