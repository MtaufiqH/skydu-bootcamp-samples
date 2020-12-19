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
    private val menuDBid = 1;
    private val menuHardcodeId = 2;

    private lateinit var recipeAdapter: RecipeAdapter

    private var db_list: List<RecipeEntity> = emptyList()

    private var is_db_mode = false

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
            if(is_db_mode) {
                db_list[pos].isBookmark = !db_list[pos].isBookmark
                updateRecipe(db_list[pos])
            }
        }

        val onFavClick = { pos: Int ->
            if(is_db_mode) {
                db_list[pos].isFavorite = !db_list[pos].isFavorite
                updateRecipe(db_list[pos])
            }
        }

        recipeAdapter = RecipeAdapter(onItemClick, onFavClick, onBookmarkClick)
        binding.recyclerView.run {
            clipToPadding = false
            setPadding(padding, padding, padding, padding)
            addItemDecoration(ItemOffsetDecoration(padding))


            layoutManager = GridLayoutManager(this@RecipeActivity, 2)
            adapter = recipeAdapter.apply {
                submitData(getData())
            }
        }
    }

    private fun updateRecipe(recipeEntity: RecipeEntity) {
        AppExecutors.getInstance().diskIO().execute {
            AppDatabaseHelper.getInstance(this).recipeDao().update(recipeEntity)
        }
    }

    private fun getData() = listOf(
        Recipe(
            name = "Kue Nastar",
            image = R.drawable.img_recipe1,
            isFavorite = false,
            isBookmark = false
        ),
        Recipe(
            name = "Putri Salju",
            image = R.drawable.img_recipe2,
            isFavorite = false,
            isBookmark = false
        ),
        Recipe(
            name = "Kastengel",
            image = R.drawable.img_recipe3,
            isFavorite = false,
            isBookmark = false
        ),
        Recipe(
            name = "Sagu Keju",
            image = R.drawable.img_recipe4,
            isFavorite = false,
            isBookmark = false
        ),
        Recipe(
            name = "Ayam Woku",
            image = R.drawable.img_recipe1,
            isFavorite = false,
            isBookmark = false
        ),
        Recipe(
            name = "Rendang Iga",
            image = R.drawable.img_recipe2,
            isFavorite = false,
            isBookmark = false
        ),
        Recipe(
            name = "Sup Labu Keju",
            image = R.drawable.img_recipe3,
            isFavorite = false,
            isBookmark = false
        ),
        Recipe(
            name = "Pasta a la Mamah",
            image = R.drawable.img_recipe4,
            isFavorite = false,
            isBookmark = false
        )
    )

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.run {
            this.add(0, menuDBid, 0, "Change to DB")
            this.add(0, menuHardcodeId, 0, "Change to Hardcode")
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == menuDBid) {
            is_db_mode = true
            AppExecutors.getInstance().diskIO().execute {
                AppDatabaseHelper.getInstance(this).recipeDao().getAll().run {
                    db_list = this
                    refreshDbData()
                }
            }
        } else if (item.itemId == menuHardcodeId) {
            is_db_mode = false
            submitData(getData())
            Toast.makeText(this, "Ganti ke hardcode", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refreshDbData() {
        db_list
            .mapIndexed { index, recipeEntity ->
                Recipe(
                    name = recipeEntity.name,
                    isFavorite = recipeEntity.isFavorite,
                    isBookmark = recipeEntity.isBookmark,
                    recipeBahan = recipeEntity.recipeBahan,
                    recipeCara = recipeEntity.recipeCara,
                    image = when (index % 4) {
                        0 -> {
                            R.drawable.img_recipe1
                        }
                        1 -> {
                            R.drawable.img_recipe2
                        }
                        2 -> {
                            R.drawable.img_recipe3
                        }
                        else -> {
                            R.drawable.img_recipe4
                        }
                    }
                )
            }
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