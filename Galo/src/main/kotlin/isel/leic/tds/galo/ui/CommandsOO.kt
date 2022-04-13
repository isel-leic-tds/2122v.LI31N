package isel.leic.tds.galo.ui

import isel.leic.tds.galo.model.*
import isel.leic.tds.galo.storage.Storage

/**
 * Represents a command in the Object Oriented technique.
 */
abstract class CommandOO {
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
 * Grid command object (Can be also an object created in the getCommandsOO function)
 */
object GridCmd : CommandOO() {
    override fun action(game: Galo?, args: List<String>): Galo {
        checkNotNull(game) { "Game not started yet" }
        return game
    }
}
/**
 * Creates the associative map of game commands that associates the name of the command to its representation.
 */
fun getCommandsOO(st: Storage) = mapOf(
    "GRID" to GridCmd,
    "PLAY" to object:CommandOO() {
        override fun action(game: Galo?, args: List<String>) = playAction(game, args, st)
        override val argsSyntax = "<position>"
    },
    "EXIT" to object:CommandOO() {
        override fun action(game: Galo?, args: List<String>): Nothing? = null
    },
    "START" to object:CommandOO() {
        override fun action(game: Galo?, args: List<String>) = startAction(game, args, st)
        override val argsSyntax = "<gameName>"
    },
    "REFRESH" to object:CommandOO() {
        override fun action(game: Galo?, args: List<String>): Galo {
            checkNotNull(game) { "Game not started yet" }
            return st.load(game)
        }
    }
)

