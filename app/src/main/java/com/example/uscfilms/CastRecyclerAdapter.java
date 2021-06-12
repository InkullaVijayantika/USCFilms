package com.example.uscfilms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uscfilms.ui.home.RecyclerAdapter;
import com.example.uscfilms.ui.home.RecyclerData;

import java.util.ArrayList;
import java.util.List;

public class CastRecyclerAdapter extends RecyclerView.Adapter<CastRecyclerAdapter.CastRecyclerAdapterViewHolder>{
    private List<CastData> mCastItems;

    public CastRecyclerAdapter(ArrayList<CastData> mCastItems) {
        this.mCastItems = mCastItems;
    }

    @NonNull
    @Override
    public CastRecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_recycler_layout, null);
        return new CastRecyclerAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CastRecyclerAdapterViewHolder holder, int position) {
        final CastData castItem = mCastItems.get(position);
        Glide.with(holder.itemView)
                .load(castItem.getCastImg())
                .into(holder.castimg);
        holder.text.setText(castItem.getName());
    }

    @Override
    public int getItemCount() {
        return mCastItems.size();
    }

    public void setCastItems(ArrayList<CastData> mRecyclerItems){
        this.mCastItems = mRecyclerItems;
        notifyDataSetChanged();
    }


    public static class CastRecyclerAdapterViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        CardView castcard;
        ImageView castimg;
        TextView text;

        public CastRecyclerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            castcard = itemView.findViewById(R.id.castcard);
            castimg = itemView.findViewById(R.id.castimg);
            text = itemView.findViewById(R.id.castname);
            this.itemView = itemView;

        }
    }

}
