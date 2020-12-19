package skydu.android.recipeapp.database

import android.provider.ContactsContract
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConstant.RECEIPT_TABLE_NAME)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = DatabaseConstant.COLUMN_ID) val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: Int,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean,
    @ColumnInfo(name = "isBookmark") val isBookmark: Boolean,
    @ColumnInfo(name = "recipeBahan") val recipeBahan: String,
    @ColumnInfo(name = "recipeCara") val recipeCara: String
)