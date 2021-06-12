package com.example.uscfilms.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uscfilms.MainActivity;
import com.example.uscfilms.R;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

//    ArrayList<SliderData> sliderDataArrayListMov = new ArrayList<>();
//    ArrayList<SliderData> sliderDataArrayListTV = new ArrayList<>();
//    ArrayList<RecyclerData> recyclerDataArrayListMov = new ArrayList<>();
//    ArrayList<RecyclerData> recyclerDataArrayListTV = new ArrayList<>();
//    ArrayList<RecyclerData> recyclerListMov = new ArrayList<>();
//    ArrayList<RecyclerData> recyclerListTV = new ArrayList<>();

    SliderAdapter sliderAdapter ;
    RecyclerAdapter reAdapter;
    RecyclerAdapter reAdapterDup ;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayList<SliderData> sliderDataArrayListMov = new ArrayList<>();
        ArrayList<SliderData> sliderDataArrayListTV = new ArrayList<>();
        ArrayList<RecyclerData> recyclerDataArrayListMov = new ArrayList<>();
        ArrayList<RecyclerData> recyclerDataArrayListTV = new ArrayList<>();
        ArrayList<RecyclerData> recyclerListMov = new ArrayList<>();
        ArrayList<RecyclerData> recyclerListTV = new ArrayList<>();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar mToolbar = root.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
//        ((Toolbar) root.findViewById(R.id.toolbar)).setLogo(R.drawable.ic_theaters_white_18dp);

        SliderView sliderView = root.findViewById(R.id.slider);
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        RecyclerView recycler = root.findViewById(R.id.recyclerdup);
        TextView footer = root.findViewById(R.id.footer);

//        String url ="https://vikrama-backend.wl.r.appspot.com/currentmovies";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String url = "https://hw8server-311321.wl.r.appspot.com/currentmovies";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                for(int i=0; i<5; i++){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = (JSONObject) response.get(i);
                        sliderDataArrayListMov.add(new SliderData(jsonObject));
                        Log.d("Curr", jsonObject.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                sliderAdapter = new SliderAdapter(sliderDataArrayListMov);
                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                sliderView.setSliderAdapter(sliderAdapter);
                sliderView.setScrollTimeInSec(3);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        queue.add(jsonArrayRequest);

        RequestQueue q = Volley.newRequestQueue(requireContext());
        String URL ="https://hw8server-311321.wl.r.appspot.com/topmovies";
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<10; i++){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = (JSONObject) response.get(i);
                        recyclerDataArrayListMov.add(new RecyclerData(jsonObject));
                        Log.d("Top mov", jsonObject.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                reAdapter = new RecyclerAdapter(recyclerDataArrayListMov, getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(reAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        q.add(jsonArrayReq);

        RequestQueue Q = Volley.newRequestQueue(requireContext());
        String Url ="https://hw8server-311321.wl.r.appspot.com/popularmovies";
        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<10; i++){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = (JSONObject) response.get(i);
                        recyclerListMov.add(new RecyclerData(jsonObject));
                        Log.d("Pop mov", jsonObject.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                reAdapterDup = new RecyclerAdapter(recyclerListMov, getContext());
                recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                recycler.setAdapter(reAdapterDup);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        Q.add(jsonReq);

//        sliderAdapter.setSliderItems(sliderDataArrayListMov);

//        reAdapter.setRecyclerItems(recyclerDataArrayListMov);

//        reAdapterDup.setRecyclerItems(recyclerListMov);



        RequestQueue queue2 = Volley.newRequestQueue(requireContext());
        String url2 ="https://hw8server-311321.wl.r.appspot.com/trendtv";
        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                for(int i=0; i<5; i++){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = (JSONObject) response.get(i);
                        sliderDataArrayListTV.add(new SliderData(jsonObject));
                        Log.d("Trend", jsonObject.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        queue2.add(jsonArrayRequest2);


        RequestQueue q2 = Volley.newRequestQueue(requireContext());
        String URL2 ="https://hw8server-311321.wl.r.appspot.com/toptv";
        JsonArrayRequest jsonArrayReq2 = new JsonArrayRequest(Request.Method.GET, URL2, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<10; i++){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = (JSONObject) response.get(i);
                        recyclerDataArrayListTV.add(new RecyclerData(jsonObject));
                        Log.d("Top tv", jsonObject.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                     reAdapterTV = new RecyclerAdapter(this, recyclerDataArrayListTV);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        q2.add(jsonArrayReq2);



        RequestQueue Q2 = Volley.newRequestQueue(requireContext());
        String Url2 ="https://hw8server-311321.wl.r.appspot.com/populartv";
        JsonArrayRequest jsonReq2 = new JsonArrayRequest(Request.Method.GET, Url2, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<10; i++){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = (JSONObject) response.get(i);
                        recyclerListTV.add(new RecyclerData(jsonObject));
                        Log.d("pop tv", jsonObject.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                ConstraintLayout constraintLayout = root.findViewById(R.id.progress_section);
                constraintLayout.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        Q2.add(jsonReq2);



        TextView movies_tab = (TextView)root.findViewById(R.id.movies_tab);
        TextView tv_tab = (TextView)root.findViewById(R.id.tv_tab);

        movies_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movies_tab.setTextColor(Color.WHITE);
                tv_tab.setTextColor(getResources().getColor(R.color.colorSelection));
                Log.d("tab","movie");
//                SliderView sliderView = root.findViewById(R.id.slider);
//                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//                sliderView.setSliderAdapter(sliderAdapterMov);
//                sliderView.setScrollTimeInSec(3);
//                sliderView.setAutoCycle(true);
//                sliderView.startAutoCycle();
                sliderAdapter.setSliderItems(sliderDataArrayListMov);


//                RecyclerView recyclerView = root.findViewById(R.id.recycler);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//                recyclerView.setAdapter(reAdapterMov);
                reAdapter.setRecyclerItems(recyclerDataArrayListMov);


//                RecyclerView recycler = root.findViewById(R.id.recyclerdup);
//                recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//                recycler.setAdapter(reAdapterDupMov);
                reAdapterDup.setRecyclerItems(recyclerListMov);

            }
        });


        tv_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_tab.setTextColor(Color.WHITE);
                movies_tab.setTextColor(getResources().getColor(R.color.colorSelection));
                Log.d("tab","tv");
//                SliderView sliderView = root.findViewById(R.id.slider);
//                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//                sliderView.setSliderAdapter(sliderAdapterTV);
//                sliderView.setScrollTimeInSec(3);
//                sliderView.setAutoCycle(true);
//                sliderView.startAutoCycle();
                sliderAdapter.setSliderItems(sliderDataArrayListTV);


//                RecyclerView recyclerView = root.findViewById(R.id.recycler);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//                recyclerView.setAdapter(reAdapterTV);
                reAdapter.setRecyclerItems(recyclerDataArrayListTV);


//                RecyclerView recycler = root.findViewById(R.id.recyclerdup);
//                recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//                recycler.setAdapter(reAdapterDupTV);
                reAdapterDup.setRecyclerItems(recyclerListTV);

            }
        });

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/"));
                getContext().startActivity(browserIntent);
            }
        });

        return root;
    }

}


