package com.example.myapplication.SharedData;

import java.util.List;

/**
 * Created by Delma Song on 2019-05-30
 */
public class LikeDesign {

    List<String> waterColorId;
    List<String> blackGrayId;
    List<String> coverUpId;
    List<String> crayonId;
    List<String> letteringId;
    List<String> designId;

    public List<String> getDesignId() {

        //designId 는 각각의 모든 리스트 합친놈

        return designId;
    }

    public void setDesignId(List<String> designId) {
        this.designId = designId;
    }

    public List<String> getWaterColorId() {
        return waterColorId;
    }

    public void setWaterColorId(List<String> waterColorId) {
        this.waterColorId = waterColorId;
    }

    public List<String> getBlackGrayId() {
        return blackGrayId;
    }

    public void setBlackGrayId(List<String> blackGrayId) {
        this.blackGrayId = blackGrayId;
    }

    public List<String> getCoverUpId() {
        return coverUpId;
    }

    public void setCoverUpId(List<String> coverUpId) {
        this.coverUpId = coverUpId;
    }

    public List<String> getCrayonId() {
        return crayonId;
    }

    public void setCrayonId(List<String> crayonId) {
        this.crayonId = crayonId;
    }

    public List<String> getLetteringId() {
        return letteringId;
    }

    public void setLetteringId(List<String> letteringId) {
        this.letteringId = letteringId;
    }
}
