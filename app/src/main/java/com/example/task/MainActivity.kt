package com.example.task

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity(), TaskClickInterface, TaskClickDeleteInterface {

    lateinit var viewModal: TaskViewModal
    lateinit var tasksRV: RecyclerView
    lateinit var addFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tasksRV = findViewById(R.id.tasksRV)
        addFAB = findViewById(R.id.idFAB)

        tasksRV.layoutManager = LinearLayoutManager(this)

        val taskRVAdapter = TaskRVAdapter(this, this, this)

        tasksRV.adapter = taskRVAdapter

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(TaskViewModal::class.java)

        viewModal.allTasks.observe(this, Observer { list ->
            list?.let {
                // on below line we are updating our list.
                taskRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditTaskActivity::class.java)
            startActivity(intent)
            //.finish()
        }
    }

    override fun onTaskClick(task: Task) {
        val intent = Intent(this@MainActivity, AddEditTaskActivity::class.java)
        intent.putExtra("taskType", "Edit")
        intent.putExtra("taskTitle", task.taskTitle)
        intent.putExtra("taskDescription", task.taskDescription)
        intent.putExtra("taskId", task.id)
        startActivity(intent)
        //this.finish()
    }

    override fun onDeleteIconClick(task: Task) {
        viewModal.deleteTask(task)
        Toast.makeText(this, "${task.taskTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}
