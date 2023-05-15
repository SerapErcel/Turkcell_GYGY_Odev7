package com.serapercel.seraperel_odev7

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.get
import com.serapercel.seraperel_odev7.database.DB
import com.serapercel.seraperel_odev7.model.Note
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    lateinit var db: DB
    lateinit var etTitle: EditText
    lateinit var etDetail: EditText
    lateinit var btnSave: Button
    lateinit var btnDate: Button
    lateinit var lvNotes: ListView
    var selectDate = ""
    lateinit var noteList: List<Note>

    companion object {
        var thisNote: com.serapercel.seraperel_odev7.model.Note? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTitle = findViewById(R.id.etTitle)
        etDetail = findViewById(R.id.etDetail)
        btnDate = findViewById(R.id.btnDate)
        btnSave = findViewById(R.id.btnSave)
        lvNotes = findViewById(R.id.lvNotes)

        db = DB(this)

        getNotes()

        val calender = Calendar.getInstance()
        btnDate.setOnClickListener(View.OnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                    Log.d("i", i.toString()) // yıl
                    Log.d("i2", (i2 + 1).toString()) // ay
                    Log.d("i3", i3.toString()) // gün
                    var ay = "${i2 + 1}"
                    if (i2 + i < 10) {
                        ay = "0${i2 + 1}"
                    }
                    selectDate = "$i3.$ay.$i"
                },
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH),
            )
            datePickerDialog.show()
        })

        btnSave.setOnClickListener {

            if (selectDate != "" && etTitle.text.toString() != "" && etDetail.text.toString() != "") {
                val status =
                    db.addNote(etTitle.text.toString(), etDetail.text.toString(), selectDate)
                Log.d("status", status.toString())
                etTitle.setText("")
                etDetail.setText("")
                selectDate = ""

                /* val noteList = db.allNote()
                 val titleList = ArrayList<String>()
                 for (note in noteList) {
                     titleList.add(note.title)
                 }

                 val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titleList)
                 lvNotes.adapter = adapter*/
                getNotes()

            } else {
                Toast.makeText(this, "Missing Information!", Toast.LENGTH_LONG).show()
            }
        }

        lvNotes.setOnItemClickListener { parent, view, position, id ->
            thisNote = noteList[position] as com.serapercel.seraperel_odev7.model.Note
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getNotes() {
        noteList = db.allNote()
        val titleList = ArrayList<String>()
        for (note in noteList) {
            titleList.add(note.title)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titleList)
        lvNotes.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getNotes()
    }

    override fun onStart() {
        super.onStart()
        getNotes()
    }
}