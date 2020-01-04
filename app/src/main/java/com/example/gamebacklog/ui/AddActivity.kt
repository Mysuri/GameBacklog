package com.example.gamebacklog.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.gamebacklog.R
import com.example.gamebacklog.model.Game

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.util.*
import java.text.SimpleDateFormat


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

        if (day.isNullOrEmpty() || month.isNullOrEmpty() || year.isNullOrEmpty()) {
            Toast.makeText(this, "You must fill in a correct date!", Toast.LENGTH_LONG).show()
            return
        }

        val date = convertToDate(day, month, year)

        if (title.isNotBlank() && platform.isNotBlank() && day.isNotBlank()
            && month.isNotBlank() && year.isNotBlank()
        ) {
            if (date != null) {
                val game = Game(title, platform, date)
                addActivityViewModel.insertGame(game)
                finish()
            }
        } else {
            Toast.makeText(this, "You must fill in the input fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun convertToDate(day: String, month: String, year: String): Date? {
        val string = "$day $month $year"
        val format = SimpleDateFormat("dd MM yyyy", Locale.ENGLISH)
        val date = format.parse(string)

        val currentDate = getCurrentDate()
        val format2 = SimpleDateFormat("dd MM yyyy", Locale.ENGLISH)
        val date2 = format2.parse(currentDate)

        if (date.before(date2)) {
            Toast.makeText(this, "Please give a correct date", Toast.LENGTH_LONG).show()
            return null
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

    private fun getCurrentDate(): String {
        val pattern = "dd MM yyyy"
        return SimpleDateFormat(pattern).format(Date())
    }

}
