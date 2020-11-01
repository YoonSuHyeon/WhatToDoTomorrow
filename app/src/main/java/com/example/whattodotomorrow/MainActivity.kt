package com.example.whattodotomorrow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.whattodotomorrow.db.TodoDatabase
import com.example.whattodotomorrow.db.TodoEntitiy
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var todoDatabase: TodoDatabase? = null
    private var readList: List<TodoEntitiy>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoDatabase = TodoDatabase.getInstance(this)
        readDate()



        btn_set.setOnClickListener {
            val currentTime = Calendar.getInstance().time
            val currentText =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime)
            if (et_content1.text.toString() != "") {
                val tempList = checkedParse(et_content1.text.toString())
                if (tempList.size != 2) {
                    Toast.makeText(this, "올바른 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    //알람등록

                    //DB 저장
                    Log.d("local date", Calendar.getInstance().time.toString())
                    addDb(
                        TodoEntitiy(
                            null,
                            currentText + "/" + tempList[0],
                            tempList[1]
                        )
                    ) //날짜와  내용 저장
                    Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show()
                }
            } else if (et_content2.text.toString() != "") {
                val tempList = checkedParse(et_content1.text.toString())
                if (tempList.size != 2) {
                    Toast.makeText(this, "올바른 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    //알람등록후 DB에 저장
                }
            }

        }

    }


    private fun addDb(todo: TodoEntitiy) {
        val addRunnable = Runnable {
            todoDatabase?.todoDao()?.insertTodo(todo)
        }
        val addThread = Thread(addRunnable)
        addThread.start()
    }

    private fun checkedParse(s: String): List<String> {
        return s.split("/")
    }


    private fun readDate() {
        val r = Runnable {
            // 데이터에 읽고 쓸때는 쓰레드 사용

            val currentTime = Calendar.getInstance().time
            val currentText =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime)

            readList =
                todoDatabase?.todoDao()?.getAll()?.filter { it.time!!.split("/")[0] == currentText }
            runOnUiThread {
                for(i in readList!!.indices){
                    when(i){
                        0 -> {
                            et_content1.setText(readList!![i].time!!.split("/")[1]+"/"+ readList!![i].content)
                        }
                        1->{
                            et_content2.setText(readList!![i].time!!.split("/")[1]+"/"+ readList!![i].content)
                        }
                        2->{
                            et_content3.setText(readList!![i].time!!.split("/")[1]+"/"+ readList!![i].content)
                        }
                        3->{
                            et_content4.setText(readList!![i].time!!.split("/")[1]+"/"+ readList!![i].content)
                        }
                        4->{
                            et_content5.setText(readList!![i].time!!.split("/")[1]+"/"+ readList!![i].content)
                        }
                        5->{
                            et_content6.setText(readList!![i].time!!.split("/")[1]+"/"+ readList!![i].content)
                        }
                        6->{
                            et_content7.setText(readList!![i].time!!.split("/")[1]+"/"+ readList!![i].content)
                        }
                    }
                }
            }

        }
        val thread = Thread(r)
        thread.start()
    }

}