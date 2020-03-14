package com.costello.sudokugame.data

import com.costello.sudokugame.contract.contract

class SudokuBoard(private val sudokuBoard: List<SudokuBoardCell>) : contract.Model {
    override fun getValue(x: Int, y: Int) = sudokuBoard[(x*9)+y].value

    fun getCell(x: Int, y: Int) = sudokuBoard[(x*9)+y]

    override fun setValue(x: Int, y: Int, value: Int){
        sudokuBoard[(x*9)+y].value = value
    }

    override fun isPrime(x: Int, y: Int): Boolean {
        if (sudokuBoard[(x*9)+y].baseCell)
            return true
        return false
    }
}