package isel.leic.tds.galo

import isel.leic.tds.galo.model.*
import isel.leic.tds.galo.ui.*

fun main() {
    var game = Galo()
    val cmds = getCommands()
    while (true) {
        val (name,args) = readCommand()
        val cmd = cmds[name]
        if (cmd==null) println("Invalid Command $name")
        else try {
            game = cmd.action(game,args) ?: break
            cmd.show(game)
        } catch (ex: Exception) {
            println(ex.message)
            if (ex is IllegalArgumentException)
                println("Use: $name ${cmd.argsSyntax}")
        }
    }
    println("BYE.")
}