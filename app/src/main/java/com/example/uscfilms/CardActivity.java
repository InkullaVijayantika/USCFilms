package com.example.uscfilms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.example.uscfilms.ui.home.RecyclerAdapter;
import com.example.uscfilms.ui.home.RecyclerData;
import com.example.uscfilms.ui.home.SliderAdapter;
import com.example.uscfilms.ui.home.SliderData;
import com.example.uscfilms.ui.notifications.WatchlistRecyclerAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CardActivity extends AppCompatActivity {
    private ReadMoreTextView textViewCollapsed;
    private TextView textViewExpanded;
    private ImageView backdropImg;
    private  TextView textTitle;
    private  TextView textOverview;
    private  TextView btnSeeMore;
    private  TextView textGenres;
    private  TextView genres;
    private  TextView textYear;
    private ImageView btnAdd;
    private  ImageView btnFb;
    private ImageView btnTwitter;
    private boolean isAdded = false;
    private boolean isExpanded=false;

    ArrayList<CastData> castDataArrayList = new ArrayList<>();
    ArrayList<RecyclerData> recyclerDataArrayList = new ArrayList<>();
    ArrayList<ReviewsData> reviewsDataArrayList = new ArrayList<>();
    ArrayList<RecyclerData> watchlistDataArrayList = new ArrayList<>();
    CastRecyclerAdapter reAdapter;
    ReviewsRecyclerAdapter reAdapterDup;
    RecyclerAdapter adapter;
    Utililties utililties;
//    WatchlistRecyclerAdapter watchlistRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_card);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorSplash));

        textViewCollapsed = findViewById(R.id.textViewCollapsed);
//        textViewExpanded = findViewById(R.id.textViewExpanded);
        backdropImg = findViewById(R.id.backdropimg);
        textTitle = findViewById(R.id.textTitle);
        textOverview = findViewById(R.id.textOverview);
        textGenres = findViewById(R.id.textGenres);
        genres = findViewById(R.id.genres);
        textYear = findViewById(R.id.textYears);
        btnAdd = findViewById(R.id.btnAdd);
        btnFb = findViewById(R.id.fb);
        btnTwitter = findViewById(R.id.twitter);
        utililties = new Utililties(this);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);


        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id",0);
        String media_type = intent.getStringExtra("media_type");
        String title = intent.getStringExtra("title");
        String backdrop = intent.getStringExtra("backdrop_path");
        String poster_path = intent.getStringExtra("poster_path");
//        RecyclerView wrecycler = intent.getViewExtra("view");

        JSONObject json = new JSONObject();
        try {
            json.put("poster_path", poster_path);
            json.put("id", id);
            json.put("title", title);
            json.put("backdrop_path", backdrop);
            json.put("media_type", media_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(utililties.inWatchlist(json)){
            btnAdd.setImageResource(R.drawable.remove_button_foreground);
        }
//        JSONArray array = utililties.getWatchlist();
//        for (int i=0;i<array.length();i++){
//            JSONObject obj = null;
//            try {
//                obj = (JSONObject) array.get(i);
//                watchlistDataArrayList.add(new RecyclerData(obj));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        watchlistRecyclerAdapter = new WatchlistRecyclerAdapter(watchlistDataArrayList,this);

        final String[] videoId = new String[1];

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://hw8server-311321.wl.r.appspot.com/watch/"+media_type+"/"+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                    try {
                        String site = response.getString("key");
                        if(site.equals("tzkWB85ULJY")){
                            backdropImg.setVisibility(View.VISIBLE);
                            youTubePlayerView.setVisibility(View.GONE);
                            Glide.with(getBaseContext())
                                    .load(backdrop)
                                    .into(backdropImg);
                            Log.d("Check",backdrop);
                        }else{
                            videoId[0] = response.getString("key");
                            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                                    youTubePlayer.cueVideo(videoId[0], 0);
                                }
                            });
                        }
                        textTitle.setText(title);
                        textOverview.setText("Overview");
                        textViewCollapsed.setText(response.getString("overview"));
