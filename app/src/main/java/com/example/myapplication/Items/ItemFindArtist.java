package com.example.myapplication.Items;

/**
 * Created by Delma Song on 2019-05-03
 */
public class ItemFindArtist {

    String item_id;
    String item_contents;

    public ItemFindArtist(String item_id, String item_contents){
        this.item_id = item_id;
        this.item_contents = item_contents;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_title(String item_title) {
        this.item_id = item_id;
    }

    public String getItem_contents() {
        return item_contents;
    }

    public void setItem_contents(String item_contents) {
        this.item_contents = item_contents;
    }
}
