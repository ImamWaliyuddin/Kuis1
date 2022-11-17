package com.example.kuis1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {
    private lateinit var timer: CountDownTimer
    private val detik = MutableLiveData<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var selesai = MutableLiveData<Boolean>()
        var waktu= MutableLiveData<Long>()
        val tv = findViewById<TextView>(R.id.detik)
        val input = "1000000"
        fun getDetik (): LiveData<Int> {
            return detik
        }

        fun startTimer (){
            timer = object
                :CountDownTimer(waktu.value!!.toLong()*1000L, 1000L){
                override fun onFinish() {
                    detik.value = 0
                    selesai.value = true
                }
                override fun onTick(millisUntilFinished: Long) {
                    val waktuTersisa = millisUntilFinished/1000L
                    val ba = input.toInt()
                    var wow = waktuTersisa.toInt()
                    detik.value = ba - wow
                }
            }.start()
        }

        fun stopTimer (){
            timer.cancel()
        }

        getDetik().observe(this, Observer {
            tv.text = it.toString()
        })

        findViewById<Button>(R.id.start).setOnClickListener{
            waktu.value = input.toLong()
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