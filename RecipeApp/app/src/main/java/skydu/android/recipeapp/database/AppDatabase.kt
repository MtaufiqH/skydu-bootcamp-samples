package skydu.android.recipeapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RecipeEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipetDao
}