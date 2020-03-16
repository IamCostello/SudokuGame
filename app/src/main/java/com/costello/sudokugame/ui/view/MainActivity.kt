package com.costello.sudokugame.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.costello.sudokugame.R
import com.costello.sudokugame.data.repository.SudokuBoardRepository
import com.costello.sudokugame.ui.SudokuBoardPresenter
import com.costello.sudokugame.util.PuzzleValidator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val presenter: SudokuBoardPresenter by lazy { SudokuBoardPresenter(sudokuBoard) }
    private val puzzleValidator by lazy{PuzzleValidator()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val btnList: List<Button> = listOf(btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine)
        btnList.forEachIndexed { index, button ->
            button.setOnClickListener(View.OnClickListener { presenter.updateCellValue(index+1) })
        }
        btnRestart.setOnClickListener(View.OnClickListener { presenter.refreshBoard() })
        btnValidate.setOnClickListener(View.OnClickListener {
            if(puzzleValidator.validatePuzzle(presenter.getSudokuBoard())){
                Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Failure!", Toast.LENGTH_LONG).show()
            }
        })

        presenter.view.setOnClickListener(View.OnClickListener { presenter.updateCellSelected(presenter.view.rowSelected, presenter.view.colSelected) })
    }
}
