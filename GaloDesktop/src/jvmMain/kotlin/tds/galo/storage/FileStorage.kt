package isel.leic.tds.galo.storage

import isel.leic.tds.galo.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Rename library operations for simulation.
 */
import kotlin.io.writeText as writeTextBase
import kotlin.io.readLines as readLinesBase

//region Simulation of blocking IO operations on files
fun waiting(label: String, time: Long) {
    print("$label:")
    repeat((time/500).toInt()) {
        Thread.sleep(500)
        print('.')
    }
    println("Done.")
}

fun File.writeText(txt: String) = writeTextBase(txt).also { waiting("Writing",3000) }

fun File.readLines() = readLinesBase().also { waiting("Reading",2000) }
//endregion

class FileStorage : Storage {
    private fun getFile(name: String) = File("$name.txt")

    override suspend fun start(name: String): Player  = withContext(Dispatchers.IO) {
        val file = getFile(name)
        if (file.exists()) {
            val indexes = file.readLines().map { it.toInt() }
            if (indexes.size <= 1) return@withContext Player.CIRCLE
            file.delete()
        }
        file.writeText("")
        Player.CROSS
    }

    override suspend fun save(game: Galo) = withContext(Dispatchers.IO) {
        getFile(game.name).writeText(game.board.moves
            .joinToString("\n") { it.pos.index.toString() }
        )
    }

    override suspend fun load(game: Galo): Galo = withContext(Dispatchers.IO) {
        with(game) {
            val indexes = getFile(name).readLines().map { it.toInt() }
            if (indexes.size == board.moves.size) game
            else copy(board = board.addMove(indexes.last().toPosition()))
        }
    }
}