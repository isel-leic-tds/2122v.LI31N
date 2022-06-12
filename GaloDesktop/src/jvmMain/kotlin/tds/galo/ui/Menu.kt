package tds.galo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar

/**
 * Menu of the game.
 */
@Composable
fun FrameWindowScope.GaloMenu( state: GameState, onExit: ()->Unit) {
    MenuBar {
        Menu("Game") {
            Item("Start", enabled = state.game==null, onClick = { state.start() } )
            Item("Refresh", enabled = state.jobAutoRefresh==null && state.game!=null, onClick = { state.refresh() })
            Item("Cancel Auto-refresh", enabled = state.jobAutoRefresh!=null, onClick = { state.cancelAutoRefresh() })
            Item("Exit", onClick = onExit)
        }
    }
}

