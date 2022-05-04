// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import isel.leic.tds.galo.model.*
import isel.leic.tds.galo.storage.FileStorage
import isel.leic.tds.galo.storage.MongoStorage
import isel.leic.tds.galo.storage.Storage
import isel.leic.tds.mongoDB.MongoDriver

const val CELL_SIZE = 80

@Composable
fun CellView(player: Player?, onClick: () -> Unit) {
    val imageName = when(player) {
        Player.CIRCLE -> "circle"
        Player.CROSS -> "cross"
        else -> {
            Box(Modifier
                .size(CELL_SIZE.dp).background(Color.White)
                .clickable( onClick = onClick )
            )
            return
        }
    }
    Image(painterResource("$imageName.png"),imageName,
        Modifier.size(CELL_SIZE.dp).background(Color.White))
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

@Composable
fun StatusView(game: Galo?) {
    Text("Status")
}

@Composable
fun FrameWindowScope.GaloMenu(
    onExit: ()->Unit, onStart: ()->Unit, onRefresh: ()->Unit
) {
    MenuBar {
        Menu("Game") {
            Item("Start", onClick = onStart)
            Item("Refresh", onClick = onRefresh)
            Item("Exit", onClick = onExit)
        }
    }
}

@Composable
@Preview
fun FrameWindowScope.GaloApp(storage: Storage, onExit: ()->Unit ) {
    var game by remember { mutableStateOf<Galo?>(null) }
    MaterialTheme {
        GaloMenu(
            onExit = onExit,
            onStart = { game = startGame("game1",storage) },
            onRefresh = { game?.let { game = storage.load(it) } }
        )
        Column {
            BoardView(game?.board) { pos ->
                game?.let {
                    val (g, error) = it.play(pos, storage)
                    if (error==PlayError.NONE)
                        game = g
                }
            }
            StatusView(game)
        }
    }
}

fun main()  { // = MongoDriver().use { drv ->
    application(exitProcessOnExit = false) {
        Window(
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
