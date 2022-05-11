package tds.galo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import isel.leic.tds.galo.model.*

const val CELL_SIZE = 120

/**
 * Shows a cell of the game grid with the corresponding move [player] or empty.
 * Executes [onClick] if there is a click in the area.
 */
@Composable
fun CellView(player: Player?, onClick: (()->Unit)?) {
    val modifier = Modifier.size(CELL_SIZE.dp).background(Color.White)
    Box( if (onClick==null) modifier else modifier.clickable(onClick = onClick) ) {
        player?.let {
            val imageName = when (it) {
                Player.CIRCLE -> "circle"
                Player.CROSS -> "cross"
            }
            Image(painterResource("$imageName.png"), imageName, Modifier.fillMaxSize())
        }
    }
}

/**
 * Shows the [board] grid of the game.
 * Calls [onClick] if one cell grid was clicked.
 */
@Composable
fun BoardView(board: Board?, onClick: ((Position)->Unit)?) {
    Column(Modifier.background(Color.Black)) {
        repeat(BOARD_DIM) { line ->
            if (line!=0) Spacer(Modifier.height(4.dp))
            Row {
                repeat(BOARD_DIM) { col ->
                    if (col!=0) Spacer(Modifier.width(4.dp))
                    val pos = Position(line,col)
                    val fx: ()->Unit = { onClick?.invoke(pos) }
                    CellView(
                        board?.get(pos),
                        if (onClick!=null) fx else null
                    )
                }
            }
        }
    }
}