package com.example.recipelicious.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipelicious.Display.Food_List;
import com.example.recipelicious.Model.Cuisine;
import com.example.recipelicious.R;
import java.util.ArrayList;

public class Ic_Cuisine_Adapter extends RecyclerView.Adapter<Ic_Cuisine_Adapter.ViewHolder> {

    private ArrayList<Cuisine> Cuisine;
    private Context context;

    public Ic_Cuisine_Adapter(ArrayList<Cuisine> ic_cuisine, Context context) {
        Cuisine = ic_cuisine;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.ic_cuisine, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.imageView.setImageResource(Cuisine.get(position).getImg());
        holder.textView.setText(Cuisine.get(position).getText());
        holder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String cuisineName = Cuisine.get(position).getText();
                Toast toast = Toast.makeText(context, cuisineName, Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(context, Food_List.class);
                intent.putExtra("cuisine", Cuisine.get(position).getText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return Cuisine.size(); }
}