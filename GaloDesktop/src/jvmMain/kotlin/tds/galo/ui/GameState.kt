package tds.galo.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import isel.leic.tds.galo.model.*
import isel.leic.tds.galo.storage.Storage

class GameState(val storage: Storage) {
    var game by mutableStateOf<Galo?>(null)
        private set
    var openDialogName by mutableStateOf(false)

    fun refresh() {
        game?.let { game = storage.load(it) }
    }
    fun start(name: String?=null) {
        if (name==null) openDialogName=true
        else {
            game = startGame(name,storage)
            openDialogName=false
        }
    }
    fun play(pos: Position) {
        game?.let {
            val (g, error) = it.play(pos, storage)
            if (error== PlayError.NONE)
                game = g
        }
    }
}