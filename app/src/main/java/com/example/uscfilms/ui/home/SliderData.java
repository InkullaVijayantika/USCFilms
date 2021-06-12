package com.example.uscfilms.ui.home;

import org.json.JSONException;
import org.json.JSONObject;

public class SliderData {


    private String imgUrl;
    private Integer imgId;
    private String title;
    private String media_type;
    private String backdrop_path;



    public SliderData(JSONObject jsonObject) throws JSONException{
        this.imgUrl = jsonObject.getString("poster_path");
        this.imgId = jsonObject.getInt("id");
        this.media_type = jsonObject.getString("media_type");
        this.title = jsonObject.getString("title");
        this.backdrop_path = jsonObject.getString("backdrop_path");
    }


    public String getImgUrl() {
        return imgUrl;
    }
    public Integer getImgId() {
        return imgId;
    }
    public String getMedia_type() {
        return media_type;
    }
    public String getTitle() {
        return title;
    }
    public String  getBackdropUrl(){return backdrop_path;}

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}



