package com.example.myapplication.SharedData;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Delma Song on 2019-05-11
 */
public class Tattooist implements Parcelable {

    String id;              //tattooist's id
    List<String> style;     //주력 스타일
    String address;        //타투이스트 주소
    String profile;         //자기 소개
    String profilePhoto;   //프로필 사진
    String liked;           //좋아요
    String work;            //시술사진
    String bookingId;         //예약 현황 키값 가져오기
    List<String> workDate, workTime;        //시술 가능 요일, 시간
    String backgroundPhoto;     //mypage background photo

    Uri uri;

    public Tattooist() {

    }

    //리사이클러뷰에서 띄워줄 놈들
    public Tattooist(String profilePhoto, String id, String profile){
        this.profilePhoto = profilePhoto;
        this.id = id;
        this.profile = profile;
    }

    //예약 리사이클러뷰에서 띄워줄 시간
    public Tattooist(List<String> workTime) {
        this.workTime = workTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getStyle() {
        return style;
    }

    public void setStyle(List<String> style) {
        this.style = style;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public List<String> getWorkDate() {
        return workDate;
    }

    public void setWorkDate(List<String> workDate) {
        this.workDate = workDate;
    }

    public List<String> getWorkTime() {
        return workTime;
    }

    public void setWorkTime(List<String> workTime) {
        this.workTime = workTime;
    }

    public String getBackgroundPhoto() {
        return backgroundPhoto;
    }

    public void setBackgroundPhoto(String backgroundPhoto) {
        this.backgroundPhoto = backgroundPhoto;
    }

    //string > uri parsing method
    public Uri changeUri(){
        uri = Uri.parse(getProfilePhoto());
        return uri;
    }

    public Uri changeUriForBackground(){

        uri = Uri.parse(getBackgroundPhoto());
        return uri;
    }

    @Override
    public String toString() {
        return "Tattooist{" +
                "id='" + id + '\'' +
                ", style=" + style +
                ", address='" + address + '\'' +
                ", profile='" + profile + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", liked='" + liked + '\'' +
                ", work='" + work + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", workDate=" + workDate +
                ", workTime=" + workTime +
                ", backgroundPhoto='" + backgroundPhoto + '\'' +
                ", uri=" + uri +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Tattooist(Parcel in) {
        id = in.readString();
        style = in.createStringArrayList();
        address = in.readString();
        profile = in.readString();
        profilePhoto = in.readString();
        liked = in.readString();
        work = in.readString();
        bookingId = in.readString();
        workDate = in.createStringArrayList();
        workTime = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeStringList(style);
        dest.writeString(address);
        dest.writeString(profile);
        dest.writeString(profilePhoto);
        dest.writeString(liked);
        dest.writeString(work);
        dest.writeString(bookingId);
        dest.writeStringList(workDate);
        dest.writeStringList(workTime);


    }

    public static final Creator<Tattooist> CREATOR = new Creator<Tattooist>() {
        @Override
        public Tattooist createFromParcel(Parcel in) {
            return new Tattooist(in);
        }

        @Override
        public Tattooist[] newArray(int size) {
            return new Tattooist[size];
        }
    };
}
