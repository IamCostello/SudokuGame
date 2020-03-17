package com.costello.sudokugame.data.repository

import com.costello.sudokugame.data.SudokuBoard
import com.costello.sudokugame.data.SudokuBoardCell
import java.util.*

object SudokuBoardRepository {
    fun generatePuzzle(): List<SudokuBoardCell> {
        val random = Random()

        val puzzle = Array(9) { IntArray(9) }
        val sudokuBoard: MutableList<SudokuBoardCell> = mutableListOf<SudokuBoardCell>()

        var counter = 0
        var x: Int
        var y: Int

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


        for (i in 0..8) {
            for (j in 0..8) {
                sudokuBoard.add(SudokuBoardCell(i, j, puzzle[i][j], puzzle[i][j] != 0))
            }
        }

        return sudokuBoard
    }
}