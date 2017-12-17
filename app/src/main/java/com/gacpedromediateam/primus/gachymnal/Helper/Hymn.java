package com.gacpedromediateam.primus.gachymnal.Helper;

/**
 * Created by Primus on 7/10/2017.
 */

public class Hymn {

    public Integer hymn_id;
    public String english;
    public int fave;
    public String yoruba;
    public String title;

    public int getFave() {
        return fave;
    }
    public void setFave(int fave) {
        this.fave = fave;
    }

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



    public Hymn(Integer hymn_id, String english, String yoruba, int fave) {
        this.hymn_id = hymn_id;
        this.english = english;
        this.yoruba = yoruba;
        this.fave = fave;
    }

    public int getID() {
        return hymn_id;
    }


    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Hymn{" +
                "hymn_id=" + hymn_id +
                ", english='" + english + '\'' +
                ", fave=" + fave +
                ", yoruba='" + yoruba + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
