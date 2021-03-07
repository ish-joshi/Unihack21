package me.ishanjoshi.unihack

import android.app.Application
import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext

class MainViewModel @ViewModelInject constructor(
        @ApplicationContext context: Context,
        number: Int
) : AndroidViewModel(context as Application) {

    private val number = MutableLiveData<Int>(number)
    fun number() = number as LiveData<Int>

    fun onLabelTapped() {
        // Increment the number
        number.value = (number.value ?: 0 ).plus(1)
    }

}