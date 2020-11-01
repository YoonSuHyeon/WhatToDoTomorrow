package com.example.whattodotomorrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.whattodotomorrow.databinding.ActivityHomeBinding
import com.example.whattodotomorrow.db.TodoDatabase
import com.example.whattodotomorrow.db.TodoEntitiy

import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var todoAdapter: TodoAdapter
    private lateinit var todoList: ObservableArrayList<Todo>
    private  var todoDatabase: TodoDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_home)
        var binding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        todoAdapter= TodoAdapter()
        todoList= ObservableArrayList()
        binding.rvList.adapter= todoAdapter
        binding.todoList=todoList


        todoDatabase = TodoDatabase.getInstance(this)

        val r = Runnable {
            // 데이터에 읽고 쓸때는 쓰레드 사용

            val temp =todoDatabase?.todoDao()?.getAll()
            temp?.forEach { todoList.add(Todo(it.time!!,it.content!!)) }
        }

        val thread = Thread(r)
        thread.start()

        btn_tomorrow.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }



    companion object{
        @BindingAdapter("bind:item")
        @JvmStatic
        fun bindItem(recyclerView: RecyclerView, todoList: ObservableArrayList<Todo>){
            var adapter:TodoAdapter = recyclerView.adapter as TodoAdapter
            adapter.setItem(todoList)

        }
    }

    override fun onResume() {
        super.onResume()

    }
}