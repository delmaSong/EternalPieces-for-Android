package com.example.myapplication.Items;

/**
 * Created by Delma Song on 2019-05-04
 */
public class ItemTattooistBookingList {

    String item_month, item_date, item_time, item_subs, item_detail;

    public ItemTattooistBookingList(String item_month, String item_date, String item_time, String item_subs, String item_detail) {
        this.item_month = item_month;
        this.item_date = item_date;
        this.item_time = item_time;
        this.item_subs = item_subs;
        this.item_detail = item_detail;
    }

    public String getItem_month() {
        return item_month;
    }

    public void setItem_month(String item_month) {
        this.item_month = item_month;
    }

    public String getItem_date() {
        return item_date;
    }

    public void setItem_date(String item_date) {
        this.item_date = item_date;
    }

    public String getItem_time() {
        return item_time;
    }

    public void setItem_time(String item_time) {
        this.item_time = item_time;
    }

    public String getItem_subs() {
        return item_subs;
    }

    public void setItem_subs(String item_subs) {
        this.item_subs = item_subs;
    }

    public String getItem_detail() {
        return item_detail;
    }

    public void setItem_detail(String item_detail) {
        this.item_detail = item_detail;
    }
}
