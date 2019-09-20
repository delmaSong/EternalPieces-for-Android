package com.example.myapplication.Items;

/**
 * Created by Delma Song on 2019-05-04
 */
public class ItemChatListTattooist {

    int item_img;
    String item_time, item_count, item_userid, item_msg;

    public ItemChatListTattooist(int item_img, String item_time, String item_count, String item_userid, String item_msg) {
        this.item_img = item_img;
        this.item_time = item_time;
        this.item_count = item_count;
        this.item_userid = item_userid;
        this.item_msg = item_msg;
    }

    public int getItem_img() {
        return item_img;
    }

    public void setItem_img(int item_img) {
        this.item_img = item_img;
    }

    public String getItem_time() {
        return item_time;
    }

    public void setItem_time(String item_time) {
        this.item_time = item_time;
    }

    public String getItem_count() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public String getItem_userid() {
        return item_userid;
    }

    public void setItem_userid(String item_userid) {
        this.item_userid = item_userid;
    }

    public String getItem_msg() {
        return item_msg;
    }

    public void setItem_msg(String item_msg) {
        this.item_msg = item_msg;
    }
}
