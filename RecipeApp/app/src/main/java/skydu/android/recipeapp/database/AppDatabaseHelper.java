package skydu.android.recipeapp.database;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseHelper {
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DatabaseConstant.DB_NAME)
                    .build();
        }
        return instance;
    }
}
