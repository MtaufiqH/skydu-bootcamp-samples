package skydu.android.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import skydu.android.recipeapp.database.AppDatabaseHelper
import skydu.android.recipeapp.database.RecipeEntity
import skydu.android.recipeapp.databinding.ActivityRecipeCreateBinding
import skydu.android.recipeapp.thread.AppExecutors

class RecipeCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindings = ActivityRecipeCreateBinding.inflate(layoutInflater)

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
                    val id = database.recipeDao().insert(
                        RecipeEntity(
                            0,
                            name,
                            false,
                            false,
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