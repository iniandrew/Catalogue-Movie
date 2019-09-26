package com.andrew.moviecatalogue.ui.favorite.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.model.favorite.MovieFavorite;
import com.andrew.moviecatalogue.network.ApiConfiguration;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovieFavoriteViewHolder> {
    private List<MovieFavorite> items;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public MovieFavoriteAdapter(List<MovieFavorite> items) {
        this.items = items;
    }

    public MovieFavoriteAdapter() {
        this.items = new ArrayList<>();
    }

    public void setItems(List<MovieFavorite> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MovieFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new MovieFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieFavoriteViewHolder movieFavoriteViewHolder, int i) {
        movieFavoriteViewHolder.onBind(items.get(i));
        movieFavoriteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(items.get(movieFavoriteViewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MovieFavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvOverview;

        public MovieFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
        }

        void onBind(MovieFavorite item) {
            Glide.with(itemView).load(ApiConfiguration.POSTER_PATH + item.getPosterPath()).into(imgPoster);
            tvTitle.setText(item.getTitle());
            tvOverview.setText(item.getOverview());
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieFavorite data);
    }
}
