package isel.leic.tds.galo.storage

import isel.leic.tds.galo.model.*
import isel.leic.tds.mongoDB.*
import java.io.File

class FileStorage : Storage {
    private fun getFile(name: String) = File("$name.txt")

    override fun start(name: String): Player {
        val file = getFile(name)
        if (file.exists()) {
            val indexes = file.readLines().map { it.toInt() }
            if (indexes.size <= 1) return Player.CIRCLE
            file.delete()
        }
        file.writeText("")
        return Player.CROSS
    }

    override fun save(game: Galo) {
        getFile(game.name).writeText(game.board.moves
            .joinToString("\n") { it.pos.index.toString() }
        )
    }

    override fun load(game: Galo): Galo = with(game) {
        val indexes = getFile(name).readLines().map { it.toInt() }
        if (indexes.size == board.moves.size)  game
        else copy( board = board.addMove(indexes.last().toPosition()))
    }
}