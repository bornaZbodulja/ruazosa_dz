package com.jetpackintro.stopwatch_exe.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jetpackintro.stopwatch_exe.R
import com.jetpackintro.stopwatch_exe.model.TimeModel
import com.jetpackintro.stopwatch_exe.model.TimeService
import com.jetpackintro.stopwatch_exe.viewmodel.TimeViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var timeViewModel: TimeViewModel

    inner class SleepBroadcastReciever : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            timeViewModel.updateTime()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sleepBroadcastReciever = SleepBroadcastReciever()
        val filter = IntentFilter(TimeService.WAIT_TIME_PASSED)
        registerReceiver(sleepBroadcastReciever, filter)

        timeViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(TimeViewModel::class.java)

        timeViewModel.timeModel.value = TimeModel()

        timeViewModel.timeModel.observe(this, Observer {
            timeTextView.text = it.minutes + ":" + it.seconds
        })

        startButton.setOnClickListener {
            when(startButton.text){
                "Start" -> {
                    startButton.text = "Stop"
                    intent = Intent(baseContext, TimeService::class.java)
                    startService(intent)
                }
                "Stop" -> {
                    startButton.text = "Start"
                    intent = Intent(baseContext, TimeService::class.java)
                    stopService(intent)
                }
            }
        }

        resetButton.setOnClickListener {
            timeViewModel.resetTime()
        }
    }
}
