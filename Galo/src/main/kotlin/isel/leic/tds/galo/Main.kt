package isel.leic.tds.galo

import isel.leic.tds.galo.model.*
import isel.leic.tds.galo.storage.FileStorage
import isel.leic.tds.galo.storage.MongoStorage
import isel.leic.tds.galo.ui.*
import isel.leic.tds.mongoDB.MongoDriver

fun main() {
    // Creates and use the driver to MongoDB
    MongoDriver().use { drv ->
        // The game state (mutability point)
        var game: Galo? = null
        // Table of commands to use
        val cmds = getCommandsFP( MongoStorage(drv) ) //FileStorage() )

        // Command execution loop.
        while (true) {
            val (name, args) = readCommand()
            val cmd = cmds[name]
            if (cmd == null) println("Invalid Command $name")
            else try {
                game = cmd.action(game, args) ?: break
                cmd.show(game)
            } catch (ex: Exception) {
                println(ex.message)
                if (ex is IllegalArgumentException)
                    println("Use: $name ${cmd.argsSyntax}")
            }
        }
    }
    println("BYE.")
}