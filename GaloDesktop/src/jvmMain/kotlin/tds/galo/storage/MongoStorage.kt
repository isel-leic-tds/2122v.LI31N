package isel.leic.tds.galo.storage

import isel.leic.tds.galo.model.*
import isel.leic.tds.mongoDB.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MongoStorage(private val driver: MongoDriver) : Storage {
    /**
     * Representation of the game state in a document.
     */
    private data class Doc(val _id: String, val moves: List<Int>)

    /**
     * The collection with all the games.
     */
    private val col = driver.getCollection<Doc>("games")

    override suspend fun start(name: String): Player = withContext(Dispatchers.IO)  {
        val doc = col.getDocument(name)
        if (doc!=null) {
            if (doc.moves.size <= 1) return@withContext Player.CIRCLE
            col.deleteDocument(name)
        }
        col.insertDocument(Doc(name, emptyList()))
        Player.CROSS
    }

    override suspend fun save(game: Galo) = withContext<Unit>(Dispatchers.IO) {
        col.replaceDocument(Doc(game.name,game.board.moves.map { it.pos.index }))
    }

    override suspend fun load(game: Galo): Galo = withContext(Dispatchers.IO) {
        val doc = col.getDocument(game.name)
        checkNotNull(doc) { "no document in load" }
        if (doc.moves.size == game.board.moves.size) game
        else game.copy( board = game.board.addMove(doc.moves.last().toPosition()))
    }
}