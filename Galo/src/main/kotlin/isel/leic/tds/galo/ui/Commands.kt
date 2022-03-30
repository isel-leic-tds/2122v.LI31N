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
    abstract fun action(game: Galo, args:List<String>): Galo?

    /**
     * Presentation of command result.
     * @param game Actual game state.
     */
    open fun show(game: Galo) { }

    /**
     * Description of the syntax of the arguments
     */
    open val argsSyntax = ""
}

/**
 * Creates the associative map of game commands that associates the name of the command to its representation.
 */
fun getCommands() = mapOf<String,Command>(
    "GRID" to object:Command() {
        override fun action(game: Galo, args: List<String>) = game
        override fun show(game: Galo) { println("Grid") }
    },
    "EXIT" to object:Command() {
        override fun action(game: Galo, args: List<String>) = null
    },
    "START" to object:Command() {
        override fun action(game: Galo, args: List<String>): Galo {
            require(args.isNotEmpty()) { "Missing game name" }
            return game
        }
        override val argsSyntax = "<gameName>"
    }
)
