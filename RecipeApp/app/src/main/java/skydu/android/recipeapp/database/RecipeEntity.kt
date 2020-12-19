package skydu.android.recipeapp.database

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: Int,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean,
    @ColumnInfo(name = "isBookmark") val isBookmark: Boolean,
    @ColumnInfo(name = "recipeBahan") val recipeBahan: String,
    @ColumnInfo(name = "recipeCara") val recipeCara: String
)