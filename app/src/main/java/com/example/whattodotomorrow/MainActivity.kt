package com.example.whattodotomorrow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whattodotomorrow.db.TodoDatabase
import com.example.whattodotomorrow.db.TodoEntitiy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private  var todoDatabase: TodoDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoDatabase = TodoDatabase.getInstance(this)

        btn_set.setOnClickListener {
            val addRunnable = Runnable {
                val todo = TodoEntitiy(null,"2020-10-29","처음")
                todoDatabase?.todoDao()?.insertTodo(todo)
                val todo1 = TodoEntitiy(null,"2020-10-29","두번째")
                todoDatabase?.todoDao()?.insertTodo(todo1)
            }
            val addThread = Thread(addRunnable)
            addThread.start()
        }

    }
}