package com.example.myapplication.SharedData;

/**
 * Created by Delma Song on 2019-05-12
 */
public class Reply {

    String repleContents;
    String repleWriter;
    String repleId;

    public Reply() {
    }

    public String getRepleContents() {
        return repleContents;
    }

    public void setRepleContents(String repleContents) {
        this.repleContents = repleContents;
    }

    public String getRepleWriter() {
        return repleWriter;
    }

    public void setRepleWriter(String repleWriter) {
        this.repleWriter = repleWriter;
    }

    public String getRepleId() {
        return repleId;
    }

    public void setRepleId(String repleId) {
        this.repleId = repleId;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "repleContents='" + repleContents + '\'' +
                ", repleWriter='" + repleWriter + '\'' +
                ", repleId='" + repleId + '\'' +
                '}';
    }
}
