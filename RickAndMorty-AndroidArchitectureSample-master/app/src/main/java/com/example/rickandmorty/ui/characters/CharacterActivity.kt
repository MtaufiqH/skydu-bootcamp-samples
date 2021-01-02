package com.example.rickandmorty.ui.characters

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.ui.characterdetail.CharacterDetailActivity
import com.example.rickandmorty.utils.Result

class CharacterActivity : AppCompatActivity(), CharactersAdapter.CharacterItemListener {
    private val viewModel: CharactersViewModel by viewModels()

    private val MENU_REFRESH = 0
    private val MENU_REFRESH_ERROR = 1
    private val MENU_CLEAR = 2


    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupToolbar()
        setupRecyclerView()
        setupObservers()
        setContentView(binding.root)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        title = "Character List"
    }

    private fun setupRecyclerView() {
        adapter = CharactersAdapter(this)
        binding.charactersRv.layoutManager = LinearLayoutManager(this)
        binding.charactersRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.characters.observe(this, Observer {
            when (it.status) {
                Result.Status.CACHE -> {
                    binding.info.text = "SHOW FROM CACHE"
                    binding.progressBar.visibility = View.GONE
                    binding.charactersRv.visibility = View.VISIBLE
                    showItems(it.data)
                }
                Result.Status.SUCCESS -> {
                    binding.info.text = "SHOW FROM SERVER"
                    binding.progressBar.visibility = View.GONE
                    binding.charactersRv.visibility = View.VISIBLE
                    showItems(it.data)
                }
                Result.Status.ERROR -> {
                    if (it.data != null) {
                        binding.info.text = "ERROR, SHOWING_CACHE"
                        showItems(it.data)
                    } else {
                        binding.info.text = "ERROR, NO CACHE"
                    }
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Result.Status.LOADING -> {
                    binding.info.text = "LOADING..."
                    binding.charactersRv.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

            }
        })
    }

    private fun showItems(items: List<Character>?) {
        adapter.setItems(ArrayList(items ?: emptyList()))

    }

    override fun onClickedCharacter(characterId: Int) {
        Intent(this, CharacterDetailActivity::class.java).run {
            putExtra("id", characterId)
            startActivity(this)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.run {
            menu.add(0, MENU_REFRESH, 0, "Refresh")
            menu.add(0, MENU_REFRESH_ERROR, 0, "Refresh ERROR")
            menu.add(0, MENU_CLEAR, 0, "Clear")
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == MENU_REFRESH) {
            viewModel.refresh()
        }
        if (item.itemId == MENU_REFRESH_ERROR) {
            viewModel.refreshError()
        }
        if (item.itemId == MENU_CLEAR) {
            viewModel.clear()
        }
        return super.onOptionsItemSelected(item)
    }


}
