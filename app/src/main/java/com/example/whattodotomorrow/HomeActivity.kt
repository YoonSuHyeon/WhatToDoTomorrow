package com.example.whattodotomorrow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.whattodotomorrow.databinding.ActivityHomeBinding
import com.example.whattodotomorrow.db.TodoDatabase
import com.example.whattodotomorrow.db.TodoEntitiy

import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    lateinit var todoAdapter: TodoAdapter
    private lateinit var todoList: ObservableArrayList<Todo>
    private var todoDatabase: TodoDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_home)
        var binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        todoAdapter = TodoAdapter()
        todoList = ObservableArrayList()
        binding.rvList.adapter = todoAdapter
        binding.todoList = todoList





        btn_tomorrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btn_history.setOnClickListener {
            val intnet = Intent(this, HistoryActivity::class.java)
            startActivity(intent)

        }
    }


    companion object {
        @BindingAdapter("bind:item")
        @JvmStatic
        fun bindItem(recyclerView: RecyclerView, todoList: ObservableArrayList<Todo>) {
            var adapter: TodoAdapter = recyclerView.adapter as TodoAdapter
            adapter.setItem(todoList)

        }
    }

    override fun onResume() {
        super.onResume()
        todoDatabase = TodoDatabase.getInstance(this)
        todoList.clear()
        val r = Runnable {
            // 데이터에 읽고 쓸때는 쓰레드 사용


            val temp = todoDatabase?.todoDao()?.getAll()

            //현재 날짜를 가져와서 전날 등록한 것들만 리스트에 등록한다.

            val currentTime = Calendar.getInstance().time

            val cal = GregorianCalendar(Locale.KOREA)
            cal.time = currentTime
            cal.add(Calendar.DATE, -1)

            val beforeText =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
            Log.d("beforeText",beforeText)

            temp?.forEach {
                Log.d("databaseText",it.time!!)

                if (it.time!!.split("/")[0] == beforeText) {
                    todoList.add(Todo(it.time!!, it.content!!))
                }
            }


        }

        val thread = Thread(r)
        thread.start()
    }
}