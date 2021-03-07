package me.ishanjoshi.unihack

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UnihackApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}