package tennis.first

import tennis.Player

enum class Points(val number: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40), GAME(45);
    fun advance() = values()[ordinal+1]  // throws IndexOutOfBounds
}

class Score(private val pointsOfA : Points, private val pointsOfB : Points) {
    fun isGame() =
        pointsOfA=== Points.GAME && pointsOfB!== Points.FORTY ||
        pointsOfB=== Points.GAME && pointsOfA!== Points.FORTY
    private fun pointsOf(p: Player) = if (p===Player.A) pointsOfA else pointsOfB
    val display: String = when {
        pointsOfA=== Points.FORTY && pointsOfB=== Points.FORTY -> "Deuce"
        pointsOfA=== Points.GAME && pointsOfB=== Points.FORTY -> "Advantage A"
        pointsOfB=== Points.GAME && pointsOfA=== Points.FORTY -> "Advantage B"
        isGame()-> "Game of ${if (pointsOfA=== Points.GAME) 'A' else 'B'}"
        else -> "${pointsOfA.number} - ${pointsOfB.number}"
    }
    fun next(win: Player) : Score = when {
        pointsOfA=== Points.FORTY && pointsOfB=== Points.FORTY -> // Deuce
            if (win===Player.A) Score(Points.GAME,pointsOfB)
            else Score(pointsOfA, Points.GAME)
        pointsOfA=== Points.GAME && pointsOfB=== Points.FORTY -> // Adv A
            if (win==Player.A) Score(Points.GAME, Points.LOVE)
            else Score(Points.FORTY, Points.FORTY)
        pointsOfB=== Points.GAME && pointsOfA=== Points.FORTY -> // Adv B
            if (win==Player.A) Score(Points.FORTY, Points.FORTY)
            else Score(Points.LOVE, Points.GAME)
        else ->
            if (win==Player.A) Score(pointsOfA.advance(),pointsOfB)
            else Score(pointsOfA,pointsOfB.advance())
    }
}

val InitialScore = Score(Points.LOVE, Points.LOVE)
