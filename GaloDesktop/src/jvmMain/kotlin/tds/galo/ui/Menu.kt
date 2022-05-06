package tds.galo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import isel.leic.tds.galo.model.Galo

@Composable
fun FrameWindowScope.GaloMenu( state: GameState,
    onExit: ()->Unit, onStart: ()->Unit
) {
    MenuBar {
        Menu("Game") {
            Item("Start", enabled = state.game==null, onClick = { state.start() } )
            Item("Refresh", enabled = state.game!=null, onClick = { state.refresh() })
            Item("Exit", onClick = onExit)
        }
    }
}

