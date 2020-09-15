package com.jetpackintro.stopwatch_exe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jetpackintro.stopwatch_exe.model.TimeModel

class TimeViewModel: ViewModel() {

    var timeModel = MutableLiveData<TimeModel>()

    fun resetTime(){
        val model = timeModel?.value
        model?.minutes = "0"
        model?.seconds = "00"
        timeModel.value = model
    }

    fun updateTime(){
        val model = timeModel.value
        when(model?.seconds?.toInt()){
            in 0..8 -> model?.seconds = "0" + (model?.seconds?.toInt()?.plus(1)).toString()
            in 9..58 -> model?.seconds = (model?.seconds?.toInt()?.plus(1)).toString()
            59 -> {
                model?.seconds = "00"
                model?.minutes = (model?.minutes?.toInt()?.plus(1)).toString()
            }
        }
        timeModel.value = model
    }
}