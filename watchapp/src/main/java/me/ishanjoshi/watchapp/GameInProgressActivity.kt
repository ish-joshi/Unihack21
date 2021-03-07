package me.ishanjoshi.watchapp

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class GameInProgressActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_in_progress)

        // Enables Always-on
        setAmbientEnabled()
    }
}