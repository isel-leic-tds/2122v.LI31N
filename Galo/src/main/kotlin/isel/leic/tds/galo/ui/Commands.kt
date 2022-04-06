package isel.leic.tds.galo.ui

import isel.leic.tds.galo.model.*

/**
 * Represents a command.
 */
abstract class Command {
    /**
     * Operation to be performed in the game with the indicated arguments.
     * @param game Actual game state.
     * @param args Arguments passed to command.
     * @return The game with the changes made or null if it is to end.
     */
    abstract fun action(game: Galo?, args:List<String>): Galo?

    /**
     * Presentation of command result.
     * @param game Actual game state.
     */
    open fun show(game: Galo) { printBoard(game.board) }

    /**
     * Description of the syntax of the arguments
     */
    open val argsSyntax = ""
}

/**
 * Creates the associative map of game commands that associates the name of the command to its representation.
 */
fun getCommands() = mapOf(
    "GRID" to object:Command() {
        override fun action(game: Galo?, args: List<String>) = game
    },
    "PLAY" to object:Command() {
        override fun action(game: Galo?, args: List<String>): Galo {
            checkNotNull(game) { "Game unstarted" }
            require(args.isNotEmpty()) { "Missing position" }
            val pos = args[0].toIntOrNull()?.toPositionOrNull() ?:
                       throw IllegalArgumentException("Illegal position ${args[0]}")
            val result = game.board.play(pos, game.board.turn) ?:
                throw IllegalStateException("Invalid play")
            return game.copy(board = result)
        }
        override val argsSyntax = "<position>"
    },
    "EXIT" to object:Command() {
        override fun action(game: Galo?, args: List<String>): Nothing? = null
    },
    "START" to object:Command() {
        override fun action(game: Galo?, args: List<String>): Galo {
            require(args.isNotEmpty()) { "Missing game name" }
            check(game==null) { "game started" }
            return Galo(Board(),Player.CROSS,args[0])
        }
        override val argsSyntax = "<gameName>"
    }
)
