package isel.leic.tds.galo.ui

import isel.leic.tds.galo.model.*
import isel.leic.tds.galo.storage.Storage

/**
 * Represents a command.
 */
class CommandFP(
    /**
     * Operation to be performed in the game with the indicated arguments.
     * @param game Actual game state.
     * @param args Arguments passed to command.
     * @return The game with the changes made or null if it is to end.
     */
    val action: (game: Galo?, args:List<String>) -> Galo?,

    /**
     * Presentation of command result.
     * @param game Actual game state.
     */
    val show: (game: Galo) -> Unit = { game -> printBoard(game.board) },

    /**
     * Description of the syntax of the arguments
     */
    val argsSyntax: String = ""
)

/**
 * Creates the associative map of game commands that associates the name of the command to its representation.
 */
fun getCommandsFP(st: Storage) = mapOf(
    "GRID" to CommandFP(
        action = { game, _ ->
            checkNotNull(game) { "Game not started yet" }
            game
        }
    ),
    "PLAY" to CommandFP(
        action = { game, args -> playAction(game, args, st) },
        argsSyntax = "<position>"
    ),
    "EXIT" to CommandFP(
        action = { _, _ -> null }
    ),
    "START" to CommandFP(
        action = { game, args -> startAction(game, args, st) },
        argsSyntax = "<gameName>"
    ),
    "REFRESH" to CommandFP(
        action =  { game, _ ->
            checkNotNull(game) { "Game not started yet" }
            st.load(game)
        }
    )
)

