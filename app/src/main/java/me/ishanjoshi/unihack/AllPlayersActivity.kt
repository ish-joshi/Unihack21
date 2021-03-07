package me.ishanjoshi.unihack

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerOptions.Builder
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import me.ishanjoshi.unihack.databinding.ActivityAllPlayersBinding
import me.ishanjoshi.unihack.databinding.PlayerPreviewItemBinding


typealias TeamPlayers = List<Player>

data class Player(val name: String = "", val phone: String = "")

class AllPlayersViewModel @ViewModelInject constructor() : ViewModel() {

    val fb = FirebaseFirestore.getInstance()
    fun addPlayer(name: String, phone: String) {
        fb.collection("players").add(Player(name, phone))
    }
}

class PlayerHolder(val b: PlayerPreviewItemBinding) : RecyclerView.ViewHolder(b.root) {
    fun bind(player: Player) {
        b.apply {
            playerName.text = player.name
            phoneNumber.text = player.phone
        }
    }
}

@AndroidEntryPoint
class AllPlayersActivity : AppCompatActivity() {

    private val viewModel: AllPlayersViewModel by viewModels()
    private lateinit var binding: ActivityAllPlayersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllPlayersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        // Recycler list configuration
        val options: FirestoreRecyclerOptions<Player> = Builder<Player>()
            .setQuery(viewModel.fb.collection("players"), Player::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter: FirestoreRecyclerAdapter<*, *> = object : FirestoreRecyclerAdapter<Player, PlayerHolder>(options) {
            override fun onBindViewHolder(holder: PlayerHolder, position: Int, model: Player) {
                holder.bind(model)
                holder.b.root.setOnClickListener {
                    startActivity(Intent(this@AllPlayersActivity, PlayerDetailActivity::class.java))
                }
            }

            override fun onCreateViewHolder(group: ViewGroup, i: Int): PlayerHolder {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                val b = PlayerPreviewItemBinding.inflate(LayoutInflater.from(group.context), group, false)
                return PlayerHolder(b)
            }
        }

        binding.layout.playersList.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(adapter)
        }

        adapter.startListening()

        binding.fab.setOnClickListener { view ->
            viewModel.addPlayer("Ishan", "+61422682029")
        }
    }
}