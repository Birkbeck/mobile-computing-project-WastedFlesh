package viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.culinarycompanioncw.data.Recipe
import com.example.culinarycompanioncw.repository.RecipeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.kotlin.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RecipeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: RecipeRepository
    private lateinit var viewModel: RecipeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repository = mock()
        viewModel = RecipeViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun insertRecipe_callsRepository() = runTest {
        val recipe = Recipe(0, "Toast", "Bread", "Toast", "Breakfast")
        viewModel.insert(recipe)
        advanceUntilIdle()
        verify(repository).insert(recipe)
    }

    @Test
    fun deleteRecipe_callsRepository() = runTest {
        val recipe = Recipe(0, "Toast", "Bread", "Toast", "Breakfast")
        viewModel.delete(recipe)
        advanceUntilIdle()
        verify(repository).delete(recipe)
    }
}
