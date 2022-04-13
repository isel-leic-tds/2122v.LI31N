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
    val diag1 get() = lin == col
    val diag2 get() = lin == BOARD_DIM -col -1
    companion object {
        val values = List(BOARD_DIM*BOARD_DIM) { Position(it) }
        operator fun invoke(lin:Int, col:Int) = values[lin*BOARD_DIM+col]
    }
}

/**
 * Converts an integer value (index) to a position.
 * @return The position or null if the index is invalid
 */
fun Int.toPositionOrNull() =
    if (this in 0 until BOARD_DIM*BOARD_DIM) Position.values[this]
    else null

fun Int.toPosition() = toPositionOrNull() ?: error("Invalid position")

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
data class Board(
    val moves: List<Move> = emptyList(),
    val turn: Player = Player.CROSS,
    val winner: Player? = null
)

/**
 * Makes a move by player [player] in position [pos].
 * @receiver The current board.
 * @return The board with the new move or null if this move is not possible.
 */
fun Board.play(pos: Position, player: Player): Board? =
    if (player!=turn || winner!=null || moves.any{ it.pos===pos }) null
    else addMove(pos)

/**
 * Add a move by the current turn player in position [pos].
 * @receiver The current board.
 * @return The board with the new move.
 */
fun Board.addMove(pos: Position): Board =
    Board( moves + Move(pos,turn), turn.other(), turn.takeIf { win(pos) } )

/**
 * Returns the player who played in position [pos]
 * @return The player or null if position [pos] is free
 */
operator fun Board.get(pos: Position): Player? =
    moves.firstOrNull { it.pos===pos }?.player

/**
 * Check if the move in the position [pos] of the current player causes a win.
 * @return true if causes a win
 */
fun Board.win(pos: Position) =
    moves.size >= 2*BOARD_DIM-2 &&
    with( moves.filter{it.player==turn}.map { it.pos } ) {
        count { it.lin == pos.lin } == BOARD_DIM-1 ||
        count { it.col == pos.col } == BOARD_DIM-1 ||
        pos.diag1 && count { it.diag1 } == BOARD_DIM-1 ||
        pos.diag2 && count { it.diag2 } == BOARD_DIM-1
    }


