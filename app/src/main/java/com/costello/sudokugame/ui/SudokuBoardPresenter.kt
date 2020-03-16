package com.costello.sudokugame.ui

import com.costello.sudokugame.data.SudokuBoard
import com.costello.sudokugame.data.SudokuBoardCell
import com.costello.sudokugame.data.repository.SudokuBoardRepository
import com.costello.sudokugame.ui.view.custom.SudokuBoardView
import java.util.*

class SudokuBoardPresenter(_view: SudokuBoardView) {
    val view: SudokuBoardView = _view
    var rowSelected: Int = -1
    var colSelected: Int = -1
    //TODO get from repo
    private var sudokuBoard: SudokuBoard = SudokuBoard(SudokuBoardRepository.generatePuzzle())

    init {
        view.updateBoard(sudokuBoard)
    }

    fun updateCellValue(value: Int){
        if(rowSelected != -1) {
            sudokuBoard.setValue(rowSelected, colSelected, value)
            view.invalidate()
        }
    }

    fun updateCellSelected(rowSelected: Int, colSelected: Int) {
        this.rowSelected = rowSelected
        this.colSelected = colSelected
    }

    fun getSudokuBoard(): SudokuBoard = sudokuBoard

    fun refreshBoard(){
        sudokuBoard = SudokuBoard(SudokuBoardRepository.generatePuzzle())
        view.updateBoard(sudokuBoard)
    }
}