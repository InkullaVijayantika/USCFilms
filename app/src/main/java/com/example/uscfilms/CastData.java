package com.example.uscfilms;

import org.json.JSONException;
import org.json.JSONObject;

public class CastData {

    private final String castImg;
    private final Integer castId;
    private final String name;
    private final String character;

    public CastData(JSONObject jsonObject) throws JSONException {
        this.castImg = jsonObject.getString("profile_path");
        this.castId =jsonObject.getInt("id");
        this.name = jsonObject.getString("name");
        this.character =jsonObject.getString("character");
    }

    public String getCastImg() {
        return castImg;
    }

    public Integer getCastId() {
        return castId;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }
}

