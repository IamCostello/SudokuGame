package com.costello.sudokugame.ui.view.custom

import android.content.Context
import android.content.res.AssetManager
import android.graphics.*
import android.graphics.fonts.FontFamily
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.costello.sudokugame.R
import com.costello.sudokugame.contract.contract.BoardView
import com.costello.sudokugame.data.SudokuBoard
import kotlinx.android.synthetic.main.activity_main.*
import javax.xml.parsers.ParserConfigurationException


class SudokuBoardView(context: Context, attrs: AttributeSet) : View(context, attrs), BoardView {
    private var cellSize: Float = 0f
    private var boardSize: Float = 0f
    private var rect = RectF(0f, 0f, 0f, 0f)
    private var cornerRadius = 0f

    var rowSelected: Int = -1
    var colSelected: Int = -1

    var sudokuBoard: SudokuBoard? = null

    //paints
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = 0.4F
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
        color = Color.BLACK
    }

    //TODO round rectangles
    val cellFill = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#30cdcdcd")
        isAntiAlias = true
    }
    val cellStrokeGreen = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 1f
        isAntiAlias = true
        color = Color.parseColor("#8012a690")
    }
    val cellStrokeRed = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 1f
        isAntiAlias = true
        color = Color.parseColor("#80b50d0d")
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
        textPaint.textSize = (cellSize*0.7).toFloat()
        primeTextPaint.textSize = cellSize

        rect = RectF((cellSize*0.05).toFloat(), (cellSize*0.05).toFloat(), (cellSize*0.95).toFloat(), (cellSize*0.95).toFloat())
        cornerRadius = (cellSize*0.95).toFloat()/4

//        for(i in 1..8){
//            if(i%3 == 0){
//                canvas.drawLine(0f, cellSize*i, boardSize, cellSize*i, linePaint)
//                canvas.drawLine(cellSize*i, 0f, cellSize*i, boardSize, linePaint)
//            }
//        }

        drawSelection(canvas)
        fillCells(canvas)
        drawBoard(canvas)
//        drawSelection(canvas)
//        canvas.translate(cellSize*colSelected, cellSize*rowSelected)
//        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, cellSelection)
//        canvas.translate(-(cellSize*colSelected), -(cellSize*rowSelected))

//        for(i in 0..8){
//            for(j in 0..8){
//                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, cellFill)
//
//                when(sudokuBoard!!.getCell(i, j).baseCell){
//                    true -> {
//                        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, cellStrokeGreen)
//                    }
//                }
//                canvas.translate(cellSize, 0f)
//            }
//            canvas.translate(-(cellSize*9), cellSize)
//        }
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
        for(i in 1..8){
            if(i%3 == 0){
                canvas.drawLine(0f, cellSize*i, boardSize, cellSize*i, linePaint)
                canvas.drawLine(cellSize*i, 0f, cellSize*i, boardSize, linePaint)
            }
        }

        for(i in 0..8){
            for(j in 0..8){
                canvas.drawRoundRect(rect, cornerRadius, cornerRadius, cellFill)

                when(sudokuBoard!!.getCell(i, j).baseCell){
                    true -> {
                        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, cellStrokeGreen)
                    }
                }
                canvas.translate(cellSize, 0f)
            }
            canvas.translate(-(cellSize*9), cellSize)
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
                    cellSelectionHelper
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

        canvas.translate(cellSize*colSelected, cellSize*rowSelected)
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, cellSelection)
        canvas.translate(-(cellSize*colSelected), -(cellSize*rowSelected))
    }
    private fun fillCells(canvas: Canvas){
        for (i in 0..8) {
            for (j in 0..8) {
                val cell = sudokuBoard!!.getCell(i, j)

                if(cell.value == 0) continue

                textPaint.color = if(cell.baseCell) Color.BLACK else Color.BLACK

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