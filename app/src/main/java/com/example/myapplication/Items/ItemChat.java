package com.example.myapplication.Items;

import java.util.Date;

/**
 * Created by Delma Song on 2019-05-06
 */
public class ItemChat {

    String item_msg;
    int flag;
    String item_now_time;

    public ItemChat(String item_msg, int flag, String item_now_time) {
        this.item_msg = item_msg;
        this.flag = flag;
        this.item_now_time = item_now_time;
    }

    public String getItem_my_msg() {
        return item_msg;
    }

    public void setItem_my_msg(String item_my_msg) {
        this.item_msg = item_my_msg;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getItem_now_time() {
        return item_now_time;
    }

    public void setItem_now_time(String item_now_time) {
        this.item_now_time = item_now_time;
    }
}
