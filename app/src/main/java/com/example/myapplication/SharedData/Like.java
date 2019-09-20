package com.example.myapplication.SharedData;

import java.util.List;

/**
 * Created by Delma Song on 2019-05-21
 */
public class Like {

    String userId;
    List<String> artist;
    List<String> design;

    public Like() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getArtist() {
        return artist;
    }

    public void setArtist(List<String> artist) {
        this.artist = artist;
    }

    public List<String> getDesign() {
        return design;
    }

    public void setDesign(List<String> design) {
        this.design = design;
    }

    @Override
    public String toString() {
        return "Like{" +
                "userId='" + userId + '\'' +
                ", artist='" + artist + '\'' +
                ", design='" + design + '\'' +
                '}';
    }
}
