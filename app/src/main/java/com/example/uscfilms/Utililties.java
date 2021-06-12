package com.example.uscfilms;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.uscfilms.ui.home.RecyclerData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Utililties {

    String key;
    String pref;
    Context context;
    SharedPreferences sharedPreferences;


    public Utililties(Context context) {
        this.context = context;
        this.key="Watchlist";
        this.pref="USC";
        sharedPreferences = context.getSharedPreferences(pref,Context.MODE_PRIVATE);
    }

    public void addToWatchlist(JSONObject jsonObject) {
        try{
            String watchlist = sharedPreferences.getString(key, null);
            JSONArray jsonArray;
            if(watchlist == null){
                jsonArray = new JSONArray("[]");
            }
            else{
                jsonArray = new JSONArray(watchlist);
            }
            jsonArray.put(jsonObject);
            String s = jsonArray.toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key,s);
            editor.apply();
            Log.d("Added",jsonObject.getString("title"));
        }catch(JSONException e){
            e.printStackTrace();
        }


    }

    public JSONArray getWatchlist()  {
        String watchlist = sharedPreferences.getString(key, null);
        if(watchlist == null) {
            return new JSONArray();
        }
        JSONArray jsonArray = null;
        try{
            jsonArray = new JSONArray(watchlist);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void removeFromWatchlist(JSONObject jsonObject) {
//        ArrayList<String> list = new ArrayList<String>();
        try{
            String watchlist = sharedPreferences.getString(key, null);
            JSONArray jsonArray = new JSONArray(watchlist);
            int len = jsonArray.length();
            JSONObject object=null;
            for (int i=0;i<len;i++){
                object = (JSONObject) jsonArray.get(i);
                if(object.getString("title").equals(jsonObject.getString("title"))){
                    jsonArray.remove(i);
                    break;
                }
            }
            String s = jsonArray.toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key,s);
            editor.apply();
            Log.d("Removed",jsonObject.getString("title"));
            Toast.makeText(context, jsonObject.getString("title") + " removed from watchlist", Toast.LENGTH_SHORT).show();

        }catch(JSONException e){
            e.printStackTrace();
        }

    }

    public boolean inWatchlist(JSONObject jsonObject) {
//        ArrayList<String> list = new ArrayList<String>();
        try{
            String watchlist = sharedPreferences.getString(key, null);
            if(watchlist == null) {
                return false;
            }
            JSONArray jsonArray = new JSONArray(watchlist);
            int len = jsonArray.length();
            JSONObject object=null;
            for (int i=0;i<len;i++){
                object = (JSONObject) jsonArray.get(i);
                if(object.getString("title").equals(jsonObject.getString("title"))){
                    return true;
                }
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
        return false;
    }

    public void writeToWatchlsit(ArrayList<RecyclerData> listdata){
        try{
            JSONArray jsonArray = new JSONArray();

            for(int i = 0; i< listdata.size(); i++){
                JSONObject obj = new JSONObject();
                obj.put("id",listdata.get(i).getImgId());
                obj.put("poster_path",listdata.get(i).getImgUrl());
                obj.put("title",listdata.get(i).getTitle());
                obj.put("media_type",listdata.get(i).getMedia_type());
                obj.put("backdrop_path",listdata.get(i).getBackdropUrl());
                jsonArray.put(obj);

            }
            String s = jsonArray.toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key,s);
            editor.apply();
        }catch (JSONException e){
            e.printStackTrace();
        }


    }

}
