package tds.galo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import isel.leic.tds.galo.model.Galo

/**
 * Menu of the game.
 */
@Composable
fun FrameWindowScope.GaloMenu( state: GameState, onExit: ()->Unit) {
    MenuBar {
        Menu("Game") {
            Item("Start", enabled = state.game==null, onClick = { state.start() } )
            // TODO: Comment next line when auto-refresh is implemented.
            Item("Refresh", enabled = state.game!=null, onClick = { state.refresh() })
            Item("Exit", onClick = onExit)
        }
    }
}

