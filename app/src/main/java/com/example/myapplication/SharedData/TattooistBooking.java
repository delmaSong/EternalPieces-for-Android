package com.example.myapplication.SharedData;

import android.net.Uri;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Delma Song on 2019-05-12
 */
public class TattooistBooking {

    String date;      //날짜
    String time;      //시간
    String price;
    String bodyPart;
    String size;
    String design;              //도안 사진정보
    String bookerComment;       //예약자 당부사항,코멘트
    String bookerId;            //예약자 id
    String bookingId;           //예약 id.. 타투이스트 아이디 + 번호
    String designId;        //도안 아이디값
    String tattooistId;
    String address;
    Uri uri;

    public TattooistBooking() {
    }

    //recyclerView에서 뿌려줄 때 쓸 생성자


    public TattooistBooking(String date, String time, String price, String bodyPart, String size, String bookerComment, String bookerId, String designId, String tattooistId, String address, String design) {
        this.date = date;
        this.time = time;
        this.price = price;
        this.bodyPart = bodyPart;
        this.size = size;
        this.bookerComment = bookerComment;
        this.bookerId = bookerId;
        this.designId = designId;
        this.tattooistId = tattooistId;
        this.address = address;
        this.design = design;
    }

    public String getTattooistId() {
        return tattooistId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTattooistId(String tattooistId) {
        this.tattooistId = tattooistId;
    }

    public String getDesignId() {
        return designId;
    }

    public void setDesignId(String designId) {
        this.designId = designId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
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

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getBookerComment() {
        return bookerComment;
    }

    public void setBookerComment(String bookerComment) {
        this.bookerComment = bookerComment;
    }

    public String getBookerId() {
        return bookerId;
    }

    public void setBookerId(String bookerId) {
        this.bookerId = bookerId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }


    public Uri chageUri(){
        uri = Uri.parse(getDesign());
        return uri;
    }
    @Override
    public String toString() {
        return "TattooistBooking{" +
                "date=" + date +
                ", time=" + time +
                ", price=" + price +
                ", bodyPart='" + bodyPart + '\'' +
                ", size=" + size +
                ", design='" + design + '\'' +
                ", bookerComment='" + bookerComment + '\'' +
                ", bookerId='" + bookerId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                '}';
    }
}


