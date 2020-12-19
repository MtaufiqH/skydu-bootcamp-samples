package skydu.android.recipeapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConstant.RECEIPT_TABLE_NAME)
data class RecipeEntity constructor(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DatabaseConstant.COLUMN_ID) val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean,
    @ColumnInfo(name = "isBookmark") var isBookmark: Boolean,
    @ColumnInfo(name = "recipeBahan") val recipeBahan: String,
    @ColumnInfo(name = "recipeCara") val recipeCara: String,
    @ColumnInfo(name = "notes") val notes: String?
)