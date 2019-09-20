package com.example.myapplication.SharedData;

import android.net.Uri;

/**
 * Created by Delma Song on 2019-05-12
 */
public class Design {

    String name;
    String photo;           //uri로 되어있는 놈 String으로 변환해 저장한 값이다.. 아래에서 도로 uri로 변환 해주는 메소드 있음
    String price;
    String size;
    String spendTime;
    String style;
    boolean liked;
    String tattooist;
    String designId;
    String comment;
    Uri uri;
    boolean soldOut;


    public Design() {
    }



    //find Style 리사이클러뷰에서 쓸 생성자
    public Design(String name, String photo, String comment, String designId) {
        this.name = name;
        this.photo = photo;
        this.comment = comment;
        this.designId = designId;
    }

    //타투이스트 마이페이지 - 도안 에서 리사이클러뷰로 쓸 생성자
    public Design(String photo, String designId){
        this.photo = photo;
        this.designId = designId;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(String spendTime) {
        this.spendTime = spendTime;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getTattooist() {
        return tattooist;
    }

    public void setTattooist(String tattooist) {
        this.tattooist = tattooist;
    }

    public String getDesignId() {
        return designId;
    }

    public void setDesignId(String designId) {
        this.designId = designId;
    }

    @Override
    public String toString() {
        return "Design{" +
                "name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", price='" + price + '\'' +
                ", size='" + size + '\'' +
                ", spendTime='" + spendTime + '\'' +
                ", style='" + style + '\'' +
                ", liked=" + liked +
                ", tattooist='" + tattooist + '\'' +
                ", designId='" + designId + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }



    //string > uri parsing method
    public Uri changeUri(){
        uri = Uri.parse(getPhoto());
//        uri = Uri.parse(photo);
        return uri;
    }
}

