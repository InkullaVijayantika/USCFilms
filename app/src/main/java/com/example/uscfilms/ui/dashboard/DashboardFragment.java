package com.example.uscfilms.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.RequestManager;
import com.example.uscfilms.R;
import com.example.uscfilms.RecyclerDecorator;
import com.example.uscfilms.ui.home.RecyclerAdapter;
import com.example.uscfilms.ui.home.RecyclerData;
import com.example.uscfilms.ui.home.SliderData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    ArrayList<SearchData> recyclerDataArrayList = new ArrayList<>();
    RecyclerView search_recycler;
    SearchView searchView;
    TextView noSearchResults;
    SearchRecyclerAdapter reAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        noSearchResults = root.findViewById(R.id.text_dashboard);
        search_recycler = root.findViewById(R.id.search_recycler);
        search_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        search_recycler.addItemDecoration(new RecyclerDecorator(20,20,20,20));
        searchView = root.findViewById(R.id.search);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
//                RequestManager.getInstance().doRequest().searchData(getActivity(), DashboardFragment.this, s);
                if(s.equals("")){
                    noSearchResults.setVisibility(View.VISIBLE);
                    search_recycler.setVisibility(View.GONE);
                }
                else{
                    noSearchResults.setVisibility(View.GONE);
                    search_recycler.setVisibility(View.VISIBLE);
                }
                RequestQueue q = Volley.newRequestQueue(requireContext());
                String URL ="https://hw8server-311321.wl.r.appspot.com/autocomplete/"+s;
                JsonArrayRequest jsonArrayReq = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() == 0){
                            noSearchResults.setVisibility(View.VISIBLE);
                            search_recycler.setVisibility(View.GONE);
                        }
                        else{
                            noSearchResults.setVisibility(View.GONE);
                            search_recycler.setVisibility(View.VISIBLE);
                            recyclerDataArrayList = new ArrayList<>();
                            for(int i=0; i<10; i++){
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = (JSONObject) response.get(i);
                                    recyclerDataArrayList.add(new SearchData(jsonObject));
                                    Log.d("Search", jsonObject.getString("title"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            reAdapter = new SearchRecyclerAdapter(recyclerDataArrayList, getContext());
                            Log.d("Something", "onResponse: "+recyclerDataArrayList.toString());
                            search_recycler.setAdapter(reAdapter);
                            reAdapter.notifyDataSetChanged();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", error.toString());
                    }
                });
                q.add(jsonArrayReq);
                return false;
            }
        });


        

//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}