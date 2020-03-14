package com.costello.sudokugame.ui.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.costello.sudokugame.contract.contract.BoardView
import com.costello.sudokugame.data.SudokuBoard
import java.lang.String


class SudokuBoardView(context: Context, attrs: AttributeSet) : View(context, attrs), BoardView {
    private var cellSize: Float = 0f
    private var boardSize: Float = 0f

    var rowSelected: Int = -1
    var colSelected: Int = -1

    var sudokuBoard: SudokuBoard? = null

    //paints
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2F
    }
    private val thickLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 6F
    }
    private val cellSelection = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#606e6e6e")
        style = Paint.Style.FILL
    }
    private val cellSelectionHelper = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#40cdcdcd")
        style = Paint.Style.FILL
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }
    private val primeTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val minWidth: Int = paddingLeft+paddingRight+suggestedMinimumWidth
        val boardSize: Int = resolveSizeAndState(minWidth, widthMeasureSpec, 1)

        setMeasuredDimension(boardSize, boardSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        cellSize = height.toFloat()/9
        boardSize = height.toFloat()
        textPaint.textSize = cellSize
        primeTextPaint.textSize = cellSize

        drawBoard(canvas)
        drawSelection(canvas)
        fillCells(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                val row = (event.getY()/cellSize).toInt()
                val col = (event.getX().toInt()/cellSize).toInt()

                if(sudokuBoard!!.isPrime(row, col)) {
                    return false
                }
                else {
                    rowSelected = row
                    colSelected = col
                    invalidate()
                    return true
                }
            }
            else -> return false
        }
    }

    //TODO change draw style (white / clean / green + red / draw boxes not grid)

    private fun drawBoard(canvas: Canvas){
        canvas.apply {
            for(i in 1..8){
                if(i%3 == 0){
                    drawLine(
                        0f,
                        cellSize*i, boardSize,
                        cellSize*i,
                        thickLinePaint
                    )
                    drawLine(
                        cellSize*i,
                        0f,
                        cellSize*i,
                        boardSize,
                        thickLinePaint)
                } else {
                    drawLine(
                        0f,
                        cellSize*i,
                        boardSize,
                        cellSize*i,
                        linePaint
                    )
                    drawLine(
                        cellSize*i,
                        0f,
                        cellSize*i,
                        boardSize,
                        linePaint
                    )
                }
            }

            for(i in 1..8){
                for(j in 1..8){
                    if (i == rowSelected && j == colSelected)
                        canvas.drawRect(
                            cellSize * j,
                            cellSize * i,
                            cellSize * j + cellSize,
                            cellSize * i + cellSize,
                            cellSelection
                        )
                    else if (i == rowSelected && j != colSelected)
                        canvas.drawRect(
                            cellSize * j,
                            cellSize * i,
                            cellSize * j + cellSize, cellSize * i + cellSize, cellSelectionHelper
                        )
                    else if (i != rowSelected && j == colSelected)
                        canvas.drawRect(
                            cellSize * j,
                            cellSize * i,
                            cellSize * j + cellSize, cellSize * i + cellSize, cellSelectionHelper
                        )
                }
            }
        }
    }
    private fun drawSelection(canvas: Canvas){

        //Fill cells
        for (i in 0..8) {
            for (j in 0..8) {
                if (i == rowSelected && j == colSelected) canvas.drawRect(
                    cellSize * j.toFloat(),
                    cellSize * i.toFloat(),
                    cellSize * j + cellSize,
                    cellSize * i + cellSize,
                    cellSelection
                ) else if (i == rowSelected && j != colSelected) canvas.drawRect(
                    cellSize * j.toFloat(),
                    cellSize * i.toFloat(),
                    cellSize * j + cellSize,
                    cellSize * i + cellSize,
                    cellSelectionHelper
                ) else if (i != rowSelected && j == colSelected) canvas.drawRect(
                    cellSize * j.toFloat(),
                    cellSize * i.toFloat(),
                    cellSize * j + cellSize,
                    cellSize * i + cellSize,
                    cellSelectionHelper
                )
            }
        }
    }
    private fun fillCells(canvas: Canvas){
        for (i in 0..8) {
            for (j in 0..8) {
                val cell = sudokuBoard!!.getCell(i, j)

                if(cell.value == 0) continue

                textPaint.color = if(cell.baseCell) Color.BLUE else Color.BLACK

                val textBounds = Rect()
                textPaint.getTextBounds(cell.value.toString(), 0, cell.value.toString().length, textBounds)
                val textWidth = textPaint.measureText(cell.value.toString())
                val textHeight = textBounds.height()

                canvas.drawText(
                    cell.value.toString(),
                    (cell.y * cellSize) + cellSize / 2 - textWidth / 2,
                    (cell.x * cellSize) + cellSize / 2 + textHeight / 2,
                    textPaint
                )
            }
        }
    }

    fun updateBoard(sudokuBoard: SudokuBoard) {
        this.sudokuBoard = sudokuBoard
        invalidate()
    }
}