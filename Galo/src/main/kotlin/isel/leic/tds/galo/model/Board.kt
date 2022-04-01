package isel.leic.tds.galo.model

const val BOARD_DIM = 3

/**
 * Represents each player in the game
 */
enum class Player(val symbol: Char) {
    CROSS('X'), CIRCLE('O');
    fun other() = if (this===CROSS) CIRCLE else CROSS
}

/**
 * Represents each position on the board grid
 * @property index Index in the grid from the upper left corner to the lower right corner.
 */
class Position private constructor(val index: Int) {
    val lin get() = index / BOARD_DIM
    val col get() = index % BOARD_DIM
    companion object {
        val values = List(BOARD_DIM*BOARD_DIM) { Position(it) }
        operator fun invoke(lin:Int, col:Int) = values[lin*BOARD_DIM+col]
    }
}

/**
 * Represents a move made by one of the players.
 * @property pos The move position on the grid.
 * @property player The player who made the move.
 */
data class Move(val pos: Position, val player: Player)

/**
 * Represents the game board.
 * @property moves Plays already made.
 * @property turn The next player to play.
 */
data class Board(val moves: List<Move> = emptyList(), val turn:Player = Player.CROSS)

/**
 * Makes a move by player [player] in position [pos].
 * @receiver The current board.
 * @return The board with the new move or null if this move is not possible.
 */
fun Board.play(pos: Position, player: Player): Board? =
    if (player!=turn || moves.any{ it.pos===pos }) null
    else Board( moves + Move(pos,turn), turn.other() )

/**
 * Returns the player who played in position [pos]
 * @return The player or null if position [pos] is free
 */
fun Board.get(pos: Position): Player? =
    moves.firstOrNull { it.pos===pos }?.player
