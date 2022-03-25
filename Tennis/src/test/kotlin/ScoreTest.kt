import tennis.Player
import tennis.fp.*
import kotlin.test.*

class ScoreTest {
    @Test
    fun `observe initial score`(){
        val sut = InitialScore
        assertEquals("0 - 0", sut.display)
        assertFalse(sut.isGame())
        assertEquals("15 - 0", sut.next(Player.A).display)
    }
    private fun Score.play(wins: String): Score =
        wins.fold(this){ acc, w -> acc.next(Player.valueOf(w.toString())) }

    @Test
    fun `deuce condition`() {
        val sut = InitialScore.play("ABAABB")
        assertEquals("Deuce",sut.display)
        assertFalse(sut.isGame())
        assertEquals(sut, InitialScore.play("AB".repeat(3)))
        assertEquals("Advantage of A",sut.next(Player.A).display)
    }
    @Test
    fun `victory of A`() {
        val sut = InitialScore.play("AAAA")
        assertTrue(sut.isGame())
        assertEquals("Game of A",sut.display)
        assertEquals(InitialScore.play("AAABA"),sut)
    }
    @Test
    fun `test Advantage`() {
        val sut = InitialScore.play("AB".repeat(3)+'A')
        assertEquals("Advantage of A",sut.display)
        assertEquals("Advantage of B",sut.play("BB").display)
    }
}

