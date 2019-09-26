package com.andrew.moviecatalogueconsumer.ui.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.moviecatalogueconsumer.BuildConfig;
import com.andrew.moviecatalogueconsumer.R;
import com.andrew.moviecatalogueconsumer.entity.MovieItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<MovieItem> mData;

    public MovieAdapter(ArrayList<MovieItem> mData) {
        this.mData = mData;
    }

    public void setData(ArrayList<MovieItem> items) {
        mData = new ArrayList<>();
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.onBind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvOverview;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
        }

        void onBind(MovieItem item) {
            Glide.with(itemView).load(BuildConfig.TMDB_POSTER_PATH_URL + item.getPosterPath()).into(imgPoster);
            tvTitle.setText(item.getTitle());
            tvOverview.setText(item.getOverview());
        }
    }
}
