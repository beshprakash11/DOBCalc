package com.beshpd.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate: TextView?  = null
    private var tvAgeInMinutes: TextView? = null
    private var tvAgeInYear: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)

        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvAgeInYear = findViewById(R.id.tvAgeInYear)


        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }

    }

    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener{ _, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this, "Year was $selectedYear, month was ${selectedMonth + 1}, date was $selectedDayOfMonth", Toast.LENGTH_SHORT).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.GERMAN)
                val theDate = sdf.parse(selectedDate)

                // date return in miliseconds
                // convert to seconds by dividing /1000 and minutes by 60 i.e. in minutes = 60000

                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        tvAgeInMinutes?.text = differenceInMinutes.toString()


                        // Selected date in year
                        val selectedYear = selectedDateInMinutes /( 60 * 24 * 30 * 12)
                        val currentYear = currentDateInMinutes /( 60 * 24 * 30 * 12)
                        val diffYear = currentYear - selectedYear

                        tvAgeInYear?.text = diffYear.toString()
                    }
                }
            },
            year,
            month,
            day
        )
        //dont allow to select future dates
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }
}