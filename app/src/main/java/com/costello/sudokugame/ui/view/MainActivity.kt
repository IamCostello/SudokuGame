package com.costello.sudokugame.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.costello.sudokugame.R
import com.costello.sudokugame.ui.SudokuBoardPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var presenter: SudokuBoardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = SudokuBoardPresenter(sudokuBoard)

        val btnList: List<Button> = listOf(btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine)
        btnList.forEachIndexed { index, button ->
            button.setOnClickListener(View.OnClickListener { presenter.updateCellValue(index+1) })
        }

        presenter.view.setOnClickListener(View.OnClickListener { presenter.updateCellSelected(presenter.view.rowSelected, presenter.view.colSelected) })
    }
}
