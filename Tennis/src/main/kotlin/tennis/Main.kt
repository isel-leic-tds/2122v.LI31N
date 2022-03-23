package tennis

//import tennis.first.*
//import tennis.oop.*
import tennis.fp.*

fun main() {
    var game : Score = InitialScore
    while (true) {
        println( game.display )
        if ( game.isGame() ) break
        game = game.next( readWinPlayer() )
    }
}

enum class Player { A, B;
    fun other() = if (this==A) B else A
}

fun readWinPlayer(): Player {
    print("Player A win? (y/n) ")
    val answer = readln().first()
    return if (answer in "Yy") Player.A else Player.B
}

