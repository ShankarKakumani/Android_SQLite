package com.globomed.learn

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class EmployeeListAdapter(
	private val context: Context
) : RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>() {

	private lateinit var employeeList : ArrayList<Employee>
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): EmployeeViewHolder {

		val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
		return EmployeeViewHolder(itemView)
	}

	override fun getItemCount(): Int {
		return employeeList.size
	}

	override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
		val employee = employeeList[position]

		holder.setData(employee)
		holder.setListener()
	}

	fun setEmployees(employeeList: ArrayList<Employee>) {

		this.employeeList = employeeList
		notifyDataSetChanged()
	}

	inner class EmployeeViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView) {


		fun setData(employee: Employee) {
			itemView.tvEmpName.text = employee.name
			itemView.tvEmpDesignation.text = employee.designation
			itemView.tvIsSurgeonConfirm.text =
				if(1 == employee.isSurgeon) "YES"
				else "NO"
		}

		fun setListener() {
			itemView.setOnClickListener {

				val intent = Intent(context, UpdateEmployeeActivity::class.java)
				intent.putExtra(GloboMedDBContact.EmployeeEntry.COLUMN_ID, employeeList[adapterPosition].id)
				(context as Activity).startActivityForResult(intent, 1)

			}
		}

	}
}
