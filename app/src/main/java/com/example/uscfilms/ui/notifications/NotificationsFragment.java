package com.example.uscfilms.ui.notifications;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uscfilms.CastRecyclerAdapter;
import com.example.uscfilms.R;
import com.example.uscfilms.Utililties;
import com.example.uscfilms.ui.home.RecyclerAdapter;
import com.example.uscfilms.ui.home.RecyclerData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    ArrayList<RecyclerData> recyclerDataArrayList = new ArrayList<>();
    WatchlistRecyclerAdapter reAdapter;
    Utililties utililties;
    TextView alert;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        alert = root.findViewById(R.id.text_notifications);


//        getContext().getSharedPreferences("USC",0).edit().clear().apply();

        Utililties utililties = new Utililties(getContext());
        JSONArray jsonArray = utililties.getWatchlist();
        RecyclerView recycler = root.findViewById(R.id.watchlistrecycler);

        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) jsonArray.get(i);
                recyclerDataArrayList.add(new RecyclerData(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        reAdapter = new WatchlistRecyclerAdapter(recyclerDataArrayList, getContext(), alert);
        recycler.setLayoutManager(new GridLayoutManager(recycler.getContext(), 3));
        recycler.setAdapter(reAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recycler);



        if(utililties.getWatchlist().length() == 0){
            alert.setVisibility(View.VISIBLE);
        }
        else{
            alert.setVisibility(View.GONE);

        }


//        final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        utililties = new Utililties(getContext());
        JSONArray array = utililties.getWatchlist();
        ArrayList<RecyclerData> listdata = new ArrayList<>();
        JSONObject obj = null;
        for (int i = 0; i < array.length(); i++) {
            try {
                obj = (JSONObject) array.get(i);
                listdata.add(new RecyclerData(obj));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        reAdapter.setWatchlistRecyclerItems(listdata);
        reAdapter.notifyDataSetChanged();

        if (utililties.getWatchlist().length() == 0) {
            alert.setVisibility(View.VISIBLE);
        } else {
            alert.setVisibility(View.GONE);

        }

    }
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.START|ItemTouchHelper.END,0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAbsoluteAdapterPosition();
                int to = target.getAbsoluteAdapterPosition();

                Log.d("Check","check");
                utililties = new Utililties(getContext());
                JSONArray array = utililties.getWatchlist();
                ArrayList<RecyclerData> listdata = new ArrayList<>();
                JSONObject obj = null;
                for (int i=0;i<array.length();i++){
                    try {
                        obj = (JSONObject) array.get(i);
                        listdata.add(new RecyclerData(obj));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                move(listdata, from, to);
                utililties.writeToWatchlsit(listdata);
                reAdapter.notifyItemMoved(from, to);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

    public void move(ArrayList<RecyclerData> myList, int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            myList.add(toPosition, myList.get(fromPosition));
            myList.remove(fromPosition);
        }else {
            myList.add(toPosition, myList.get(fromPosition));
            myList.remove(fromPosition + 1);
        }
    }

}