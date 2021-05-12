package com.globomed.learn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.globomed.learn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val employeeAdapter = EmployeeListAdapter(this)
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.recyclerView.adapter = employeeAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        employeeAdapter.setEmployees(DataManager.fetchAllEmployees(databaseHelper))

        binding.fab.setOnClickListener{

            val intent = Intent(this@MainActivity, AddEmployeeActivity::class.java)
            startActivityForResult(intent, 1)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK ) {
            employeeAdapter.setEmployees(DataManager.fetchAllEmployees(databaseHelper))
        }
    }
}