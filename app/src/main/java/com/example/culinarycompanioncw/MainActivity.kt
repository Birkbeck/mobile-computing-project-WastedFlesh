package com.example.culinarycompanioncw

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yourappname.R
import com.yourappname.data.Recipe
import com.yourappname.databinding.ActivityMainBinding
import com.yourappname.viewmodel.RecipeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recipeAdapter: RecipeAdapter

    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        recipeViewModel.allRecipes.observe(this) { recipes ->
            recipeAdapter.submitList(recipes)
        }

        binding.addRecipeButton.setOnClickListener {
            val intent = Intent(this, AddEditRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter { recipe ->
            // Handle recipe click (e.g., open edit activity)
        }

        binding.recipeRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recipeAdapter
        }
    }
}
