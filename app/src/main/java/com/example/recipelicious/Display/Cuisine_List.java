package com.example.recipelicious.Display;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipelicious.Adapter.Cuisine_Adapter;
import com.example.recipelicious.Model.Cuisine;
import com.example.recipelicious.R;

import java.util.ArrayList;

public class Cuisine_List extends AppCompatActivity {

    ArrayList<Cuisine> cuisine_;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuisine_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        cuisine_ = new ArrayList<Cuisine>();
        cuisine_.add(new Cuisine(R.drawable.thai, "Thai Cuisine"));
        cuisine_.add(new Cuisine(R.drawable.japanese, "Japanese Cuisine"));
        cuisine_.add(new Cuisine(R.drawable.korean, "Korean Cuisine"));
        cuisine_.add(new Cuisine(R.drawable.indian, "Indian Cuisine"));
        cuisine_.add(new Cuisine(R.drawable.italian, "Italian Cuisine"));
        cuisine_.add(new Cuisine(R.drawable.french, "French Cuisine"));

        Cuisine_Adapter cuisine_adapter = new Cuisine_Adapter(cuisine_, this);
        recyclerView.setAdapter(cuisine_adapter);
    }
}