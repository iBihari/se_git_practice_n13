package com.example.vwc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {

    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private Context context;


    public CategoryRVAdapter(ArrayList<CategoryRVModal> categoryRVModalArrayList, Context context) {
        this.categoryRVModalArrayList = categoryRVModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, int position) {
        CategoryRVModal categoryRVModal = categoryRVModalArrayList.get(position);
        holder.categoryTV.setText(categoryRVModal.getCategory());
        Glide.with(Context).load(categoryRVModal.getCategoryIVUrl()).into(holder.categoryIV);

    }

    @Override
    public int getItemCount() {
        return categoryRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView categoryTV;
        private ImageView categoryIV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIV = itemView.findViewById(R.id.idIVCategory);
            categoryTV = itemView.findViewById(R.id.idRVCategory);

        }
    }
}
