package com.costello.sudokugame.util

import com.costello.sudokugame.data.SudokuBoard

class PuzzleValidator() {
    fun validatePuzzle(sudokuBoard: SudokuBoard): Boolean {
        val seenValues: MutableSet<String> = mutableSetOf()

        for(i in 0..8){
            for(j in 0..8){
                val cellValue = sudokuBoard.getValue(i, j)

                if(cellValue != 0){
                    seenValues.add("$cellValue found in row $i")
                    seenValues.add("$cellValue found in col $j")
                    seenValues.add("$cellValue found in block ${i/3} - ${j/3}")
                    }
                }
            }
        return seenValues.size == 243
    }
}