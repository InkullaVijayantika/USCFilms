package com.example.uscfilms.ui.home;//package com.example.uscfilms.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.uscfilms.CardActivity;
import com.example.uscfilms.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    private List<SliderData> mSliderItems;
    Context mContext;

//    public SliderAdapter(View.OnClickListener context, ArrayList<SliderData> sliderDataArrayList) {
//        this.mSliderItems = sliderDataArrayList;
//    }
//    public SliderAdapter(Context context, ArrayList<SliderData> sliderDataArrayList) {
//        this.mSliderItems = sliderDataArrayList;
//        this.mContext = context;
//
//    }
public SliderAdapter(ArrayList<SliderData> sliderDataArrayList) {
    this.mSliderItems = sliderDataArrayList;

}

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {
        final SliderData sliderItem = mSliderItems.get(position);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .into(viewHolder.imageViewBackground);

        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgUrl())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(viewHolder.imageViewForeground);

        viewHolder.imageViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.imageViewBackground.getContext(), CardActivity.class);
                intent.putExtra("id",sliderItem.getImgId());
                intent.putExtra("media_type",sliderItem.getMedia_type());
                intent.putExtra("title",sliderItem.getTitle());
                intent.putExtra("backdrop_path",sliderItem.getBackdropUrl());
                intent.putExtra("poster_path", sliderItem.getImgUrl());
                viewHolder.imageViewBackground.getContext().startActivity(intent);
                Log.d("Card","Card Clicked");
            }
        });
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    public void setSliderItems(ArrayList<SliderData> mSliderItems){
        this.mSliderItems = mSliderItems;
        notifyDataSetChanged();
    }

    public static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;
        ImageView imageViewForeground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myBackImage);
            imageViewForeground = itemView.findViewById(R.id.myimage);
            this.itemView = itemView;
        }
    }
}



