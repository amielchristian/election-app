package com.example.machineproblem4

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.iterator
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnChairman = findViewById<Button>(R.id.btnChairman)
        btnChairman.setOnClickListener()    {
            val selectedChairman = findViewById<TextView>(R.id.tvChairmanName)
            showChairmanDialog(selectedChairman)
        }

        val btnCouncilors = findViewById<Button>(R.id.btnCouncilors)
        btnCouncilors.setOnClickListener()    {
            val selectedCouncilors = findViewById<TextView>(R.id.tvCouncilorNames)
            showCouncilorDialog(selectedCouncilors)
        }
    }

    private fun showChairmanDialog(selectedChairman : TextView) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.chairman_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // add listener to radio buttons
        val choices = dialog.findViewById<RadioGroup>(R.id.chairmanChoices)
        choices.setOnCheckedChangeListener { _, _ ->
            val choice = dialog.findViewById<RadioButton>(choices.checkedRadioButtonId)
            selectedChairman.text = choice.text.toString()
            dialog.dismiss()
        }

        dialog.setOnCancelListener() {
            selectedChairman.text = "No chairman selected."
        }

        dialog.show()
    }

    private fun showCouncilorDialog(selectedCouncilors : TextView) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.councilor_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // check boxes already in the list, for consistency
        val checkboxes = dialog.findViewById<LinearLayout>(R.id.checkboxContainer)
        val selectedNames = selectedCouncilors.text.split("\n")
        for (checkboxObject in checkboxes)   {
            val checkbox = dialog.findViewById<CheckBox>(checkboxObject.id)
            if (selectedNames.contains(checkbox.text))
                checkbox.isChecked = true
        }

        // add on-click listener to Vote button
        val btnVote = dialog.findViewById<Button>(R.id.btnVote)
        btnVote.setOnClickListener()    {
            var nameList = ""
            for (checkboxObject in checkboxes)   {
                val checkbox = dialog.findViewById<CheckBox>(checkboxObject.id)
                if (checkbox.isChecked)   {
                    nameList += checkbox.text.toString() + "\n"
                }
            }
            nameList =
                if (nameList == "")
                    "No councilors selected."
                else
                    nameList.removeSuffix("\n")

            selectedCouncilors.text = nameList
            dialog.dismiss()
        }

        dialog.show()
    }
}