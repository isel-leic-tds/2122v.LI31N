package isel.leic.tds.galo.ui

import isel.leic.tds.galo.model.*
import isel.leic.tds.galo.storage.Storage

/**
 * Analyze and execute the start command.
 */
fun startAction(game: Galo?,  args: List<String>, st:Storage): Galo {
    require(args.isNotEmpty()) { "Missing game name" }
    check(game == null) { "Game already started" }
    return startGame(args[0],st)
}

/**
 * Analyze and execute the play command.
 */
fun playAction(game: Galo?, args: List<String>, st: Storage): Galo {
    require(args.isNotEmpty()) { "Missing position" }
    checkNotNull(game) { "Game not started yet" }
    val pos = args[0].toIntOrNull()?.toPositionOrNull() ?:
       throw IllegalArgumentException("Illegal position ${args[0]}")
    val res = game.play(pos, st)
    if (res.error==PlayError.NONE) return res.game
    error( when(res.error) {
        PlayError.INVALID_TURN -> "Not your turn"
        PlayError.OCCUPIED_POSITION -> "Position occupied"
        PlayError.GAME_OVER -> "Game ended"
        else -> "Unknown error"
    })
}


