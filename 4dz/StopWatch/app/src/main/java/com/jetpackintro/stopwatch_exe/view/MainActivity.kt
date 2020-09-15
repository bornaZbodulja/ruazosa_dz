package com.jetpackintro.stopwatch_exe.view

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jetpackintro.stopwatch_exe.R
import com.jetpackintro.stopwatch_exe.model.TimeModel
import com.jetpackintro.stopwatch_exe.viewmodel.TimeViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var timeViewModel: TimeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(TimeViewModel::class.java)

        timeViewModel.timeModel.value = TimeModel()
        var timeCounter = countTime()

        timeViewModel.timeModel.observe(this, Observer {
            timeTextView.text = it.minutes + ":" + it.seconds
        })

        startButton.setOnClickListener {
            when(startButton.text){
                "Start" -> {
                    timeCounter.execute(timeViewModel)
                    startButton.text = "Stop"
                }
                "Stop" -> {
                    timeCounter.cancel(true)
                    startButton.text = "Start"
                    timeCounter = countTime()
                }
            }
        }

        resetButton.setOnClickListener {
            timeViewModel.resetTime()
        }
    }
    class countTime(): AsyncTask<TimeViewModel, TimeViewModel, Unit>(){

        override fun onProgressUpdate(vararg values: TimeViewModel) {
            super.onProgressUpdate(*values)
            for (viewModel: TimeViewModel in values.iterator()){
                viewModel.updateTime()
            }
        }

        override fun doInBackground(vararg params: TimeViewModel) {
            for (viewModel in params) {
                while (!isCancelled) {
                    Thread.sleep(1000)
                    publishProgress(viewModel)
                }
            }

        }
    }
}
