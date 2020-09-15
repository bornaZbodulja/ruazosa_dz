package com.jetpackintro.stopwatch_exe.model

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class TimeService(): Service() {

    private val INTERVAL = 1000
    private val t = Timer()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun countTime(){
        t.scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                sendBroadcastAfterWait()
            }
        }, 1000, INTERVAL.toLong())
    }

    private fun sendBroadcastAfterWait() {
        Intent().also { intent ->
            intent.setAction(WAIT_TIME_PASSED)
            sendBroadcast(intent)
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        countTime()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if(t != null){
            t.cancel()
        }
    }

    companion object{
        var WAIT_TIME_PASSED = "com.jetpackintro.stopwatch_exe.waittimepassed"
    }
}