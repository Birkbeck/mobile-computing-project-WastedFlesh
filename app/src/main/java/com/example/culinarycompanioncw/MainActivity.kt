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
            // TODO: Handle click (edit/delete later)
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
