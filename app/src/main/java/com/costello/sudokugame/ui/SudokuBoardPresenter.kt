package com.costello.sudokugame.ui

import com.costello.sudokugame.data.SudokuBoard
import com.costello.sudokugame.data.SudokuBoardCell
import com.costello.sudokugame.ui.view.custom.SudokuBoardView
import java.util.*

class SudokuBoardPresenter(_view: SudokuBoardView) {
    val view: SudokuBoardView = _view
    var rowSelected: Int = -1
    var colSelected: Int = -1
    //TODO get from repo
    private val sudokuBoard: SudokuBoard = SudokuBoard(boardFromArray(generatePuzzle()))

    init {
        view.updateBoard(sudokuBoard)

        for(i in 0..8){
            for(j in 0..8){
                print(sudokuBoard.getValue(i,j))
            }
            println()
        }
    }

    fun updateCellValue(value: Int){
        sudokuBoard.setValue(rowSelected, colSelected, value)
        view.invalidate()
    }
    fun getCellValue(x: Int, y: Int) = sudokuBoard.getValue(x, y)

    fun updateCellSelected(rowSelected: Int, colSelected: Int) {
        this.rowSelected = rowSelected
        this.colSelected = colSelected
    }
}




//TODO repo code
fun boardFromArray(array: Array<IntArray>): List<SudokuBoardCell> {
    val sudokuBoard: MutableList<SudokuBoardCell> = mutableListOf()
    for (i in 0..8) {
        for (j in 0..8) {
            if(array[i][j] == 0) {
                sudokuBoard.add(SudokuBoardCell(i, j, array[i][j], false))
            }
            else {
                sudokuBoard.add(SudokuBoardCell(i, j, array[i][j], true))
            }
        }
    }
    return sudokuBoard
}

fun generatePuzzle(): Array<IntArray> {
    //TODO row col transmutations and shifts to generate random state from seed
    val puzzle =
        Array(9) { IntArray(9) }
    var counter = 0
    var x: Int
    var y: Int
    val random = Random()
    val puzzleSet =
        ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9))
    Collections.shuffle(puzzleSet)
    for (col in 0..8) {
        puzzle[0][col] = puzzleSet[col]
    }
    for (row in 1..8) {
        for (col in 0..8) {
            if (row % 3 == 0) {
                puzzle[row][col] = puzzle[row - 1][(col + 1) % 9]
            } else {
                puzzle[row][col] = puzzle[row - 1][(col + 3) % 9]
            }
        }
    }
    while (counter < 60) {
        x = random.nextInt(9)
        y = random.nextInt(9)
        if (puzzle[x][y] != 0) {
            puzzle[x][y] = 0
            counter++
        }
    }
    return puzzle
}