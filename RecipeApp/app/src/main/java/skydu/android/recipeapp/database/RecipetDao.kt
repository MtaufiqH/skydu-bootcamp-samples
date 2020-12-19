package skydu.android.recipeapp.database

import androidx.room.*
import skydu.android.recipeapp.Recipe

@Dao
interface RecipetDao {
    @Query("SELECT * FROM ${DatabaseConstant.RECEIPT_TABLE_NAME}")
    fun getAll(): List<RecipeEntity>

    @Query("SELECT * FROM ${DatabaseConstant.RECEIPT_TABLE_NAME} WHERE ${DatabaseConstant.COLUMN_ID} is (:id)")
    fun getById(id: Int): RecipeEntity

    @Insert
    fun insert(recipe: RecipeEntity): Long

    @Update
    fun update(recipe: RecipeEntity)

    @Delete
    fun delete(recipe: RecipeEntity)
}