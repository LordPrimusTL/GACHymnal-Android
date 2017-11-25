package com.gacpedromediateam.primus.gachymnal.Helper;

/**
 * Created by Primus on 7/11/2017.
 */

public class verse {
    public Integer hymn_id;
    public Integer verse_id;

    public verse(Integer verse_id, String word) {
        this.verse_id = verse_id;
        this.word = word;
    }

    public String english;

    public String getWord() {
        return word;
    }

    public String yoruba;
    public String word;

    public verse(Integer hymn_id, Integer verse_id, String english, String yoruba) {
        this.hymn_id = hymn_id;
        this.verse_id = verse_id;
        this.english = english;
        this.yoruba = yoruba;
    }

    public Integer getHymn_id() {
        return hymn_id;
    }

    @Override
    public String toString() {
        return "verse{" +
                "hymn_id=" + hymn_id +
                ", verse_id=" + verse_id +
                ", english='" + english + '\'' +
                ", yoruba='" + yoruba + '\'' +
                ", word='" + word + '\'' +
                '}';
    }

    public Integer getVerse_id() {
        return verse_id;
    }

    public String getEnglish() {
        return english;
    }

    public String getYoruba() {
        return yoruba;
    }




}
