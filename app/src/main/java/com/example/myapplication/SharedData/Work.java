package com.example.myapplication.SharedData;

import android.net.Uri;

/**
 * Created by Delma Song on 2019-05-20
 */
public class Work {

    String photo;
    Uri uri;
    String tattooistId;
    String workId;

    public Work() {
    }

    public Work(String photo, String workId) {
        this.photo = photo;
        this.workId = workId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getTattooistId() {
        return tattooistId;
    }

    public void setTattooistId(String tattooistId) {
        this.tattooistId = tattooistId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public Uri changeUri(){
        uri = Uri.parse(photo);
        return uri;
    }

    @Override
    public String toString() {
        return "Work{" +
                "photo='" + photo + '\'' +
                ", uri=" + uri +
                ", tattooistId='" + tattooistId + '\'' +
                ", workId='" + workId + '\'' +
                '}';
    }
}
