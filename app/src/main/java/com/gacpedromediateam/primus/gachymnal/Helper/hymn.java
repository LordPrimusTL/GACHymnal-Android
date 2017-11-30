package com.gacpedromediateam.primus.gachymnal.Helper;

/**
 * Created by Primus on 7/10/2017.
 */

public class hymn {

    public Integer hymn_id;
    public String english;

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getYoruba() {
        return yoruba;
    }

    public void setYoruba(String yoruba) {
        this.yoruba = yoruba;
    }

    public String yoruba;
    public String title;

    public hymn(Integer hymn_id, String english, String yoruba) {
        this.hymn_id = hymn_id;
        this.english = english;
        this.yoruba = yoruba;
    }

    public hymn(Integer hymn_id, String title) {
        this.hymn_id = hymn_id;
        this.title = title;
    }

    public int getID() {
        return hymn_id;
    }


    public String getTitle() {
        return title;
    }


    @Override
    public String toString() {
        return "hymn{" +
                "hymn_id=" + hymn_id +
                ", english='" + english + '\'' +
                ", yoruba='" + yoruba + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
