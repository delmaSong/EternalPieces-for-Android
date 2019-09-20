package com.example.myapplication.Items;

/**
 * Created by Delma Song on 2019-05-02
 */
public class ItemFindStyle {

    private int item_img;
    private String item_title;
    private String item_contents;

    public ItemFindStyle(int item_img, String item_title, String item_contents) {
        this.item_img = item_img;
        this.item_title = item_title;
        this.item_contents = item_contents;
    }

    public int getItem_img() {
        return item_img;
    }

    public void setItem_img(int item_img) {
        this.item_img = item_img;
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
