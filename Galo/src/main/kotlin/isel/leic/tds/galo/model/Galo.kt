package isel.leic.tds.galo.model

/**
 * Represents the game from one player's point of view.
 */
data class Galo(
    val board: Board = Board(),
    val player: Player,
    val name: String
)