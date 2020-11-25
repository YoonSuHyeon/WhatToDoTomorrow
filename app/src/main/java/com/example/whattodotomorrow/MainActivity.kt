package com.example.whattodotomorrow

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.whattodotomorrow.alarm.AlarmReceiver
import com.example.whattodotomorrow.db.TodoDatabase
import com.example.whattodotomorrow.db.TodoEntitiy
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var todoDatabase: TodoDatabase? = null
    private var readList: List<TodoEntitiy>? = null
    private var presentList: ArrayList<TodoEntitiy> = ArrayList()
    private lateinit var alarmManager: AlarmManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoDatabase = TodoDatabase.getInstance(this)
        readDate()

        //알람
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager


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
                    presentList.add(
                        TodoEntitiy(
                            null,
                            currentText + "/" + tempList[0],
                            tempList[1]
                        )
                    )
                }
            }
            if (et_content2.text.toString() != "") {
                val tempList = checkedParse(et_content2.text.toString())
                if (tempList.size != 2) {
                    Toast.makeText(this, "올바른 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    //알람등록

                    //DB 저장
                    Log.d("local date", Calendar.getInstance().time.toString())
                    presentList.add(
                        TodoEntitiy(
                            null,
                            currentText + "/" + tempList[0],
                            tempList[1]
                        )
                    )
                }
            }
            if (et_content3.text.toString() != "") {
                val tempList = checkedParse(et_content3.text.toString())
                if (tempList.size != 2) {
                    Toast.makeText(this, "올바른 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    //알람등록

                    //DB 저장
                    Log.d("local date", Calendar.getInstance().time.toString())
                    presentList.add(
                        TodoEntitiy(
                            null,
                            currentText + "/" + tempList[0],
                            tempList[1]
                        )
                    )
                }
            }
            if (et_content4.text.toString() != "") {
                val tempList = checkedParse(et_content4.text.toString())
                if (tempList.size != 2) {
                    Toast.makeText(this, "올바른 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    //알람등록

                    //DB 저장
                    Log.d("local date", Calendar.getInstance().time.toString())
                    presentList.add(
                        TodoEntitiy(
                            null,
                            currentText + "/" + tempList[0],
                            tempList[1]
                        )
                    )
                }
            }
            if (et_content5.text.toString() != "") {
                val tempList = checkedParse(et_content5.text.toString())
                if (tempList.size != 2) {
                    Toast.makeText(this, "올바른 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    //알람등록

                    //DB 저장
                    Log.d("local date", Calendar.getInstance().time.toString())
                    presentList.add(
                        TodoEntitiy(
                            null,
                            currentText + "/" + tempList[0],
                            tempList[1]
                        )
                    )
                }
            }
            if (et_content6.text.toString() != "") {
                val tempList = checkedParse(et_content6.text.toString())
                if (tempList.size != 2) {
                    Toast.makeText(this, "올바른 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    //알람등록

                    //DB 저장
                    Log.d("local date", Calendar.getInstance().time.toString())
                    presentList.add(
                        TodoEntitiy(
                            null,
                            currentText + "/" + tempList[0],
                            tempList[1]
                        )
                    )
                }
            }
            if (et_content7.text.toString() != "") {
                val tempList = checkedParse(et_content7.text.toString())
                if (tempList.size != 2) {
                    Toast.makeText(this, "올바른 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    //알람등록

                    //DB 저장
                    Log.d("local date", Calendar.getInstance().time.toString())
                    presentList.add(
                        TodoEntitiy(
                            null,
                            currentText + "/" + tempList[0],
                            tempList[1]
                        )
                    )
                }
            }



            addDb(presentList)
            onBackPressed()
        }

    }

    private fun makePendingIntent(privateId: Int?, content: String?): PendingIntent {
        val intent = Intent(this, AlarmReceiver::class.java)  // 1
        intent.putExtra("content", content)
        intent.putExtra("privateId", privateId)
        return PendingIntent.getBroadcast(     // 2
            this, privateId!!, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }


    private fun addDb(todo: ArrayList<TodoEntitiy>) {
        val addRunnable = Runnable {
            //넣기전에 존재하는것은 제거
            for (todoItem in todo) {
                if (readList != null) {
                    if (readList!!.find { read -> todoItem.time == read.time && todoItem.content == read.content } == null) {
                        Log.d("중복된것이 없어서 넣음 ", "ㅎㅎ");
                        todoDatabase?.todoDao()?.insertTodo(todoItem)

                        Log.d("toddItem", todoItem.time!!)
                        //고유한 숫자를 만들기위함
                        val date = todoItem.time!!.split("/")[0].split("-")[2].toInt() * 100
                        val time = todoItem.time!!.split("/")[1].split(":").map { it.toInt() }
                        val privateId = time.sum() + date
                        Log.d("toddItem", "$privateId ")
                        //PendingIntent 생성
                        val pendingIntent = makePendingIntent(privateId, todoItem.content)
                        //알람도 추가
                        val currentTime = Calendar.getInstance().time

                        val cal = GregorianCalendar(Locale.KOREA)
                        cal.time = currentTime


                        /**
                         * Test 할때는 cal.add(Calendar.DATE .. ) 을 주석 처리해서 사용 ..
                         * **/
                        //일을하나 추가시킨다.
                        cal.add(Calendar.DATE, 1)
                        cal.set(Calendar.HOUR_OF_DAY, time[0])
                        cal.set(Calendar.MINUTE, time[1])
                        alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            cal.timeInMillis,
                            pendingIntent
                        )


                        /* 1분후에 울리게하는 알람
                        val triggerTime = (SystemClock.elapsedRealtime()  // 4
                                + 60 * 1000)
                        alarmManager.set(   // 5
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            triggerTime,
                            pendingIntent
                        )*/

                        Log.d("알람설정 12:5 ", "ㅎㅎ");
                    }
                    Log.d("중복된것이 생김 ", "ㅎㅎ");
                } else {
                    todoDatabase?.todoDao()?.insertTodo(todoItem)
                }
            }
            runOnUiThread {
                Toast.makeText(this, "저장", Toast.LENGTH_SHORT).show()
            }

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
                for (i in readList!!.indices) {
                    when (i) {
                        0 -> {
                            et_content1.setText(readList!![i].time!!.split("/")[1] + "/" + readList!![i].content)
                        }
                        1 -> {
                            et_content2.setText(readList!![i].time!!.split("/")[1] + "/" + readList!![i].content)
                        }
                        2 -> {
                            et_content3.setText(readList!![i].time!!.split("/")[1] + "/" + readList!![i].content)
                        }
                        3 -> {
                            et_content4.setText(readList!![i].time!!.split("/")[1] + "/" + readList!![i].content)
                        }
                        4 -> {
                            et_content5.setText(readList!![i].time!!.split("/")[1] + "/" + readList!![i].content)
                        }
                        5 -> {
                            et_content6.setText(readList!![i].time!!.split("/")[1] + "/" + readList!![i].content)
                        }
                        6 -> {
                            et_content7.setText(readList!![i].time!!.split("/")[1] + "/" + readList!![i].content)
                        }
                    }
                }
            }

        }
        val thread = Thread(r)
        thread.start()
    }

}