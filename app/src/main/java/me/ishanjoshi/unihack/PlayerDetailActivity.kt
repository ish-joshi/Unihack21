package me.ishanjoshi.unihack

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import me.ishanjoshi.unihack.databinding.ActivityPlayerDetailBinding

data class PlayerNote(
        val uid: String, val name: String, val phone: String, val note: String
)

class PlayerDetailViewModel @ViewModelInject constructor() : ViewModel() {

    val fs = FirebaseFirestore.getInstance()
    fun sendNote(uid: String, name: String, phone: String, note: String) {
        fs.collection("notes").add(PlayerNote(uid, name, phone, note))
    }
}

@AndroidEntryPoint
class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerDetailBinding

    private val viewModel: PlayerDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            playerNameDetail.text = "Ishan Joshi"
            playerPosition.text = "Forward"
            playerSpeedStats.text = "Average speed: 18km/hr\nTop speed: 26km/hr"
            recommendations.text = "‣ Player should rest, above than usual average speed increases injury risks.\n‣ Player heart rate higher than " +
                    "usual\n‣ Player not following position assigned"
            submitNote.setOnClickListener {
                viewModel.sendNote("BXEAf3vdkJigrnxv0DkX", "Ishan", "+61422682029", note = binding.playerNotesText.text.toString())
                binding.playerNotesText.text = null
                Toast.makeText(this@PlayerDetailActivity, "Successfully stored note", Toast.LENGTH_LONG).show()
            }
            Glide.with(root.context).load(R.drawable.ij).circleCrop().into(binding.playerPhoto)
            imageView2.rotation = 90f
        }


    }
}