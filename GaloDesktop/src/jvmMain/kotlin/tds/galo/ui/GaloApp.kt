package tds.galo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.FrameWindowScope
import isel.leic.tds.galo.storage.Storage

/**
 * Contents of the game's user interface window (Board and status bar).
 * Receives the [storage], created in the main function, and the [onExit] function to execute if the application was terminated.
 * Also creates the necessary menu and dialog windows.
 * Is a stateful composable to maintain the current state of the UI.
 */
@Composable
fun FrameWindowScope.GaloApp(storage: Storage, onExit: ()->Unit ) {
    val state = remember { GameState(storage) } // State of UI (ViewModel)
    MaterialTheme {
        GaloMenu(state, onExit = onExit)
        if (state.openDialogName)
            DialogName(onCancel = { state.closeDialog() }) { state.start(it) }
        state.message?.let {
            DialogMessage(it) { state.messageAck() }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BoardView(
                state.game?.board,
                onClick = state::play.takeIf { state.game != null }
            )
            StatusView(state)
        }
    }
}
