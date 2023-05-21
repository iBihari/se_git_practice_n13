package com.shubhamgupta16.simplewallpaper.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.shubhamgupta16.simplewallpaper.R;
import com.shubhamgupta16.simplewallpaper.data_source.DataService;
import com.shubhamgupta16.simplewallpaper.activities.WallpaperActivity;
import com.shubhamgupta16.simplewallpaper.models.WallsPOJO;

import java.util.List;
import java.util.Objects;

public class WallsAdapter extends RecyclerView.Adapter<WallsAdapter.ViewHolder> {

    private final Context context;
    private final List<WallsPOJO> list;
    private final DataService dataService;
    private DataService.QueryType type;

    public WallsAdapter(Context context, DataService dataService, List<WallsPOJO> list, DataService.QueryType type) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.dataService = dataService;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (viewType == 1)
            view = inflater.inflate(R.layout.walls_layout, parent, false);

        else if (viewType == 2)
            view = inflater.inflate(R.layout.ad_layout, parent, false);

        else if (viewType == 3)
            view = inflater.inflate(R.layout.loading_layout_full, parent, false);
        else
            view = inflater.inflate(R.layout.loading_layout, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getViewType() == -1)
            return 0;
        else if (list.get(position).getViewType() == -2)
            return 2;
        else if (list.get(position).getViewType() == -3)
            return 3;
        else return 1;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WallsPOJO pojo = list.get(position);

        if (getItemViewType(position) == 0 || getItemViewType(position) == 3)

            return;
        if (getItemViewType(position) == 2) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);

            return;
        }

        holder.premiumImage.setVisibility(View.GONE);
        holder.heartImage.setVisibility(View.VISIBLE);
        Glide.with(context).load(pojo.getPreviewUrl()).transition(DrawableTransitionOptions.withCrossFade()).into(holder.imageView);
        holder.title.setText(pojo.getName());
        holder.card.setOnClickListener(view -> {
            Intent i = new Intent(context, WallpaperActivity.class);
            i.putExtra("pojo", pojo);
            context.startActivity(i);
        });

        handleHeart(position, pojo.getUrl(), holder.heartImage);
    }

    private void handleHeart(final int position, final String url, final ImageView heartImage) {
        if (dataService.isFavorite(url)) {
            heartImage.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            heartImage.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        heartImage.setOnClickListener(view -> {
            if (dataService.isFavorite(url)) {
                dataService.toggleFavorite(list.get(position), false);
                if (type == DataService.QueryType.FAVORITE) {
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, list.size());
                    if (onRemoveFromFavSection != null)
                        onRemoveFromFavSection.onRemove();
                } else {
                    heartImage.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
            } else {
                dataService.toggleFavorite(list.get(position), true);
                heartImage.setImageResource(R.drawable.ic_baseline_favorite_24);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setType(DataService.QueryType type) {
        this.type = type;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView, heartImage, premiumImage;
        private TextView title;
        private View card;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if (viewType == 1) {
                heartImage = itemView.findViewById(R.id.heartImage);
                premiumImage = itemView.findViewById(R.id.premiumImage);
                imageView = itemView.findViewById(R.id.image);
                title = itemView.findViewById(R.id.title);
                card = itemView.findViewById(R.id.card);
            } else if (viewType == 2) {
            }
        }
    }
    private OnRemoveFromFavSection onRemoveFromFavSection;

    public void setOnRemoveFromFavSection(OnRemoveFromFavSection onRemoveFromFavSection) {
        this.onRemoveFromFavSection = onRemoveFromFavSection;
    }

    public interface OnRemoveFromFavSection {
        void onRemove();
    }
}