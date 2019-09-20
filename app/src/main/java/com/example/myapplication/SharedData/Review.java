package com.example.myapplication.SharedData;

import android.net.Uri;

import java.util.Date;

/**
 * Created by Delma Song on 2019-05-12
 */
public class Review {

    String writer;      //작성자(user id)
    String title;
    String contents;    //후기 내용
    String date;          //작성 날짜
    String photo;       //후기 사진
    float star;         //별점
    String tattooistId;     //시술해준 타투이스트 아이디
    Uri uri;

    public Review() {
    }

    //리사이클러뷰에서 쓸 생성자..
    public Review(String writer, String title, String contents, String date, String photo, float star, String tattooistId) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.date = date;
        this.photo = photo;
        this.star = star;
        this.tattooistId = tattooistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTattooistId() {
        return tattooistId;
    }

    public void setTattooistId(String tattooistId) {
        this.tattooistId = tattooistId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

     public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public Uri chageUri(){
        uri = Uri.parse(photo);
        return uri;
    }

    @Override
    public String toString() {
        return "Review{" +
                "writer='" + writer + '\'' +
                ", contents='" + contents + '\'' +
                ", date=" + date +
                ", photo='" + photo + '\'' +
                ", star=" + star +
                '}';
    }
}


