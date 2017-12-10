package com.gacpedromediateam.primus.gachymnal.Helper;

/**
 * Created by micheal on 10/12/2017.
 */

public class Team {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
