package com.example.task

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditTaskActivity : AppCompatActivity() {
    lateinit var taskTitleEdt: EditText
    lateinit var taskEdt: EditText
    lateinit var saveBtn: Button

    lateinit var viewModal: TaskViewModal
    var taskID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(TaskViewModal::class.java)

        taskTitleEdt = findViewById(R.id.idEdtTaskName)
        taskEdt = findViewById(R.id.idEdtTaskDesc)
        saveBtn = findViewById(R.id.idBtn)


        val taskType = intent.getStringExtra("taskType")
        if (taskType.equals("Edit")) {
            val taskTitle = intent.getStringExtra("taskTitle")
            val taskDescription = intent.getStringExtra("taskDescription")
            taskID = intent.getIntExtra("taskId", -1)
            saveBtn.setText("Update Task")
            taskTitleEdt.setText(taskTitle)
            taskEdt.setText(taskDescription)
        } else {
            saveBtn.setText("Save Task")
        }

        saveBtn.setOnClickListener {
            val taskTitle = taskTitleEdt.text.toString()
            val taskDescription = taskEdt.text.toString()

            if (taskType.equals("Edit")) {
                if (taskTitle.isNotEmpty() && taskDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedTask = Task(taskTitle, taskDescription, currentDateAndTime)
                    updatedTask.id = taskID
                    viewModal.updateTask(updatedTask)
                    Toast.makeText(this, "Task Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (taskTitle.isNotEmpty() && taskDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())

                    viewModal.addTask(Task(taskTitle, taskDescription, currentDateAndTime))
                    Toast.makeText(this, "$taskTitle Added", Toast.LENGTH_LONG).show()
                }
            }

            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}
