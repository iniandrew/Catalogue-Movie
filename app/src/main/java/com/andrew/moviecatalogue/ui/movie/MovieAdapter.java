package com.andrew.moviecatalogue.ui.movie;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.model.movie.MovieItems;
import com.andrew.moviecatalogue.network.ApiConfiguration;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<MovieItems> mData;
    private OnItemClickCallback onItemClickCallback;

    public MovieAdapter(ArrayList<MovieItems> mData) {
        this.mData = mData;
    }

    public void setData(ArrayList<MovieItems> items) {
        mData = new ArrayList<>();
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.onBind(mData.get(i));
        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(mData.get(movieViewHolder.getAdapterPosition()));
            }
        });
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

        void onBind(MovieItems item) {
            Glide.with(itemView).load(ApiConfiguration.POSTER_PATH + item.getPosterPath()).into(imgPoster);
            tvTitle.setText(item.getTitle());
            tvOverview.setText(item.getOverview());
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieItems data);
    }
}
