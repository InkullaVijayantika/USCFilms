package com.example.uscfilms;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ReviewsData {
    private final String author;
//    private final String name;
    private final String id;
    private final String content;
    private final String created;
    private final Integer rating;

    public ReviewsData(JSONObject jsonObject) throws JSONException {
        Integer rating1;
        this.author = jsonObject.getString("author");
//        this.name = jsonObject.getString("name");
        this.id = jsonObject.getString("id");
        this.content = jsonObject.getString("content");
        this.created = jsonObject.getString("created");
        rating1 = jsonObject.getInt("rating");
        if(rating1 == null){
            rating1 = 0;
        }
        this.rating = rating1;
        System.out.println(author);
    }

    public String getAuthor() {
        return author;
    }

    public String getReviewsId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreated() {
        String c = created.split("T")[0];
        return c;
    }

    public Integer getRating() {
        Log.d("Details", rating.toString());
        return rating/2;
    }
//
//    public String getName() {
//        return name;
//    }
}
