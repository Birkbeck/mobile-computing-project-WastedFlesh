package com.example.culinarycompanioncw

import android.R
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.culinarycompanioncw.R
import com.example.culinarycompanioncw.data.Recipe
import com.example.culinarycompanioncw.viewmodel.RecipeViewModel

class AddEditRecipeActivity<RecipeViewModel> : AppCompatActivity() {

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_recipe)

        val titleEditText = findViewById<EditText>(R.id.titleEditText)
        val ingredientsEditText = findViewById<EditText>(R.id.ingredientsEditText)
        val instructionsEditText = findViewById<EditText>(R.id.instructionsEditText)
        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
        val saveButton = findViewById<Button>(R.id.saveButton)

        // Set up category spinner
        val categories = listOf("Breakfast", "Brunch", "Lunch", "Dinner", "Desserts", "Other")
        categorySpinner.adapter = ArrayAdapter(
            this,
            R.layout.simple_spinner_dropdown_item,
            categories
        )

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val ingredients = ingredientsEditText.text.toString()
            val instructions = instructionsEditText.text.toString()
            val category = categorySpinner.selectedItem.toString()

            if (title.isNotBlank() && ingredients.isNotBlank() && instructions.isNotBlank()) {
                val newRecipe = Recipe(
                    title = title,
                    ingredients = ingredients,
                    instructions = instructions,
                    category = category
                )
                viewModel.insert(newRecipe)
                finish() // Go back to main screen
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
