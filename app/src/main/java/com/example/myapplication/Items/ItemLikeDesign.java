package com.example.myapplication.Items;

import android.graphics.drawable.Drawable;

import com.example.myapplication.R;

/**
 * Created by Delma Song on 2019-05-04
 */
public class ItemLikeDesign {

    int item_img;
    boolean item_tb1;
    String item_title, item_contents;

    public ItemLikeDesign(int item_img, boolean item_tb1, String item_title, String item_contents) {
        this.item_img = item_img;
        this.item_tb1 = item_tb1;
        this.item_title = item_title;
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

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_contents() {
        return item_contents;
    }

    public void setItem_contents(String item_contents) {
        this.item_contents = item_contents;
    }
}
