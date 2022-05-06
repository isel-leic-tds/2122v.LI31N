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

const val CELL_SIZE = 80

@Composable
fun CellView(player: Player?, onClick: () -> Unit) {
    val modifier = Modifier.size(CELL_SIZE.dp).background(Color.White)
    val imageName = when(player) {
        Player.CIRCLE -> "circle"
        Player.CROSS -> "cross"
        else -> {
            Box( modifier.clickable( onClick = onClick ) )
            return // ATENÇÃO: Isto não é boa prática
        }
    }
    Image(painterResource("$imageName.png"),imageName, modifier)
}

@Composable
fun BoardView(board: Board?, onClick: (Position)->Unit) {
    Column(Modifier.background(Color.Black)) {
        repeat(BOARD_DIM) { line ->
            if (line!=0) Spacer(Modifier.height(4.dp))
            Row {
                repeat(BOARD_DIM) { col ->
                    if (col!=0) Spacer(Modifier.width(4.dp))
                    val pos = Position(line,col)
                    CellView(board?.get(pos)) {
                        onClick(pos)
                    }
                }
            }
        }
    }
}