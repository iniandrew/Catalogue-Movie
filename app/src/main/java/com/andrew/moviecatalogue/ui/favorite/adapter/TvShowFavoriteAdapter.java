package com.andrew.moviecatalogue.ui.favorite.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.model.favorite.TvShowFavorite;
import com.andrew.moviecatalogue.network.ApiConfiguration;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class TvShowFavoriteAdapter extends RecyclerView.Adapter<TvShowFavoriteAdapter.TvShowFavoriteViewHolder> {
    private List<TvShowFavorite> items;
    private OnItemClickCallback onItemClickCallback;

    public TvShowFavoriteAdapter(List<TvShowFavorite> items) {
        this.items = items;
    }

    public TvShowFavoriteAdapter() {
        this.items = new ArrayList<>();
    }

    public void setItems(List<TvShowFavorite> items) {
        this.items = items;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public TvShowFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new TvShowFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowFavoriteViewHolder tvShowFavoriteViewHolder, int i) {
        tvShowFavoriteViewHolder.onBind(items.get(i));
        tvShowFavoriteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(items.get(tvShowFavoriteViewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class TvShowFavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvOverview;

        public TvShowFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
        }

        void onBind(TvShowFavorite item) {
            Glide.with(itemView).load(ApiConfiguration.POSTER_PATH + item.getPosterPath()).into(imgPoster);
            tvTitle.setText(item.getTitle());
            tvOverview.setText(item.getOverview());
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShowFavorite data);
    }
}
