package skydu.android.recipeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import skydu.android.recipeapp.database.AppDatabaseHelper
import skydu.android.recipeapp.database.RecipeEntity
import skydu.android.recipeapp.databinding.ActivtyRecipeBinding
import skydu.android.recipeapp.thread.AppExecutors

class RecipeActivity : AppCompatActivity() {
    private val menuCreateId = 3;

    private lateinit var recipeAdapter: RecipeAdapter

    private var db_list: List<RecipeEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivtyRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val padding = resources.getDimensionPixelSize(R.dimen.padding) / 4

        val onItemClick = { recipe: Recipe ->
            val intent = Intent(this, RecipeDetailActivty::class.java)
            recipe.toBundle(intent)

            startActivity(intent)
        }

        val onBookmarkClick = { pos: Int ->

            db_list[pos].isBookmark = !db_list[pos].isBookmark
            updateRecipe(db_list[pos])

        }

        val onFavClick = { pos: Int ->
            db_list[pos].isFavorite = !db_list[pos].isFavorite
            updateRecipe(db_list[pos])
        }

        recipeAdapter = RecipeAdapter(onItemClick, onFavClick, onBookmarkClick)
        binding.recyclerView.run {
            clipToPadding = false
            setPadding(padding, padding, padding, padding)
            addItemDecoration(ItemOffsetDecoration(padding))


            layoutManager = GridLayoutManager(this@RecipeActivity, 2)
            adapter = recipeAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        AppExecutors.getInstance().diskIO().execute {
            AppDatabaseHelper.getInstance(this).recipeDao().getAll().run {
                db_list = this
                refreshDbData()
            }
        }
    }

    private fun updateRecipe(recipeEntity: RecipeEntity) {
        AppExecutors.getInstance().diskIO().execute {
            AppDatabaseHelper.getInstance(this).recipeDao().update(recipeEntity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.run {
            this.add(0, menuCreateId, 0, "Create")
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if (item.itemId == menuCreateId) {
            Intent(this, RecipeCreateActivity::class.java).run {
                startActivity(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refreshDbData() {
        db_list
            .map { RecipeUtil.convert(it) }
            .run {
                AppExecutors.getInstance().mainThread().execute {
                    submitData(this)
                    Toast.makeText(this@RecipeActivity, "Ganti ke db", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun submitData(data: List<Recipe>) {
        recipeAdapter.submitData(data)
    }
}