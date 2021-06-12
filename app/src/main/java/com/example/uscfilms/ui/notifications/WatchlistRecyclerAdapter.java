package com.example.uscfilms.ui.notifications;

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
import com.example.uscfilms.Utililties;
import com.example.uscfilms.ui.home.RecyclerAdapter;
import com.example.uscfilms.ui.home.RecyclerData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WatchlistRecyclerAdapter extends RecyclerView.Adapter<WatchlistRecyclerAdapter.WatchlistRecyclerAdapterViewHolder>{

    private List<RecyclerData> mRecyclerItems;
    private Context context;
    RecyclerData recyclerData;
    TextView alert;

    public WatchlistRecyclerAdapter(List<RecyclerData> mRecyclerItems, Context context, TextView view) {
        this.mRecyclerItems = mRecyclerItems;
        this.context = context;
        this.alert = view;
    }

//    public WatchlistRecyclerAdapter (Context context) {
//        this.context = context;
//    }

    @NonNull
    @Override
    public WatchlistRecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.watchlist_recycler_layout, null);
        return new WatchlistRecyclerAdapterViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WatchlistRecyclerAdapterViewHolder holder, int position) {
        final RecyclerData recyclerItem = mRecyclerItems.get(position);
        Glide.with(holder.itemView)
                .load(recyclerItem.getImgUrl())
                .into(holder.cardimg);

        holder.cardimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.cardimg.getContext(), CardActivity.class);
                intent.putExtra("id",recyclerItem.getImgId());
                intent.putExtra("media_type",recyclerItem.getMedia_type());
                intent.putExtra("title",recyclerItem.getTitle());
                intent.putExtra("backdrop_path",recyclerItem.getBackdropUrl());
                intent.putExtra("poster_path", recyclerItem.getImgUrl());
//                intent.putExtra("view", R.layout.watchlist_recycler_layout);
                holder.cardimg.getContext().startActivity(intent);
                Log.d("Card",recyclerItem.getMedia_type());
                Log.d("Card",recyclerItem.getImgId().toString());
                Log.d("Card", recyclerItem.getBackdropUrl());
            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject json = new JSONObject();
                try {
                    json.put("poster_path", recyclerItem.getImgUrl());
                    json.put("id", recyclerItem.getImgId());
                    json.put("title", recyclerItem.getTitle());
                    json.put("backdrop_path", recyclerItem.getBackdropUrl());
                    json.put("media_type", recyclerItem.getMedia_type());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utililties utililties = new Utililties(holder.btn.getContext());
                utililties.removeFromWatchlist(json);
                for(int i=0; i < getItemCount(); i++ ){
                    if(mRecyclerItems.get(i).getTitle() == recyclerItem.getTitle()){
                        mRecyclerItems.remove(i);
                        break;
                    }
                }
                notifyDataSetChanged();
                if(utililties.getWatchlist().length() == 0){
                    alert.setVisibility(View.VISIBLE);
                }

            }
        });

        holder.mediaType.setText(recyclerItem.getMedia_type().substring(0,1).toUpperCase()+recyclerItem.getMedia_type().substring(1).toLowerCase());
    }

    @Override
    public int getItemCount() {
        return mRecyclerItems.size();
    }

    public void setWatchlistRecyclerItems(ArrayList<RecyclerData> mRecyclerItems) {
        this.mRecyclerItems = mRecyclerItems;
        notifyDataSetChanged();
    }


    public static class WatchlistRecyclerAdapterViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        CardView card;
        ImageView cardimg;
        TextView mediaType;
        ImageView btn;


        public WatchlistRecyclerAdapterViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            cardimg = itemView.findViewById(R.id.cardimg);
            mediaType = itemView.findViewById(R.id.mediaType);
            btn = itemView.findViewById(R.id.btnRemove);

            this.itemView = itemView;

        }
    }
}
