package tds.galo.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import isel.leic.tds.galo.model.*
import isel.leic.tds.galo.storage.Storage

/**
 * Current state and logic of the UI. (ViewModel)
 * ...
 */
class GameState(val storage: Storage) {
    var game by mutableStateOf<Galo?>(null)
        private set
    var openDialogName by mutableStateOf(false)
        private set
    var message by mutableStateOf<String?>(null)
        private set

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
            else
                message = when(error) {
                    PlayError.GAME_OVER -> "Game over"
                    PlayError.INVALID_TURN -> "Is not your turn"
                    PlayError.OCCUPIED_POSITION -> "Invalid play"
                    else -> null
                }
        }
    }
    fun closeDialog() {
        openDialogName = false
    }
    fun messageAck() {
        message = null
    }
}