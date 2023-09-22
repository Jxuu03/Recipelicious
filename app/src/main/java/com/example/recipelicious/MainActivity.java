package com.example.recipelicious;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.text.style.ForegroundColorSpan;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.recipelicious.Adapter.Ic_Cuisine_Adapter;
import com.example.recipelicious.Display.Cuisine_List;
import com.example.recipelicious.Adapter.Food_Adapter;
import com.example.recipelicious.Display.Search_Result;
import com.example.recipelicious.Model.Cuisine;
import com.example.recipelicious.Model.Food;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Cuisine> ic_cuisine;
    SearchView searchView;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    TextView textView;
    Food_Adapter foodAdapter;
    Ic_Cuisine_Adapter ic_cuisine_adapter;
    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.LayoutManager layoutManager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cuisine Horizontal
        recyclerView1 = findViewById(R.id.horizonCuisines);
        layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager1);

        ic_cuisine = new ArrayList<Cuisine>();
        ic_cuisine.add(new Cuisine(R.drawable.thai, "Thai Cuisine"));
        ic_cuisine.add(new Cuisine(R.drawable.korean, "Korean Cuisine"));
        ic_cuisine.add(new Cuisine(R.drawable.indian, "Indian Cuisine"));
        ic_cuisine.add(new Cuisine(R.drawable.italian, "Italian Cuisine"));

        ic_cuisine_adapter = new Ic_Cuisine_Adapter(ic_cuisine, this);
        recyclerView1.setAdapter(ic_cuisine_adapter);

        //Change color of text
        textView = findViewById(R.id.tv1);
        SpannableString spannableString = new SpannableString("Have no idea?\nTry out Today's Recommends!");
        int start = spannableString.toString().indexOf('\n') + 9;
        int end = spannableString.length();
        int colorRes = R.color.purple;
        int textColor = ContextCompat.getColor(this, colorRes);
        spannableString.setSpan(new ForegroundColorSpan(textColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);

        //Recom Recipe
        recyclerView2 = findViewById(R.id.recomRecipe);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        List<Food> recomFoods = new ArrayList<>();
        processRecom(R.raw.food_recom, recomFoods);
        System.out.println(recomFoods);
        foodAdapter = new Food_Adapter(recomFoods, this);
        recyclerView2.setAdapter(foodAdapter);

        //Search Bar
        searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, Search_Result.class);
                intent.putExtra("query", query);
                hideKeyboard();
                searchView.clearFocus();
                searchView.setQuery("", false);
                startActivity(intent);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    //Method
    public void allCuisines(View view) {
        Intent intent = new Intent(MainActivity.this, Cuisine_List.class);
        startActivity(intent);
    }
    private void processRecom(int resourceId, List<Food> foods) {
        try {
            InputStream inputStream = getResources().openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            Collections.shuffle(lines);
            int count = Math.min(5, lines.size());
            for (int i = 0; i < count; i++) {
                String[] parts = lines.get(i).split(";");
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
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}