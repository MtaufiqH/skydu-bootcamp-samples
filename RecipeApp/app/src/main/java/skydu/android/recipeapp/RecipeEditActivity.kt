package skydu.android.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import skydu.android.recipeapp.database.AppDatabaseHelper
import skydu.android.recipeapp.database.RecipeEntity
import skydu.android.recipeapp.databinding.ActivityRecipeCreateBinding
import skydu.android.recipeapp.databinding.ActivityRecipeEditBinding
import skydu.android.recipeapp.thread.AppExecutors

class RecipeEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindings = ActivityRecipeEditBinding.inflate(layoutInflater)

        val id = intent.getIntExtra("id", 0)

        AppExecutors.getInstance().diskIO().execute {
            AppDatabaseHelper.getInstance(this).recipeDao().getById(id)
                .run {
                    bindings.edittextId.setText(this.uid.toString())
                    bindings.edittextName.setText(this.name)
                    bindings.edittextBahan.setText(this.recipeBahan)
                    bindings.edittextCara.setText(this.recipeCara)
                    bindings.bookmark.isChecked = this.isBookmark
                    bindings.favorite.isChecked = this.isFavorite
                }
        }

        bindings.btnSave.setOnClickListener {
            val name = bindings.edittextName.text.toString()
            val bahan = bindings.edittextBahan.text.toString()
            val cara = bindings.edittextCara.text.toString()

            var success = true
            if(name.isEmpty()) {
                bindings.edittextName.error = "Nama tidak boleh kosong"
                success = false
            }
            if(bahan.isEmpty()) {
                bindings.edittextBahan.error = "Bahan tidak boleh kosong"
                success = false
            }

            if(cara.isEmpty()) {
                bindings.edittextCara.error = "cara tidak boleh kosong"
                success = false
            }

            if(success) {
                AppExecutors.getInstance().diskIO().execute {
                    val database = AppDatabaseHelper.getInstance(this)
                    database.recipeDao().update(
                        RecipeEntity(
                            id,
                            name,
                            bindings.bookmark.isChecked,
                            bindings.favorite.isChecked,
                            bahan,
                            cara
                        )
                    )
                    finish()
                }
            }
        }

        setContentView(bindings.root)
    }
}