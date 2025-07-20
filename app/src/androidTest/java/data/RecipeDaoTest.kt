package com.yourappname.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
class RecipeDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: RecipeDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.recipeDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertRecipe() = runBlocking {
        val recipe = Recipe(0, "Toast", "Bread, Butter", "Toast the bread", "Breakfast")
        dao.insert(recipe)

        val allRecipes = dao.getAllRecipes()
        assertThat(allRecipes).contains(recipe)
    }

    @Test
    fun deleteRecipe() = runBlocking {
        val recipe = Recipe(0, "Toast", "Bread", "Toast", "Breakfast")
        dao.insert(recipe)
        dao.delete(recipe)

        val allRecipes = dao.getAllRecipes()
        assertThat(allRecipes).doesNotContain(recipe)
    }

    @Test
    fun updateRecipe() = runBlocking {
        val recipe = Recipe(0, "Toast", "Bread", "Toast", "Breakfast")
        dao.insert(recipe)
        val updated = recipe.copy(title = "French Toast")
        dao.update(updated)

        val allRecipes = dao.getAllRecipes()
        assertThat(allRecipes.first().title).isEqualTo("French Toast")
    }
}
