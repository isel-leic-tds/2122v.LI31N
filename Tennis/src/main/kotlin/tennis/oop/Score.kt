package tennis.oop

import tennis.Player

enum class Points(val number: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40);
    fun advance() = values()[ordinal+1]  // throws IndexOutOfBounds
}

abstract class Score {
    abstract val display: String
    open fun isGame() = false
    abstract fun next(win: Player) :Score
}

private class Game(private val winner: Player) : Score() {
    override val display = "Game of $winner"
    override fun isGame() = true
    override fun next(win: Player) = throw IllegalStateException()
}

class ByPoints (private val pointsOfA:Points, private val pointsOfB:Points) : Score() {
    override val display = "${pointsOfA.number} - ${pointsOfB.number}"
    private fun pointsOf(p: Player) = if (p===Player.A) pointsOfA else pointsOfB
    override fun next(win: Player) = when {
        pointsOf(win)===Points.THIRTY && pointsOf(win.other())===Points.FORTY -> Deuce
        pointsOf(win)===Points.FORTY -> Game(win)
        else ->
            if (win===Player.A) ByPoints(pointsOfA.advance(), pointsOfB)
            else ByPoints(pointsOfA, pointsOfB.advance())
    }
}

private class Advance(private val player: Player) : Score() {
    override val display = "Advance of $player"
    override fun next(win: Player) =
        if (win===player) Game(win) else Deuce
}

private object Deuce : Score() {
    override val display = "Deuce"
    override fun next(win: Player) = Advance(win)
}

val InitialScore = ByPoints(Points.LOVE, Points.LOVE)
