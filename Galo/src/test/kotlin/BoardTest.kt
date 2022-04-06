import isel.leic.tds.galo.model.*
import kotlin.test.*

class BoardTest {
    @Test
    fun `Player normal usage`() {
        assertEquals('X', Player.CROSS.symbol)
        assertEquals('O',Player.CIRCLE.symbol)
        assertSame(Player.CIRCLE, Player.CROSS.other())
    }
    @Test
    fun `Position normal usage`() {
        val sut = Position(1,2)
        assertEquals(BOARD_DIM+2, sut.index)
        assertEquals(1,sut.lin)
        assertEquals(2,sut.col)
    }
    @Test
    fun `Board normal usage`() {
        val board = Board().play(Position(1,1),Player.CROSS)
        assertNotNull(board)
        assertEquals(Player.CROSS, board.get(Position(1,1)))
        assertEquals(null, board.get(Position(0,0)))
    }
    @Test
    fun `Winner diagonal 1`() {
        assertEquals(Player.CROSS, plays(0,1,4,3,8).winner)
    }
    @Test
    fun `Winner diagonal 2`() {
        assertEquals(Player.CROSS, plays(2,0,4,1,6).winner)
    }
    @Test
    fun `Winner line`() {
        assertEquals(Player.CROSS, plays(3,0,4,1,5).winner)
    }
    @Test
    fun `Winner column`() {
        assertEquals(Player.CROSS, plays(0,1,3,2,6).winner)
    }
    private fun plays(vararg indexes: Int) =
        indexes.fold(Board()) { board, idx -> board.addMove(Position.values[idx]) }
}