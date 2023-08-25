package com.uprisingscallscreen.theme.flashscreen.callertheme.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uprisingscallscreen.theme.flashscreen.R;
import com.uprisingscallscreen.theme.flashscreen.ads.Song;
import com.uprisingscallscreen.theme.flashscreen.ui.OnBoardingActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ParseAdapter extends RecyclerView.Adapter<ParseAdapter.ViewHolder> {

    private ArrayList<Song> parseItems;
    private Context context;

    public ParseAdapter(ArrayList<Song> parseItems, Context context) {
        this.parseItems = parseItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song parseItem = parseItems.get(position);
        Picasso.get().load(parseItem.getUri()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return parseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Song parseItem = parseItems.get(position);
            Intent intent = new Intent(context, OnBoardingActivity.class);
            intent.putExtra("image", parseItem.getUri());
            context.startActivity(intent);
        }
    }

    public void setFilter (ArrayList<Song> newList) {
        parseItems = new ArrayList<>();
        parseItems.addAll(newList);
        notifyDataSetChanged();
    }
}
