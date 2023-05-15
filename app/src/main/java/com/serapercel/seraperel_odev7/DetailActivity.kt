package com.serapercel.seraperel_odev7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.serapercel.seraperel_odev7.database.DB
import com.serapercel.seraperel_odev7.model.Note

class DetailActivity : AppCompatActivity() {

    lateinit var db: DB
    lateinit var tvTitle: TextView
    lateinit var tvDetail: TextView
    lateinit var tvDate: TextView
    lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        db = DB(this)

        tvTitle = findViewById(R.id.tvTitle)
        tvDetail = findViewById(R.id.tvDetail)
        tvDate = findViewById(R.id.tvDate)
        btnDelete = findViewById(R.id.btnDelete)

        val note= Note(MainActivity.thisNote!!.nid,MainActivity.thisNote!!.title,MainActivity.thisNote!!.detail,MainActivity.thisNote!!.date)
        tvTitle.text = note.title
        tvDetail.text = note.detail
        tvDate.text = note.date

        btnDelete.setOnClickListener {
            db.deleteNote(note.nid)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}