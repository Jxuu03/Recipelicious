package com.example.recipelicious.Display;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

public class Search_Result extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchView searchView;
    Food_Adapter food_adapter;
    List<Food> allFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        recyclerView = findViewById(R.id.searchResult);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        allFoods = new ArrayList<>();
        processRecipe(R.raw.food_recom, allFoods);
        food_adapter = new Food_Adapter(allFoods, this);
        recyclerView.setAdapter(food_adapter);

        String query = getIntent().getStringExtra("query");
        ArrayList<Food> filteredList = new ArrayList<>();
        for (Food food : allFoods) {
            if (food.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(food);
            }
        }
        food_adapter.filterList(filteredList);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                filterList(newText);
                searchView.clearFocus();
                searchView.setQuery("", false);
                hideKeyboard();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void processRecipe(int resourceId, List<Food> foods) {
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
            e.printStackTrace();}}

    public void filterList(String query) {
        ArrayList<Food> filteredResults = new ArrayList<>();
        for (Food food : allFoods) {
            if (food.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredResults.add(food);
            }
        }
        food_adapter.filterList(filteredResults);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}