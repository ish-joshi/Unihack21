package me.ishanjoshi.unihack

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import me.ishanjoshi.unihack.SeasonStatAdapter.SeasonStatItem
import me.ishanjoshi.unihack.SeasonStatAdapter.SeasonStatViewHolder
import me.ishanjoshi.unihack.databinding.ActivityMainBinding
import me.ishanjoshi.unihack.databinding.SeasonStatItemBinding
import javax.inject.Inject


private const val TAG = "MainActivity"

class SeasonStatAdapter(var items: List<SeasonStatItem>) : RecyclerView.Adapter<SeasonStatViewHolder>() {
    inner class SeasonStatViewHolder(val b: SeasonStatItemBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: SeasonStatItem) {
            b.apply {
                statHeading.text = item.heading
                statValue.text = item.value
            }
        }
    }
    data class SeasonStatItem(val heading: String, val value: String)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonStatViewHolder {
        val b = SeasonStatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeasonStatViewHolder(b)
    }

    override fun onBindViewHolder(holder: SeasonStatViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bind(items[position])
        }
    }

    override fun getItemCount() = items.size

}


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var name: String

    private val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            //Setup recycler
            seasonStatsRecycler.apply {
                val items = listOf<SeasonStatItem>(
                    SeasonStatItem("Remaining games", "3"),
                    SeasonStatItem("Win percentage", "37%"),
                    SeasonStatItem("Ladder Position", "6/10"),
                    SeasonStatItem("Players Used", "22")
                )
                adapter = SeasonStatAdapter(items)
//                val lm = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val lm = GridLayoutManager(context, 2)
                layoutManager = lm


                // AR configuration using echo ar
                arCoachButton.setOnClickListener {
                    val AR_URL = "https://go.echoar.xyz/erog"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(AR_URL)
                    startActivity(i)
                }
            }

            managePlayersButton.setOnClickListener {
                startActivity(Intent(this@MainActivity.baseContext, AllPlayersActivity::class.java))
            }



        }
        
    }
}