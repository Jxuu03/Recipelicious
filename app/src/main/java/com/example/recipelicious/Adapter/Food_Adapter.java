package com.example.recipelicious.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipelicious.Display.Food_Recipe;
import com.example.recipelicious.Model.Food;
import com.example.recipelicious.R;


import java.util.List;

public class Food_Adapter extends RecyclerView.Adapter<Food_Adapter.ViewHolder> {
    private List<Food> values;
    private Context context;

    public Food_Adapter (List <Food> values, Context context) {
        this.values = values;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textHeader;
        public TextView textFooter;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            imageView = (ImageView) v.findViewById(R.id.img);
            textHeader = (TextView) v.findViewById(R.id.firstLine);
            textFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.food_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Food food = values.get(position);
        holder.textHeader.setText(food.getName());
        int duration = food.getDurations();
        if (duration >= 60) {
            int hour = duration/60;
            int min = duration % 60;
            if (min == 0) {
                holder.textFooter.setText(hour + " hr(s) ");
            } else {
                holder.textFooter.setText(hour + " hr(s) " + "\n" + min + " Mins");
            }
        } else {
            holder.textFooter.setText(duration + " Mins");
        }

        Glide.with(context).load(food.getImage()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, food.getName(), Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(context, Food_Recipe.class);
                intent.putExtra("image", food.getImage());
                intent.putExtra("name", food.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
    public void filterList(List<Food> filteredList){
        values = filteredList;
        notifyDataSetChanged();
    }
}
