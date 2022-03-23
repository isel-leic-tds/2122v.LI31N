package tennis.fp

import tennis.Player

enum class Points(val number: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30);
    fun advance() = values()[ordinal+1]  // throws IndexOutOfBounds
}

sealed class Score
open class ByPoints(val pointsOfA: Points, val pointsOfB: Points) : Score()
object Deuce : Score()
class Game(val winner:Player) : Score()
class Forty(val player: Player, val pointsOfOther: Points) :Score()
class Advantage(val player: Player) : Score()
object InitialScore : ByPoints(Points.LOVE, Points.LOVE)

fun Score.isGame() = this is Game

val Score.display get() = when(this) {
    is ByPoints -> "${pointsOfA.number} - ${pointsOfB.number}"
    Deuce -> "Deuce"
    is Game -> "Game of $winner"
    is Advantage -> "Advantage of $player"
    is Forty -> if(player===Player.A) "40 - ${pointsOfOther.number}"
                else "${pointsOfOther.number} - 40"
}

fun ByPoints.pointsOf(p: Player) = if (p===Player.A) pointsOfA else pointsOfB

fun Score.next(win: Player) = when(this) {
    is Game -> throw IllegalStateException()
    Deuce -> Advantage(win)
    is ByPoints -> when {
        pointsOf(win) === Points.THIRTY -> Forty(win, pointsOf(win.other()))
        else ->
            if (win===Player.A) ByPoints(pointsOfA.advance(), pointsOfB)
            else ByPoints(pointsOfA, pointsOfB.advance())
    }
    is Advantage -> if (win===player) Game(win) else Deuce
    is Forty -> when {
        win===player -> Game(win)
        pointsOfOther===Points.THIRTY -> Deuce
        else -> Forty(player, pointsOfOther.advance())
    }
}