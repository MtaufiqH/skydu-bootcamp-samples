package skydu.android.recipeapp.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object AppDatabaseMigrations {
    fun getMigrationsV1to2(): Migration {
        return object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE receipt ADD COLUMN notes TEXT")
            }
        }
    }
}