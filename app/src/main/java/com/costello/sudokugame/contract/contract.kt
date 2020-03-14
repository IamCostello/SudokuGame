package com.costello.sudokugame.contract

import android.graphics.Canvas
import com.costello.sudokugame.data.SudokuBoardCell

interface contract {
    interface Model {
        fun getValue(x: Int, y: Int): Int
        fun setValue(x: Int, y: Int, value: Int)
        fun isPrime(x: Int, y: Int): Boolean
    }

    interface BoardView {

    }

    interface Presenter {
        fun updateCell(x: Int, y: Int, value: Int)
        fun getCell(x: Int, y: Int): SudokuBoardCell
    }
}