package com.costello.sudokugame.data

class SudokuBoardCell(
    val x: Int,
    val y: Int,
    var value: Int,
    val baseCell: Boolean
)