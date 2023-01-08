package kr.co.nottodo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.co.nottodo.R
import kr.co.nottodo.view.calendar.monthly.monthlycalendar.MonthlyCalendar

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val test: MonthlyCalendar = findViewById(R.id.test)

        test.setOnMonthlyCalendarNextMonthListener { view, dateString ->
            Toast.makeText(this,"${dateString}",Toast.LENGTH_SHORT).show()
        }

        test.setOnMonthlyCalendarPrevMonthListener { view, dateString ->
            Toast.makeText(this,"${dateString}",Toast.LENGTH_SHORT).show()
        }
    }
}