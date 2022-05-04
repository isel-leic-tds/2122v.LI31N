package isel.leic.tds.galo.ui

import isel.leic.tds.galo.model.*

val sepLine = "\n"+"---+".repeat(BOARD_DIM-1)+"---"

fun Player?.toChar() = this?.symbol ?: ' '

fun printBoard(board: Board) {
    Position.values.forEach { pos ->
        print(" ${board.get(pos).toChar()} ")
        if (pos.col == BOARD_DIM-1)
            if (pos.lin < BOARD_DIM-1) println(sepLine)
            else println()
        else
            print("|")
    }
    board.winner?.apply{ println("Player $symbol wins.") }
}
/*
 O |   |
---+---+---
 X | O |
---+---+---
 O |   | X
 */