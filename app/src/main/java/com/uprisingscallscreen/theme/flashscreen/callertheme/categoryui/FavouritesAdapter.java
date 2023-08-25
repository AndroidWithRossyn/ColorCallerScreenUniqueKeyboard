package com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uprisingscallscreen.theme.flashscreen.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {
    private ArrayList<Images> favoriteDataList;
    private Context context;

    public FavouritesAdapter(Context context, ArrayList<Images> favoriteDataList) {
        this.context = context;
        this.favoriteDataList = favoriteDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Images data = favoriteDataList.get(position);
        Picasso.get().load(data.getUrl()).into(holder.favoriteImage);
    }

    @Override
    public int getItemCount() {
        return favoriteDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favoriteImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favoriteImage = itemView.findViewById(R.id.imageView);
        }
    }
}