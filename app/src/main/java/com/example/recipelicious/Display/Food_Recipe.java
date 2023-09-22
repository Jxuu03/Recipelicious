package com.example.recipelicious.Display;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.recipelicious.Model.Recipe;
import com.example.recipelicious.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Food_Recipe extends AppCompatActivity {

    ImageView recipeImage;
    TextView recipeName;
    TextView ingredientDetails;
    LinearLayout ingredients;
    TextView instructionDetails;
    LinearLayout instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_recipe);

        recipeImage = findViewById(R.id.recipeImage);
        recipeName = findViewById(R.id.recipeName);

        ingredientDetails = findViewById(R.id.ingredientDetails);
        ingredients = findViewById(R.id.ingredients);

        instructionDetails = findViewById(R.id.instructionDetails);
        instructions = findViewById(R.id.instructions);

        ingredients.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        instructions.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String selectedFood = intent.getStringExtra("name");
        Glide.with(this).load(image).into(recipeImage);
        recipeName.setText(selectedFood);

        Recipe recipe = recipeFinder(selectedFood);
        if (recipe != null) {
            recipeName.setText(recipe.getName());
            ingredientDetails.setText(recipe.getIngredients());
            instructionDetails.setText(recipe.getInstructions());
        }
    }
    public void ingredientsExpand(View view) {
        int v = (ingredientDetails.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(ingredients, new AutoTransition());
        ingredientDetails.setVisibility(v);
    }

    public void instructionsExpand(View view) {
        int v = (instructionDetails.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(instructions, new AutoTransition());
        instructionDetails.setVisibility(v);
    }

    public Recipe recipeFinder(String recipeName) {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.food_recipe);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(recipeName)) {
                    String[] parts = line.split(";");
                    String name = parts[0];
                    String ingredients = parts[1].replace(":", "\n\n");
                    String instructions = parts[2].replace(":", "\n\n");

                    Recipe recipe = new Recipe();
                    recipe.setName(name);
                    recipe.setIngredients(ingredients);
                    recipe.setInstructions(instructions);

                    return recipe; // Return the recipe object
                }
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
