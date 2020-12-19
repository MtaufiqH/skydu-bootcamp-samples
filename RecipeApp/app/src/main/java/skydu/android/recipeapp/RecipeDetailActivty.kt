package skydu.android.recipeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import skydu.android.recipeapp.database.AppDatabaseHelper
import skydu.android.recipeapp.database.RecipeEntity
import skydu.android.recipeapp.databinding.ActivityRecipeDetailBinding
import skydu.android.recipeapp.thread.AppExecutors

class RecipeDetailActivty : AppCompatActivity() {
    val menuEditId = 1;
    val menuDeleteId = 2;
    lateinit var recipe: Recipe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRecipeDetailBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        recipe = BundleParam.fromBundle<Recipe>(intent.extras)

        binding.imgRecipe.setImageResource(recipe.image)
        binding.txtTitle.text = recipe.name

        binding.txtBahan.text = recipe.recipeBahan
        binding.txtCara.text = recipe.recipeCara

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.run {
            add(0, menuEditId, 0, "Edit")
            add(0, menuDeleteId, 0, "Delete")
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if(item.itemId == menuEditId) {
            Intent(this, RecipeEditActivity::class.java).run {
                putExtra("id", recipe.id)
                startActivity(this)
            }
        }else if(item.itemId == menuDeleteId) {
           AppExecutors.getInstance().diskIO().execute {
               AppDatabaseHelper.getInstance(this).recipeDao().delete(
                   RecipeEntity(recipe.id, "",false, false, "","")
               )
               finish()
           }
        }
        return super.onOptionsItemSelected(item)
    }


}