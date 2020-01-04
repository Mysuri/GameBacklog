package com.example.gamebacklog.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.gamebacklog.R
import com.example.gamebacklog.database.Converters
import com.example.gamebacklog.model.Game

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.lang.Exception
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

private lateinit var addActivityViewModel: AddActivityViewModel

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        //Back btn
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        fab.setOnClickListener {
            saveGame()
        }
    }

    private fun saveGame() {
        val title = etTitle.text.toString()
        val platform = etPlatform.text.toString()
        val day = etDay.text.toString()
        val month = etMonth.text.toString()
        val year = etYear.text.toString()

        val date = convertToDate(day, month, year)

        if(title.isNotBlank() && platform.isNotBlank() && day.isNotBlank()
            && month.isNotBlank() && year.isNotBlank()) {
            if(date != null) {
                val game = Game(title, platform, date)
                addActivityViewModel.insertGame(game)
                finish()
            }
        } else {
            Toast.makeText(this, "You must fill in the input fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun convertToDate(day: String, month: String, year: String): Date? {
        val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
        var localDate: LocalDate?
        var date: Date? = null
        try {
            localDate = LocalDate.parse(("$day $month $year"), formatter)
            date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        } catch (e: Exception) {
            Toast.makeText(this, "Please give correct date", Toast.LENGTH_LONG).show()
        }

        return date
    }

    private fun initViewModel() {
        addActivityViewModel = ViewModelProviders.of(this).get(AddActivityViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
