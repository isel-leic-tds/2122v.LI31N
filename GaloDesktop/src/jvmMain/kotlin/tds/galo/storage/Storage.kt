package isel.leic.tds.galo.storage

import isel.leic.tds.galo.model.*

/**
 * Basic operations for persistence of game information.
 */
interface Storage {
    /**
     * Starts a new game in the database (without plays),
     * returning the player who is playing (CROSS if it is the first to start or CIRCLE if it is the second).
     * If a game with this [name] already exists, a new one is created if the old game has multiple moves.
     * @param name The name of the game.
     * @return The player on this side of the game.
     */
    suspend fun start(name: String) :Player

    /**
     * Update game state in database.
     * @param game Game information to store (including game name)
     */
    suspend fun save(game: Galo)

    /**
     * Loads the new game state from the information stored in the database.
     */
    suspend fun load(game: Galo): Galo
}