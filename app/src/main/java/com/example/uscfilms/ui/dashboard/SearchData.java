package com.example.uscfilms.ui.dashboard;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchData {

    private String imgUrl;
    private Integer imgId;
    private String title;
    private String media_type;
    private String backdropUrl;
    private Double rating;
    private String year;

    public SearchData(JSONObject jsonObject) throws JSONException {
        this.imgUrl = jsonObject.getString("poster_path");
        this.imgId = jsonObject.getInt("id");
        this.media_type = jsonObject.getString("media_type");
        this.title = jsonObject.getString("title");
        this.backdropUrl = jsonObject.getString("backdrop_path");
        if(jsonObject.has("rating")){
            this.rating = jsonObject.getDouble("rating");

        }
        else{
            this.rating = 0.0;
        }

        if(jsonObject.has("year")){
            this.year = jsonObject.getString("year").split("-")[0];

        }
        else{
            this.year = " ";
        }
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Integer getImgId() {
        return imgId;
    }

    public String getTitle() {
        return title;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public Double getRating() {
        return rating/2;
    }

    public String getYear() {
        return year;
    }
}
