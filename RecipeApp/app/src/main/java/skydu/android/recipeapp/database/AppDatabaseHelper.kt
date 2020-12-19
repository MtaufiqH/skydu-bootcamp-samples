package skydu.android.recipeapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import skydu.android.recipeapp.thread.AppExecutors

object AppDatabaseHelper {
    private var instance: AppDatabase? = null
    @Synchronized
    fun getInstance(context: Context): AppDatabase {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DatabaseConstant.DB_NAME
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        AppExecutors.getInstance().diskIO().execute {
                            getInstance(context).recipeDao().run {
                                for (i in 0..10) {
                                    this.insert(
                                        RecipeEntity(
                                            0,
                                            "Nama $i",
                                            false,
                                            false,
                                            "bahan $i",
                                            "cara $i"
                                        )
                                    )
                                }
                            }
                        }
                    }
                })
                .build()
        }
        return instance!!
    }
}