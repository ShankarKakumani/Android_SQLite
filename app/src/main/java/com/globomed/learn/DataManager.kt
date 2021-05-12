package com.globomed.learn

import android.content.ContentValues
import com.globomed.learn.GloboMedDBContact.EmployeeEntry
import java.util.*

object DataManager {
    fun fetchAllEmployees(databaseHelper: DatabaseHelper): ArrayList<Employee> {
        val employees = ArrayList<Employee>()


        val db = databaseHelper.readableDatabase

        val columns = arrayOf(
            EmployeeEntry.COLUMN_ID,
            EmployeeEntry.COLUMN_NAME,
            EmployeeEntry.COLUMN_DOB,
            EmployeeEntry.COLUMN_DESIGNATION,
            EmployeeEntry.COLUMN_SURGEON
        )

        val cursor = db.query(
            EmployeeEntry.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        val idPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_ID)
        val namePos = cursor.getColumnIndex(EmployeeEntry.COLUMN_NAME)
        val dobPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DOB)
        val designationPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DESIGNATION)
        val surgeonPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_SURGEON)

        while(cursor.moveToNext()) {
            val id = cursor.getString(idPos)
            val name = cursor.getString(namePos)
            val dob = cursor.getLong(dobPos)
            val designation = cursor.getString(designationPos)
            val surgeon = cursor.getInt(surgeonPos)
            employees.add(Employee(id, name, dob, designation, surgeon))
        }

        cursor.close()

        return employees
    }

    fun fetchEmployee(databaseHelper: DatabaseHelper, empId : String) : Employee? {
        val db = databaseHelper.readableDatabase
        var employee: Employee ? = null

        val columns = arrayOf(
            EmployeeEntry.COLUMN_NAME,
            EmployeeEntry.COLUMN_DOB,
            EmployeeEntry.COLUMN_DESIGNATION,
            EmployeeEntry.COLUMN_SURGEON

        )

        val selection = EmployeeEntry.COLUMN_ID+ " LIKE ? "
        val selectionArgs = arrayOf(empId)

        val cursor = db.query(
            EmployeeEntry.TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val namePos = cursor.getColumnIndex(EmployeeEntry.COLUMN_NAME)
        val dobPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DOB)
        val designationPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_DESIGNATION)
        val surgeonPos = cursor.getColumnIndex(EmployeeEntry.COLUMN_SURGEON)

        while(cursor.moveToNext()) {
            val name = cursor.getString(namePos)
            val dob = cursor.getLong(dobPos)
            val designation = cursor.getString(designationPos)
            val surgeon = cursor.getInt(surgeonPos)

            employee = Employee(empId, name, dob, designation, surgeon)
        }

        cursor.close()

        return employee
    }

    fun updateEmployee(databaseHelper: DatabaseHelper, employee: Employee) {

        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put(EmployeeEntry.COLUMN_NAME, employee.name)
        values.put(EmployeeEntry.COLUMN_DESIGNATION, employee.designation)
        values.put(EmployeeEntry.COLUMN_DOB, employee.dob)
        values.put(EmployeeEntry.COLUMN_SURGEON, employee.isSurgeon)


        val selection = EmployeeEntry.COLUMN_ID+ " LIKE ? "
        val selectionArgs = arrayOf(employee.id)

        db.update(EmployeeEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    fun deleteEmployee(databaseHelper: DatabaseHelper , empId: String) : Int{

        val db = databaseHelper.writableDatabase

        val selection = EmployeeEntry.COLUMN_ID+ " LIKE ? "
        val selectionArgs = arrayOf(empId)

        return db.delete(EmployeeEntry.TABLE_NAME, selection, selectionArgs)
    }

    fun deleteAllEmployee(databaseHelper: DatabaseHelper): Int {



        val db = databaseHelper.writableDatabase
        return db.delete(EmployeeEntry.TABLE_NAME, "1", null)

    }
}