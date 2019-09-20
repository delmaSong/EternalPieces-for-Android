package com.example.myapplication.SharedData;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Delma Song on 2019-05-11
 */
public class User implements Parcelable {

    String id, pw;
    boolean ton = false;        //tattooist Or Not. true is tattooist , false is tattooer;
    List<String> likeDesign;
    List<String> likeTattooist;


    public User() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public boolean isTon() {
        return ton;
    }

    public void setTon(boolean ton) {
        this.ton = ton;
    }

    public List<String> getLikeDesign() {
        return likeDesign;
    }

    public void setLikeDesign(List<String> likeDesign) {
        this.likeDesign = likeDesign;
    }

    public List<String> getLikeTattooist() {
        return likeTattooist;
    }

    public void setLikeTattooist(List<String> likeTattooist) {
        this.likeTattooist = likeTattooist;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", ton=" + ton +
                ", likeDesign=" + likeDesign +
                ", likeTattooist=" + likeTattooist +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(pw);

    }

    protected User(Parcel in) {
        id = in.readString();
        pw = in.readString();
        ton = in.readByte() != 0;

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
