package com.example.myapplication.Items;

/**
 * Created by Delma Song on 2019-05-04
 */
public class ItemTattooistMPReview {

    String tt_title, tt_contents;
    int item_img1, item_img2, item_img3;
    float rating_bar;
    String tt_user_id, tt_write_date;

    public ItemTattooistMPReview(String tt_title, String tt_contents, int item_img1, int item_img2, int item_img3, float rating_bar, String tt_user_id, String tt_write_date) {
        this.tt_title = tt_title;
        this.tt_contents = tt_contents;
        this.item_img1 = item_img1;
        this.item_img2 = item_img2;
        this.item_img3 = item_img3;
        this.rating_bar = rating_bar;
        this.tt_user_id = tt_user_id;
        this.tt_write_date = tt_write_date;
    }

    public String getTt_title() {
        return tt_title;
    }

    public void setTt_title(String tt_title) {
        this.tt_title = tt_title;
    }

    public String getTt_contents() {
        return tt_contents;
    }

    public void setTt_contents(String tt_contents) {
        this.tt_contents = tt_contents;
    }

    public int getItem_img1() {
        return item_img1;
    }

    public void setItem_img1(int item_img1) {
        this.item_img1 = item_img1;
    }

    public int getItem_img2() {
        return item_img2;
    }

    public void setItem_img2(int item_img2) {
        this.item_img2 = item_img2;
    }

    public int getItem_img3() {
        return item_img3;
    }

    public void setItem_img3(int item_img3) {
        this.item_img3 = item_img3;
    }

    public float getRating_bar() {
        return rating_bar;
    }

    public void setRating_bar(float rating_bar) {
        this.rating_bar = rating_bar;
    }

    public String getTt_user_id() {
        return tt_user_id;
    }

    public void setTt_user_id(String tt_user_id) {
        this.tt_user_id = tt_user_id;
    }

    public String getTt_write_date() {
        return tt_write_date;
    }

    public void setTt_write_date(String tt_write_date) {
        this.tt_write_date = tt_write_date;
    }
}
