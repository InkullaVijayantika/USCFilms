package com.example.uscfilms.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uscfilms.CardActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.ui.home.RecyclerAdapter;
import com.example.uscfilms.ui.home.RecyclerData;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchRecyclerAdapterViewHolder> {

    private List<SearchData> mRecyclerItems;
    Context mContext;

    public SearchRecyclerAdapter(List<SearchData> mRecyclerItems, Context mContext) {
        this.mRecyclerItems = mRecyclerItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SearchRecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, null);
        return new SearchRecyclerAdapterViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapterViewHolder holder, int position) {
        final SearchData recyclerItem = mRecyclerItems.get(position);
        Glide.with(holder.itemView)
                .asBitmap()
                .load(recyclerItem.getBackdropUrl())
                .placeholder(R.drawable.placeholder_movie)
                .fitCenter()
                .into(holder.cardimg);

        holder.media_type.setText(recyclerItem.getMedia_type()+" ("+recyclerItem.getYear()+")");
        holder.rating.setText(recyclerItem.getRating().toString());
        holder.title.setText(recyclerItem.getTitle());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.cardimg.getContext(), CardActivity.class);
                intent.putExtra("id",recyclerItem.getImgId());
                intent.putExtra("media_type",recyclerItem.getMedia_type());
                intent.putExtra("title",recyclerItem.getTitle());
                intent.putExtra("backdrop_path",recyclerItem.getBackdropUrl());
                intent.putExtra("poster_path", recyclerItem.getImgUrl());
                holder.cardimg.getContext().startActivity(intent);
                Log.d("Card","Card Clicked");
            }
        });


    }

    @Override
    public int getItemCount() {
        return mRecyclerItems.size();
    }

    public static class SearchRecyclerAdapterViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        CardView card;
        ImageView cardimg;
        TextView media_type;
        TextView rating;
        TextView title;
        Context context;

        public SearchRecyclerAdapterViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            card = itemView.findViewById(R.id.search_card);
            cardimg = itemView.findViewById(R.id.cardimg_search);
            media_type = itemView.findViewById(R.id.mediaType_search);
            rating = itemView.findViewById(R.id.rating_search);
            title = itemView.findViewById(R.id.title_search);
            this.itemView = itemView;

        }
    }
}
