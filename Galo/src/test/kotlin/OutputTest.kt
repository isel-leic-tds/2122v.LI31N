import isel.leic.tds.galo.model.*
import isel.leic.tds.galo.ui.printBoard
import kotlin.test.*

class OutputTest {
    @Test
    fun printBoardTest() {
        printBoard( Board().addMove(Position(1,1)) )
    }
}