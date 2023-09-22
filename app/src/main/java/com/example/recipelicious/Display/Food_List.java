package com.example.recipelicious.Display;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipelicious.Adapter.Food_Adapter;
import com.example.recipelicious.Model.Food;
import com.example.recipelicious.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Food_List extends AppCompatActivity {

    private Food_Adapter foodAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        String selectedCuisine = intent.getExtras().getString("cuisine");

        List<Food> foods = new ArrayList<>();
        switch (selectedCuisine) {
            case "Thai Cuisine":
                processCuisineFile(R.raw.thai_food_list, foods);
                break;
            case "Japanese Cuisine":
                processCuisineFile(R.raw.japanese_food_list, foods);
                break;
            case "Korean Cuisine":
                processCuisineFile(R.raw.korean_food_list, foods);
                break;
            case "Italian Cuisine":
                processCuisineFile(R.raw.italian_food_list, foods);
                break;
            case "Indian Cuisine":
                processCuisineFile(R.raw.indian_food_list, foods);
                break;
            case "French Cuisine":
                processCuisineFile(R.raw.french_food_list, foods);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedCuisine);
        };

        foodAdapter = new Food_Adapter(foods, this);
        recyclerView.setAdapter(foodAdapter);
    }
    public void processCuisineFile(int resourceId, List<Food> foods) {
        try {
            InputStream inputStream = getResources().openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length >= 3) {
                    String name = parts[0];
                    String image = parts[1];
                    int duration = Integer.parseInt(parts[2]);

                    Food food = new Food();
                    food.setName(name);
                    food.setImage(image);
                    food.setDurations(duration);
                    foods.add(food);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}}