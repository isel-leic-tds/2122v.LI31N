package isel.leic.tds.galo.model

import isel.leic.tds.galo.storage.Storage

/**
 * Represents the game from one player's point of view.
 */
data class Galo(
    val board: Board = Board(),
    val player: Player,
    val name: String
)

/**
 * Starts a new game with the player CROSS, if he is the first to start,
 * or with the player CIRCLE, if he is the second.
 * @param name Name of the game to start
 * @param st Access to persistent storage operations
 * @return The created game
 */
fun startGame(name: String, st: Storage): Galo {
    val player = st.start(name)
    val game = Galo(Board(), player, name)
    return if (player == Player.CIRCLE) st.load(game)
           else game
}

/**
 * Error types for play operation.
 */
enum class PlayError{ NONE, INVALID_TURN, OCCUPIED_POSITION, GAME_OVER }

/**
 * Result of play operation.
 * Returns a new game if there was no error.
 * If there is an error (error!=PlayError.NONE) the game is the same.
 */
data class PlayResult(val game: Galo, val error: PlayError)

/**
 * Attempt to make a move by the player on this side of the game at the indicated position.
 * @param pos Play position
 * @param st Access to persistent storage operations
 * @return A new game if the play is valid or an error that identifies the problem.
 */
fun Galo.play(pos: Position, st: Storage): PlayResult {
    val boardRes = board.play(pos, player)
    return if (boardRes==null) PlayResult(this, when {
        board.turn!=player -> PlayError.INVALID_TURN
        board.winner!=null -> PlayError.GAME_OVER
        board[pos] != null -> PlayError.OCCUPIED_POSITION
        else -> error("Unexpected Error")
    })
    else PlayResult(copy(board = boardRes).also { st.save(it) },
        PlayError.NONE
    )
}