package viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.culinarycompanioncw.data.AppDatabase
import com.example.culinarycompanioncw.data.Recipe
import com.example.culinarycompanioncw.repository.RecipeRepository
import data.AppDatabase
import data.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel<RecipeRepository>(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository
    val allRecipes: LiveData<List<Recipe>>

    init {
        val recipeDao = AppDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(recipeDao)
        allRecipes = repository.allRecipes
    }

    fun insert(recipe: Recipe) = viewModelScope.launch {
        repository.insert(recipe)
    }

    fun update(recipe: Recipe) = viewModelScope.launch {
        repository.update(recipe)
    }

    fun delete(recipe: Recipe) = viewModelScope.launch {
        repository.delete(recipe)
    }
}

private fun <RecipeRepository> RecipeRepository.insert(recipe: Recipe) {

}
private fun <RecipeRepository> RecipeRepository.update(recipe: Recipe) {

}
private fun <RecipeRepository> RecipeRepository.delete(recipe: Recipe) {

}