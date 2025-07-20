package repository

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.example.culinarycompanioncw.data.Recipe
import com.example.culinarycompanioncw.data.RecipeDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.kotlin.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import com.google.common.truth.Truth.assertThat
import data.Recipe
import data.RecipeDao
import kotlinx.coroutines.launch

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RecipeRepositoryTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var recipeDao: RecipeDao
    private lateinit var repository: RecipeRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        recipeDao = mock()
        repository = RecipeRepository(recipeDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun insertRecipe_callsDaoInsert() = runTest {
        val recipe = Recipe(1, "Toast", "Bread", "Toast bread", "Breakfast")
        repository.insert(recipe)
        verify(recipeDao).insert(recipe)
    }

    @Test
    fun deleteRecipe_callsDaoDelete() = runTest {
        val recipe = Recipe(1, "Toast", "Bread", "Toast bread", "Breakfast")
        repository.delete(recipe)
        verify(recipeDao).delete(recipe)
    }

    @Test
    fun updateRecipe_callsDaoUpdate() = runTest {
        val recipe = Recipe(1, "Toast", "Bread", "Toast bread", "Breakfast")
        repository.update(recipe)
        verify(recipeDao).update(recipe)
    }

    @Test
    fun getAllRecipes_returnsFlowFromDao() = runTest {
        val recipes = listOf(
            Recipe(1, "Toast", "Bread", "Toast bread", "Breakfast"),
            Recipe(2, "Salad", "Lettuce", "Mix it", "Lunch")
        )
        whenever(recipeDao.getAllRecipes()).thenReturn(flowOf(recipes))

        val result = repository.getAllRecipes()
        val emitted = mutableListOf<List<Recipe>>()

        val job = launch {
            result.collect {
                emitted.add(it)
            }
        }

        advanceUntilIdle()
        job.cancel()

        assertThat(emitted.first().toString()).containsExactlyElementsIn(recipes)
    }
}
