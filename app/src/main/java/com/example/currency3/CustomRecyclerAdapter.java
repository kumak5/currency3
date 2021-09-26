package com.example.currency3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>{
    List<String> names;
    List<String> values;

    public CustomRecyclerAdapter(List<String> names, List<String> values) {
        this.names = names;
        this.values = values;
    }

    public CustomRecyclerAdapter(List<String> names) {
        this.names = names;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textOne.setText(names.get(position));
        holder.textTwo.setText(values.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textOne;
        TextView textTwo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textOne = itemView.findViewById(R.id.textViewLarge);
            textTwo = itemView.findViewById(R.id.textViewSmall);
        }
    }
}
