package tds.galo.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.leic.tds.galo.model.*
import isel.leic.tds.galo.storage.Storage
import kotlinx.coroutines.*

/**
 * Current state and logic of the UI. (ViewModel)
 * ...
 */
class GameState(val storage: Storage, val scope: CoroutineScope) {
    var game by mutableStateOf<Galo?>(null)
        private set
    var openDialogName by mutableStateOf(false)
        private set
    var message by mutableStateOf<String?>(null)
        private set
    var jobAutoRefresh by mutableStateOf<Job?>(null)
        private set

    fun refresh() {
        game?.let { scope.launch { game = storage.load(it) } }
    }
    fun start(name: String?=null) {
        if (name==null) openDialogName=true
        else {
            scope.launch {
                game = startGame(name, storage)
                openDialogName = false
                waitforOther() // Auto-refresh
            }
        }
    }

    /**
     * If necessary, launch a coroutine to do periodic readings until it's the turn to play.
     * To call at the start of the game and after each move.
     */
    private fun waitforOther() {
        var g = game ?: return
        if (g.isYourTurn || g.isOver) return
        jobAutoRefresh = scope.launch {
            do {
                delay(3000)
                g = storage.load(g)
            } while (!g.isYourTurn)
            game = g
            jobAutoRefresh = null
        }
    }

    fun cancelAutoRefresh() {
        jobAutoRefresh?.cancel()
        jobAutoRefresh = null
    }

    fun play(pos: Position) {
        game?.let {
            val (g, error) = it.play(pos, storage, scope)
            if (error== PlayError.NONE) {
                game = g
                waitforOther() // Auto-refresh
            }
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