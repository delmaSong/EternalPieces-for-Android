package com.example.myapplication.SharedData;

import android.net.Uri;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Delma Song on 2019-05-12
 */
public class TattooerBooking {

    String userId;      //타투 받으려는 사용자 아이디
    String date;
    String time;
    String price;
    String bodyPart;
    String size;
    String design;
    String bookerComment;
    String designId;
    String tattooistId;     //타투 도안 주인 타투이스트 아이디
    String address;
    String bookingNumber;
    Uri uri;



    public TattooerBooking() {
    }

    public TattooerBooking(String date, String time, String price, String bodyPart, String size, String bookerComment, String userId, String designId, String tattooistId, String address, String design) {
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.price = price;
        this.bodyPart = bodyPart;
        this.size = size;
        this.design = design;
        this.bookerComment = bookerComment;
        this.designId = designId;
        this.tattooistId = tattooistId;
        this.address = address;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTattooistId() {
        return tattooistId;
    }

    public void setTattooistId(String tattooistId) {
        this.tattooistId = tattooistId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
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

    public String getDesignId() {
        return designId;
    }

    public void setDesignId(String designId) {
        this.designId = designId;
    }

    //date -> String
    public String changeDate(){
        String str_date = date.toString();
        return str_date;
    }

    public Uri chageUri(){
        uri = Uri.parse(getDesign());
        return uri;
    }

    @Override
    public String toString() {
        return "TattooerBooking{" +
                "userId='" + userId + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", price=" + price +
                ", bodyPart='" + bodyPart + '\'' +
                ", size=" + size +
                ", design='" + design + '\'' +
                ", bookerComment='" + bookerComment + '\'' +
                ", designId='" + designId + '\'' +
                ", tattooistId='" + tattooistId + '\'' +
                '}';
    }
}
