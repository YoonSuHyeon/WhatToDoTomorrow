package com.example.whattodotomorrow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
/**
 * DB에 있는 것을 가져와서 Content를 순위를 매겨서 보여준다 .
 * ex) 기상 1등  수업듣기 2등
 * ex) 시간대별로 바쁜 순서 막대 그래프
 * ex) 그래프 만들기
 * */
class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
    }
}