package com.example.kuis1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy

class MainActivity : AppCompatActivity() {
    private lateinit var timer: CountDownTimer
    private var detik: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var waktu: Long = 0
        val tv = findViewById<TextView>(R.id.detik)
        val input = "1000000"
        fun getDetik (): Int {
            return detik
        }
        val obser = Observable.create {
            it.onNext(getDetik())
        }
        fun startTimer (){
            timer = object
                :CountDownTimer(waktu*1000L, 1000L){
                override fun onFinish() {
                    detik = 0
                }
                override fun onTick(millisUntilFinished: Long) {
                    val waktuTersisa = millisUntilFinished/1000L
                    val ba = input.toInt()
                    val wow = waktuTersisa.toInt()
                    detik = ba - wow
                    obser.subscribeBy{
                        tv.text = it.toString()
                    }
                }
            }.start()
        }

        fun stopTimer (){
            timer.cancel()
        }

        findViewById<Button>(R.id.start).setOnClickListener{
            waktu = input.toLong()
            startTimer()
        }

        findViewById<Button>(R.id.stop).setOnClickListener{
            stopTimer()
        }

        findViewById<Button>(R.id.reset).setOnClickListener{
            stopTimer()
            tv.text = "0"
        }
    }
}