//                        textViewExpanded.setText(response.getString("overview"));
                        textGenres.setText("Genres");
                        JSONArray g =  response.getJSONArray("genres");
                        JSONObject obj = null;
                        String genre = "";
                        for(int i = 0; i<g.length();i++){
                            obj = (JSONObject) g.get(i);
                            genre += obj.getString("name")+" .";
                        }
                        genres.setText(genre);
                        String y = response.getString("release_date").split("-")[0];
                        Log.d("Year", y);
                        textYear.setText(y);

                        btnAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(utililties.inWatchlist(json)){
                                    utililties.removeFromWatchlist(json);

//                                    JSONArray array = utililties.getWatchlist();
//                                    ArrayList<RecyclerData> listdata = new ArrayList<>();
//                                    JSONObject obj = null;
//                                    for (int i=0;i<array.length();i++){
//                                        try {
//                                            obj = (JSONObject) array.get(i);
//                                            listdata.add(new RecyclerData(obj));
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//
//                                    watchlistRecyclerAdapter.setWatchlistRecyclerItems(listdata);
                                    btnAdd.setImageResource(R.drawable.add_button_foreground);
                                    Toast.makeText(getApplicationContext(), title + " removed from watchlist.", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    utililties.addToWatchlist(json);
                                    btnAdd.setImageResource(R.drawable.remove_button_foreground);
                                    Toast.makeText(getApplicationContext(), title + " was added to watchlist.", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                        btnFb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sharer/sharer.php?u="+"https://www.themoviedb.org/"+media_type+"/"+id));
                                startActivity(fbIntent);
                            }
                        });

                        btnTwitter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent tweetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?text=Check this out! "+"https://www.themoviedb.org/"+media_type+"/"+id));
                                startActivity(tweetIntent);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                        Log.d("Curr", jsonObject.getString("title"));
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        queue.add(jsonObjectRequest);

        RecyclerView recycler = findViewById(R.id.castrecycler);
        RequestQueue q = Volley.newRequestQueue(this);
        String URL = "https://hw8server-311321.wl.r.appspot.com/cast/"+media_type+"/"+id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                if(response.length() == 0){
                    TextView textView = findViewById(R.id.cast);
                    textView.setVisibility(View.GONE);
                    recycler.setVisibility(View.GONE);
                }
                else{
                    for(int i=0; i< Math.min(6,response.length()); i++){
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = (JSONObject) response.get(i);
                            castDataArrayList.add(new CastData(jsonObject));
                            Log.d("cast",jsonObject.getString("name"));
                            System.out.println(jsonObject.getString("name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    reAdapter = new CastRecyclerAdapter(castDataArrayList);
                    recycler.setLayoutManager(new GridLayoutManager(recycler.getContext(), 3));
                    recycler.setAdapter(reAdapter);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        q.add(jsonArrayRequest);

        RecyclerView recyclerdup = findViewById(R.id.reviewsrecycler);
        RequestQueue Q = Volley.newRequestQueue(this);
        String Url = "https://hw8server-311321.wl.r.appspot.com/reviews/"+media_type+"/"+id;
        Log.d("URL", Url);
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Length", String.valueOf(response.length()));
                if(response.length() == 0){
                    TextView textView = findViewById(R.id.reviews);
                    textView.setVisibility(View.GONE);
                    recyclerdup.setVisibility(View.GONE);
                }
                else{
                    Log.d("Reviews", String.valueOf(response.length()));
                    for(int i=0; i< Math.min(3,response.length()); i++){
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = (JSONObject) response.get(i);
                            reviewsDataArrayList.add(new ReviewsData(jsonObject));
                            Log.d("Reviews",jsonObject.getString("author"));
                            System.out.println(jsonObject.getString("author"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    reAdapterDup = new ReviewsRecyclerAdapter(reviewsDataArrayList);
                    recyclerdup.setLayoutManager(new GridLayoutManager(recyclerdup.getContext(), 1));
                    recyclerdup.setAdapter(reAdapterDup);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        Q.add(jsonRequest);

        RecyclerView recyclerrecom = findViewById(R.id.recomrecycler);
        RequestQueue q1 = Volley.newRequestQueue(this);
        String URL1 ="https://hw8server-311321.wl.r.appspot.com/recommendations/"+media_type+"/"+id;
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(Request.Method.GET, URL1, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                if(response.length() == 0){
                    TextView textView = findViewById(R.id.recommended);
                    textView.setVisibility(View.GONE);
                    recyclerrecom.setVisibility(View.GONE);
                }
                else{
                    for(int i=0; i<Math.min(10,response.length()); i++){
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = (JSONObject) response.get(i);
                            recyclerDataArrayList.add(new RecyclerData(jsonObject));
                            Log.d("Top mov", jsonObject.getString("title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter = new RecyclerAdapter(recyclerDataArrayList, getApplicationContext());
                    recyclerrecom.setLayoutManager(new LinearLayoutManager(recyclerrecom.getContext(), LinearLayoutManager.HORIZONTAL, false));
                    recyclerrecom.setAdapter(adapter);

                    ConstraintLayout constraintLayout = findViewById(R.id.progress_section);
                    constraintLayout.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        q1.add(jsonArrayReq);







//        textView.setText(title);

        initViews();
        setData();
    }

    private void setData(){
//        textView.setText("In Details Page");
    }

    private void initViews(){
//        textView = findViewById(R.id.textView);
//        textView.setText(title);

    }
}