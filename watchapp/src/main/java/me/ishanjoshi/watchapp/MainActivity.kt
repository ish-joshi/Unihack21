package me.ishanjoshi.watchapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.ishanjoshi.watchapp.R.raw
import me.ishanjoshi.watchapp.Sport.AFL
import me.ishanjoshi.watchapp.Sport.CRICKET
import me.ishanjoshi.watchapp.Sport.SOCCER
import me.ishanjoshi.watchapp.databinding.ActivityMainBinding
import java.util.Date

val oneDayInMs = 86400*1000

enum class Sport() {
    SOCCER,
    AFL,
    CRICKET
}

data class SportingEvent(
        val title: String,
        val location: String,
        val teamName: String,
        val oppositionName: String?,
        val startTime: Date = Date(Date().time + oneDayInMs.times(5)),
        val sport: Sport = SOCCER
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.upcomingEvent().observe(this) {
            binding.apply {
                addressAndTime.text = viewModel.getAddressAndTimeText()
                gameTitle.text = it.title
                upcomingAnimation.setAnimation(getAnimationResource(it.sport))
            }
        }

        val inProgress = true
        if (inProgress) {
            startActivity(Intent(this, GameInProgressActivity::class.java))
        }
    }


    private fun getAnimationResource(event: Sport) : Int {
        return when(event) {
            SOCCER -> raw.soccer_animation // https://lottiefiles.com/17434-soccer-player
            AFL -> raw.footy
            CRICKET -> raw.cricket
        }
    }
}