package com.example.myapplication.Items;

import android.graphics.drawable.Drawable;

/**
 * Created by Delma Song on 2019-05-04
 */
public class ItemLikeArtist {

    int item_img;
    boolean item_tb1;
    String item_artist, item_contents;

    public ItemLikeArtist(int item_img, boolean item_tb1, String item_artist, String item_contents) {
        this.item_img = item_img;
        this.item_tb1 = item_tb1;
        this.item_artist = item_artist;
        this.item_contents = item_contents;
    }

    public int getItem_img() {
        return item_img;
    }

    public void setItem_img(int item_img) {
        this.item_img = item_img;
    }

    public boolean isItem_tb1() {
        return item_tb1;
    }

    public void setItem_tb1(boolean item_tb1) {
        this.item_tb1 = item_tb1;
    }

    public String getItem_artist() {
        return item_artist;
    }

    public void setItem_artist(String item_artist) {
        this.item_artist = item_artist;
    }

    public String getItem_contents() {
        return item_contents;
    }

    public void setItem_contents(String item_contents) {
        this.item_contents = item_contents;
    }
}
