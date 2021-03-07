package me.ishanjoshi.watchapp

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel @ViewModelInject constructor() : ViewModel() {

    private val defaultEvent = SportingEvent(title = "Round 3 Game", location = "McPherson Oval", "", "Matilda's")

    private val upcomingEvent: MutableLiveData<SportingEvent> = MutableLiveData(defaultEvent)
    fun upcomingEvent() = upcomingEvent.toLiveData()

    fun getAddressAndTimeText(): String = upcomingEvent.value?.let {
        return@let "📌 ${it.location}\n🕥 Sun 14 March, 9 am" //TODO; remove hardcoding
    } ?: ""

}

fun<T> MutableLiveData<T>.toLiveData(): LiveData<T>  = this