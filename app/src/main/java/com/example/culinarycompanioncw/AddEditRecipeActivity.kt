package com.example.culinarycompanioncw

import android.R
import android.os.Bundle
import android.view.View
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
        if (isEditMode) {
            titleEditText.setText(intent.getStringExtra("RECIPE_TITLE"))
            ingredientsEditText.setText(intent.getStringExtra("RECIPE_INGREDIENTS"))
            instructionsEditText.setText(intent.getStringExtra("RECIPE_INSTRUCTIONS"))

            val categories = resources.getStringArray(R.array.recipe_categories)
            val passedCategory = intent.getStringExtra("RECIPE_CATEGORY")
            val selectedIndex = categories.indexOf(passedCategory)
            if (selectedIndex >= 0) {
                categorySpinner.setSelection(selectedIndex)
            }

            saveButton.setText("Update Recipe")
        }


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
                val updatedRecipe = Recipe(
                    id = if (isEditMode) recipeId else 0,
                    title = title,
                    ingredients = ingredients,
                    instructions = instructions,
                    category = category
                )

                if (isEditMode) {
                    val deleteButton = findViewById<Button>(R.id.deleteButton)

                    if (isEditMode) {
                        deleteButton.visibility = View.VISIBLE
                    } else {
                        deleteButton.visibility = View.GONE
                    }

                    viewModel.update(updatedRecipe)
                } else {
                    viewModel.insert(updatedRecipe)
                }

                finish()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
        deleteButton.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Delete Recipe")
            builder.setMessage("Are you sure you want to delete this recipe?")
            builder.setPositiveButton("Yes") { _, _ ->
                val recipeToDelete = Recipe(
                    id = recipeId,
                    title = "", // The rest can be empty — only ID is needed
                    ingredients = "",
                    instructions = "",
                    category = ""
                )
                viewModel.delete(recipeToDelete)
                finish()
            }
            builder.setNegativeButton("No", null)
            builder.show()
        }


    }
}
