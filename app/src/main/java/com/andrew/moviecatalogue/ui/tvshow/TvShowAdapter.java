package com.andrew.moviecatalogue.ui.tvshow;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrew.moviecatalogue.R;
import com.andrew.moviecatalogue.model.tvshow.TvShowItems;
import com.andrew.moviecatalogue.network.ApiConfiguration;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {
    private ArrayList<TvShowItems> mData;
    private OnItemClickCallback onItemClickCallback;

    public TvShowAdapter(ArrayList<TvShowItems> mData) {
        this.mData = mData;
    }

    void setData(ArrayList<TvShowItems> items) {
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
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new TvShowViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowViewHolder tvShowViewHolder, int i) {
        tvShowViewHolder.onBind(mData.get(i));
        tvShowViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(mData.get(tvShowViewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvOverview;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
        }

        void onBind(TvShowItems item) {
            Glide.with(itemView).load(ApiConfiguration.POSTER_PATH + item.getPosterPath()).into(imgPoster);
            tvTitle.setText(item.getTitle());
            tvOverview.setText(item.getOverview());
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShowItems data);
    }
}
