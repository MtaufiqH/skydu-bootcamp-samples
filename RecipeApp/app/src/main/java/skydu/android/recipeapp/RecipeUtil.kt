package skydu.android.recipeapp

import skydu.android.recipeapp.database.RecipeEntity

object RecipeUtil {
    fun convert(recipeEntity: RecipeEntity): Recipe {
        return Recipe(
            id = recipeEntity.uid,
            name = recipeEntity.name,
            isFavorite = recipeEntity.isFavorite,
            isBookmark = recipeEntity.isBookmark,
            recipeBahan = recipeEntity.recipeBahan,
            recipeCara = recipeEntity.recipeCara,
            image = when (recipeEntity.uid % 4) {
                0 -> {
                    R.drawable.img_recipe1
                }
                1 -> {
                    R.drawable.img_recipe2
                }
                2 -> {
                    R.drawable.img_recipe3
                }
                else -> {
                    R.drawable.img_recipe4
                }
            }
        )
    }
}