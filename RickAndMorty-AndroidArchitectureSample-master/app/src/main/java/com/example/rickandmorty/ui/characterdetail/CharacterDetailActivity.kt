package com.example.rickandmorty.ui.characterdetail

import android.R
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.databinding.ActivityCharacterDetailBinding
import com.example.rickandmorty.utils.Result

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding


    private val viewModel: CharacterDetailViewModel by viewModels() {
        CharacterDetailViewModelFactory(
            intent.extras?.getInt("id", -1) ?: -1
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)


        setupToolbar()
        setupObservers()

        setContentView(binding.root)
    }

    private fun setupToolbar() {

        setSupportActionBar(binding.toolbar)
        title = "Character Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObservers() {
        viewModel.character.observe(this, Observer {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    bindCharacter(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    binding.characterCl.visibility = View.VISIBLE
                }

                Result.Status.ERROR ->
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                Result.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.characterCl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindCharacter(character: Character) {
        binding.name.text = character.name
        binding.species.text = character.species
        binding.status.text = character.status
        binding.gender.text = character.gender
        Glide.with(binding.root)
            .load(character.image)
            .transform(CircleCrop())
            .into(binding.image)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}