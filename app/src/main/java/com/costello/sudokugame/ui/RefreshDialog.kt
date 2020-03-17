package com.costello.sudokugame.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class RefreshDialog(val presenter: SudokuBoardPresenter): AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Refresh puzzle").setMessage("Are you sure")

        builder.setPositiveButton("Refresh") { _, _ -> presenter.refreshBoard() }
        builder.setNegativeButton("Back"){_, _ -> }

        return builder.create()
    }
}