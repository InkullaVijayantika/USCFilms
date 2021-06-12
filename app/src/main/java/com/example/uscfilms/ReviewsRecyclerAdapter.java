package com.example.uscfilms;

import android.annotation.SuppressLint;
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
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ReviewsRecyclerAdapter.ReviewsRecyclerAdapterViewHolder>{

    private List<ReviewsData> mReviewsItems;
    public ReviewsRecyclerAdapter(ArrayList<ReviewsData> mReviewsItems) {
        this.mReviewsItems = mReviewsItems;
    }

    @NonNull
    @Override
    public ReviewsRecyclerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_recycler_layout, null);
        return new ReviewsRecyclerAdapter.ReviewsRecyclerAdapterViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReviewsRecyclerAdapterViewHolder holder, int position) {
        final ReviewsData reviewItem = mReviewsItems.get(position);
        holder.heading.setText("by "+ reviewItem.getAuthor()+" on "+reviewItem.getCreated());
        holder.rating.setText(reviewItem.getRating().toString()+"/5 ");
        holder.content.setText(reviewItem.getContent());

        holder.reviewsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.reviewsCard.getContext(), ReviewActivity.class);
                intent.putExtra("id",reviewItem.getReviewsId());
                intent.putExtra("Heading","by "+reviewItem.getAuthor()+" on "+reviewItem.getCreated());
                intent.putExtra("Content",reviewItem.getContent());
                intent.putExtra("Rating",reviewItem.getRating());
                holder.reviewsCard.getContext().startActivity(intent);
                Log.d("Card","Reviews Card Clicked");
            }
        });

    }

    @Override
    public int getItemCount() {
        return mReviewsItems.size();
    }

    public void setReviewsItems(ArrayList<ReviewsData> mRecyclerItems){
        this.mReviewsItems = mRecyclerItems;
        notifyDataSetChanged();
    }

    public static class ReviewsRecyclerAdapterViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView heading;
        TextView rating;
        TextView content;
        TextView star;
        MaterialCardView reviewsCard;

        public ReviewsRecyclerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.heading);
            rating = itemView.findViewById(R.id.rating);
            content = itemView.findViewById(R.id.content);
            reviewsCard = itemView.findViewById(R.id.reviewscard);
//            star = itemView.findViewById(R.id.star);
            this.itemView = itemView;
        }
    }
}
