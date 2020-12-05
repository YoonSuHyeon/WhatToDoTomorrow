package com.example.whattodotomorrow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.whattodotomorrow.db.TodoDatabase
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_history.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * DB에 있는 것을 가져와서 Content를 순위를 매겨서 보여준다 .
 * 1주일간 꺽은선 그래프
 *
 *
 * */
class HistoryActivity : AppCompatActivity() {

    private var todoDatabase: TodoDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)






        barChart.run {
            description.isEnabled = false //차트 옆에 별도로 표기되는 description이다. false로 설정하여 안보이게 했다.
            setMaxVisibleValueCount(7) // 최대 보이는 그래프 개수를 7개로 정해주었다.
            setPinchZoom(false) // 핀치줌(두손가락으로 줌인 줌 아웃하는것) 설정
            setDrawBarShadow(false)//그래프의 그림자
            setDrawGridBackground(false)//격자구조 넣을건지
            axisLeft.run { //왼쪽 축. 즉 Y방향 축을 뜻한다.
                axisMaximum = 7f //100 위치에 선을 그리기 위해 101f로 맥시멈을 정해주었다
                axisMinimum = 0f // 최소값 0
                granularity = 1f // 50 단위마다 선을 그리려고 granularity 설정 해 주었다.
                //위 설정이 20f였다면 총 5개의 선이 그려졌을 것
                setDrawLabels(true) // 값 적는거 허용 (0, 50, 100)
                setDrawGridLines(true) //격자 라인 활용
                setDrawAxisLine(false) // 축 그리기 설정
                axisLineColor = ContextCompat.getColor(context, R.color.colorAccent) // 축 색깔 설정
                gridColor = ContextCompat.getColor(context, R.color.colorPrimary) // 축 아닌 격자 색깔 설정
                textColor =
                    ContextCompat.getColor(context, R.color.colorPrimaryDark) // 라벨 텍스트 컬러 설정
                textSize = 14f //라벨 텍스트 크기
            }
            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM//X축을 아래에다가 둔다.
                granularity = 1f // 1 단위만큼 간격 두기
                setDrawAxisLine(true) // 축 그림
                setDrawGridLines(false) // 격자
                textColor = ContextCompat.getColor(context, R.color.colorPrimaryDark) //라벨 색상
                valueFormatter = MyXAxisFormatter() // 축 라벨 값 바꿔주기 위함
                textSize = 14f // 텍스트 크기
            }
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
            legend.isEnabled = false //차트 범례 설정
        }





        //dataset.setColor(ColorTemplate.COLORFUL_COLORS)


    }

    inner class MyXAxisFormatter : ValueFormatter() {
        private val days = arrayOf("일요일","월요일", "화요일", "수요일", "목요일", "금요일", "토요일")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt() - 1) ?: value.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        todoDatabase = TodoDatabase.getInstance(this)
        val r = Runnable {
            // 데이터에 읽고 쓸때는 쓰레드 사용

            var sunTemp=2f
            var monTemp=4f
            var tueemp=5f
            var wesTemp=6f
            var thuTemp=8f
            var friTemp=9f
            var satTemp=1f
            val temp = todoDatabase?.todoDao()?.getAll()

            //현재 날짜를 가져와서 전날 등록한 것들만 리스트에 등록한다.

            val currentTime = Calendar.getInstance().time

            val cal = GregorianCalendar(Locale.KOREA)
            cal.time = currentTime

            /*이번주월요일
            cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
            Log.d("cal",cal.get(Calendar.YEAR).toString())
            Log.d("cal",cal.get(Calendar.MONTH).toString())
            Log.d("cal",cal.get(Calendar.DATE).toString())
*/

            cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
            Log.d("cal",cal.get(Calendar.YEAR).toString())
            Log.d("cal",cal.get(Calendar.MONTH).toString())
            Log.d("cal",cal.get(Calendar.DATE).toString())

            Log.d("cal",cal.get(Calendar.WEEK_OF_YEAR).toString())
            Log.d("cal",cal.get(Calendar.DAY_OF_WEEK).toString())
            Log.d("cal",cal.get(Calendar.DAY_OF_WEEK_IN_MONTH).toString())
            val beforeText =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)

            //현재 주에 일요일 부터 금요일 까지 등록된것을 DB에서 검색한후 값을 넣어준다.





            runOnUiThread {
                val entries = ArrayList<BarEntry>()
                entries.add(BarEntry(1f, sunTemp))
                entries.add(BarEntry(2f, monTemp))
                entries.add(BarEntry(3f, tueemp))
                entries.add(BarEntry(4f, wesTemp))
                entries.add(BarEntry(5f, thuTemp))
                entries.add(BarEntry(6f, friTemp))
                entries.add(BarEntry(7f, satTemp))


                var set = BarDataSet(entries, "DataSet")//데이터셋 초기화 하기
                set.color = ContextCompat.getColor(this, R.color.colorPrimary)

                val dataSet: ArrayList<IBarDataSet> = ArrayList()
                dataSet.add(set)
                val data = BarData(dataSet)
                data.barWidth = 0.3f//막대 너비 설정하기
                barChart.run {
                    this.data = data //차트의 데이터를 data로 설정해줌.
                    setFitBars(true)
                    invalidate()
                }

            }
        }

        val thread = Thread(r)
        thread.start()
    }
}