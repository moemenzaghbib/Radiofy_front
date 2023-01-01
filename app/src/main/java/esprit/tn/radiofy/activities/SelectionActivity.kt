package esprit.tn.radiofy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import esprit.tn.radiofy.R
import esprit.tn.radiofy.databinding.ActivitySelectionBinding

class SelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectionBinding
    private lateinit var adapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectionBinding.inflate(layoutInflater)
        setTheme(MusicActivity.currentTheme[MusicActivity.themeIndex])
        setContentView(binding.root)
        binding.selectionRV.setItemViewCacheSize(30)
        binding.selectionRV.setHasFixedSize(true)
        binding.selectionRV.layoutManager = LinearLayoutManager(this)
        adapter = MusicAdapter(this, MusicActivity.MusicListMA, selectionActivity = true)
        binding.selectionRV.adapter = adapter
        binding.backBtnSA.setOnClickListener { finish() }
        //for search View
        binding.searchViewSA.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean = true
            override fun onQueryTextChange(newText: String?): Boolean {
                MusicActivity.musicListSearch = ArrayList()
                if(newText != null){
                    val userInput = newText.lowercase()
                    for (song in MusicActivity.MusicListMA)
                        if(song.title.lowercase().contains(userInput))
                            MusicActivity.musicListSearch.add(song)
                    MusicActivity.search = true
                    adapter.updateMusicList(searchList = MusicActivity.musicListSearch)
                }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        //for black theme checking
        if(MusicActivity.themeIndex == 4)
        {
            binding.searchViewSA.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
        }
    }
}