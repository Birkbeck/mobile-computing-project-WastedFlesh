package com.example.culinarycompanioncw

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.culinarycompanioncw.databinding.ActivityMainBinding
import com.example.culinarycompanioncw.viewmodel.RecipeViewModel
import com.example.culinarycompanioncw.data.Recipe

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recipeAdapter: RecipeAdapter

    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout with view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize adapter with click handler
        recipeAdapter = RecipeAdapter { recipe ->
            val intent = Intent(this, AddEditRecipeActivity::class.java).apply {
                putExtra("RECIPE_ID", recipe.id)
                putExtra("RECIPE_TITLE", recipe.title)
                putExtra("RECIPE_INGREDIENTS", recipe.ingredients)
                putExtra("RECIPE_INSTRUCTIONS", recipe.instructions)
                putExtra("RECIPE_CATEGORY", recipe.category)
            }
            startActivity(intent)
        }


        // Set up RecyclerView
        binding.recipeRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recipeAdapter
        }

        // Observe LiveData from ViewModel
        recipeViewModel.allRecipes.observe(this) { recipes ->
            recipeAdapter.submitList(recipes)
        }

        // Add button to open Add/Edit screen
        binding.addRecipeButton.setOnClickListener {
            val intent = Intent(this, AddEditRecipeActivity::class.java)
            startActivity(intent)
        }
    }
}
