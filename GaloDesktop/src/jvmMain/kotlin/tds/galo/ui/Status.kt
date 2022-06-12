package tds.galo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.leic.tds.galo.model.BOARD_DIM
import isel.leic.tds.galo.model.Galo
import isel.leic.tds.galo.model.isYourTurn

/**
 * Shows the game status bar.
 * The name of the game, whose turn it is and who is the player.
 */
@Composable
fun StatusView(state: GameState) = Row(
    Modifier.width((4.dp+CELL_SIZE.dp)* BOARD_DIM),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
) {
    with(state.game) {
        Text(if (this==null) "Game not started" else "Game: $name")
        if (this!=null) {
            if (board.winner==null)
                Text(if (isYourTurn) "Its your turn" else "wait for other")
            else
                Text("You ${if (board.winner==player) "Win" else "Lose"}")
            CellView(player,null)
        }
        if (state.jobAutoRefresh!=null) CircularProgressIndicator()
    }
}

