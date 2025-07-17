package repository

import androidx.lifecycle.LiveData
import com.example.culinarycompanioncw.data.Recipe
import com.example.culinarycompanioncw.data.RecipeDao
import data.Recipe
import data.RecipeDao

class RecipeRepository(private val recipeDao: RecipeDao) {

    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    suspend fun insert(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun update(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }
}