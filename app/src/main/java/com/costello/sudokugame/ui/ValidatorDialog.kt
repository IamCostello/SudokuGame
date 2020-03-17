package com.costello.sudokugame.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class ValidatorDialog(val state: Boolean) : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(activity)
        builder.setPositiveButton("Continue"){ _, _ ->  }

        when(state){
            true -> builder.setTitle("Congratulations").setMessage("You solved puzzle")
            else -> builder.setTitle("Failed").setMessage("Puzzle still not solved")
        }


        return builder.create()
    }
}