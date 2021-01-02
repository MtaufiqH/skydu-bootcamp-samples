package skydu.android.rickymortydemo.ui.characters

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import skydu.android.rickymortydemo.databinding.ActivityMainBinding

class CharactersActivity : AppCompatActivity(), CharactersAdapter.CharacterItemListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupToolbar()
        setupRecyclerView()
        setContentView(binding.root)
    }

    private fun setupToolbar() {
        title = "Character List"
    }

    private fun setupRecyclerView() {
        adapter = CharactersAdapter(this)
        binding.charactersRv.layoutManager = LinearLayoutManager(this)
        binding.charactersRv.adapter = adapter
    }

    override fun onClickedCharacter(characterId: Int) {
        Toast.makeText(this, ""+ characterId, Toast.LENGTH_SHORT).show()
    }
}