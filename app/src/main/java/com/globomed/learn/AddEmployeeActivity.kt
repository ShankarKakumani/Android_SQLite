package com.globomed.learn

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.globomed.learn.databinding.ActivityAddBinding
import java.text.SimpleDateFormat
import java.util.*

class AddEmployeeActivity : Activity() {

    private val myCalendar = Calendar.getInstance()

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var binding: ActivityAddBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        // on clicking ok on the calender dialog
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            binding.etDOB.setText(getFormattedDate(myCalendar.timeInMillis))
        }

        binding.etDOB.setOnClickListener {
            setUpCalender(date)
        }

        binding.bSave.setOnClickListener {
            saveEmployee()
        }

        binding.bCancel.setOnClickListener {

        }
    }

    private fun saveEmployee() {

        var isValid = true
        binding.etEmpName.error = if (binding.etEmpName.text.toString().isEmpty()) {
            isValid = false
            "Required Field"
        } else null


        binding.etDesignation.error = if (binding.etDesignation.text.toString().isEmpty()) {
            isValid = false
            "Required Field"
        } else null

        if(isValid) {

            val name = binding.etEmpName.text.toString()
            val designation = binding.etDesignation.text.toString()
            val dob = myCalendar.timeInMillis

            val db = databaseHelper.writableDatabase
            val values = ContentValues()
            values.put(GloboMedDBContact.EmployeeEntry.COLUMN_NAME, name)
            values.put(GloboMedDBContact.EmployeeEntry.COLUMN_DESIGNATION, designation)
            values.put(GloboMedDBContact.EmployeeEntry.COLUMN_DOB, dob)

            var result = db.insert(GloboMedDBContact.EmployeeEntry.TABLE_NAME, null, values)
            setResult(RESULT_OK, Intent())
            Toast.makeText(this, "Employee Added", Toast.LENGTH_SHORT).show()
            finish()

        }
    }


    private fun setUpCalender(date: DatePickerDialog.OnDateSetListener) {

        DatePickerDialog(
            this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun getFormattedDate(dobInMilis: Long?): String {

        return dobInMilis?.let {
            val sdf = SimpleDateFormat("d MMM, yyyy", Locale.getDefault())
            sdf.format(dobInMilis)
        } ?: "Not Found"
    }
}
