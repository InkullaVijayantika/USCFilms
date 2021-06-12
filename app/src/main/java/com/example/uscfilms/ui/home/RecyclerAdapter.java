package com.example.uscfilms.ui.home;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uscfilms.CardActivity;
import com.example.uscfilms.MainActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.Utililties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerAdapterViewHolder>{

    private List<RecyclerData> mRecyclerItems;
    Context mContext;
//    private boolean isAdded=false;

    public RecyclerAdapter(ArrayList<RecyclerData> recyclerDataArrayList, Context context) {
        this.mContext = context;
        this.mRecyclerItems = recyclerDataArrayList;
    }

    @NonNull
    @Override
    public RecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, null);
        return new RecyclerAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterViewHolder viewHolder, int position) {
        final RecyclerData recyclerItem = mRecyclerItems.get(position);
        Glide.with(viewHolder.itemView)
                .load(recyclerItem.getImgUrl())
                .into(viewHolder.cardimg);

        viewHolder.cardimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.cardimg.getContext(), CardActivity.class);
                intent.putExtra("id",recyclerItem.getImgId());
                intent.putExtra("media_type",recyclerItem.getMedia_type());
                intent.putExtra("title",recyclerItem.getTitle());
                intent.putExtra("backdrop_path",recyclerItem.getBackdropUrl());
                intent.putExtra("poster_path", recyclerItem.getImgUrl());
                viewHolder.cardimg.getContext().startActivity(intent);
                Log.d("Card","Card Clicked");
            }
        });





        viewHolder.popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context wrapper = new ContextThemeWrapper(viewHolder.popup.getContext(), R.style.popupMenuStyle);
                PopupMenu popupMenu = new PopupMenu(wrapper, viewHolder.popup);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                Utililties utililties = new Utililties(mContext);
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
                if(utililties.inWatchlist(json)){
                    popupMenu.getMenu().getItem(3).setTitle("Remove from watchlist");
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

//                        Utililties utililties = new Utililties(viewHolder.popup.getContext());
//                        JSONObject json = new JSONObject();
//                        try {
//                            json.put("imgUrl", recyclerItem.getImgUrl());
//                            json.put("imgId", recyclerItem.getImgId());
//                            json.put("title", recyclerItem.getTitle());
//                            json.put("backdrop", recyclerItem.getBackdropUrl());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        if(utililties.inWatchlist(json)){
//                            popupMenu.getMenu().getItem(3).setTitle("Remove from watchlist");
//                        }

                        switch (menuItem.getItemId()) {
                            case R.id.popup_one:
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/"+recyclerItem.getMedia_type()+"/"+recyclerItem.getImgId()));
                                viewHolder.popup.getContext().startActivity(browserIntent);
                                return true;
                            case R.id.popup_two:
                                Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sharer/sharer.php?u="+"https://www.themoviedb.org/"+recyclerItem.getMedia_type()+"/"+recyclerItem.getImgId()));
                                viewHolder.popup.getContext().startActivity(fbIntent);
                                return true;
                            case R.id.popup_three:
                                Intent tweetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?text=Check this out! "+"https://www.themoviedb.org/"+recyclerItem.getMedia_type()+"/"+recyclerItem.getImgId()));
                                viewHolder.popup.getContext().startActivity(tweetIntent);
                                return true;
                            case R.id.popup_four:
                                if(utililties.inWatchlist(json)){
                                    utililties.removeFromWatchlist(json);
                                    Toast.makeText(mContext, recyclerItem.getTitle() + " removed from watchlist", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    utililties.addToWatchlist(json);
                                    Toast.makeText(mContext, recyclerItem.getTitle() + " was added to watchlist.", Toast.LENGTH_SHORT).show();
                                }
                                JSONArray array = utililties.getWatchlist();
                                Log.d("Watchlist", array.toString());
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(viewHolder.card.getContext(),"Details",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {

        return mRecyclerItems.size();
    }

    public void setRecyclerItems(ArrayList<RecyclerData> mRecyclerItems){
        this.mRecyclerItems = mRecyclerItems;
        notifyDataSetChanged();
    }

    public class RecyclerAdapterViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        CardView card;
        ImageView cardimg;
        TextView popup;
        Context context;

        public RecyclerAdapterViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            card = itemView.findViewById(R.id.card);
            cardimg = itemView.findViewById(R.id.cardimg);
            popup = itemView.findViewById(R.id.popup);
            this.itemView = itemView;

        }
    }

}
