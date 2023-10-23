package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskViewModel=ViewModelProvider(this).get(TaskViewModel::class.java)
        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager,"newTaskTag")
        }
//        taskViewModel.name.observe(this)
//        {
//            binding.taskName.text=String.format("Task Name: %s",it)
//        }
//
//        taskViewModel.desc.observe(this)
//        {
//            binding.taskDesc.text=String.format("Task Desc: %s",it)
//        }
        setRecyclerView()
    }
    private fun setRecyclerView()
    {
        val mainActivity=this
        taskViewModel.taskItems.observe(this)
        {
            binding.todoListRecyclerView.apply{
                layoutManager=LinearLayoutManager(applicationContext)
                adapter=TaskItemAdapater(it,mainActivity)
            }
        }

    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager,"newTasktag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }
}