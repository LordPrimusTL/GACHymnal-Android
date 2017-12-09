package com.gacpedromediateam.primus.gachymnal.Helper;

/**
 * Created by Primus on 7/9/2017.
 */

public class User {
    public String Name;
    public String Branch;
    public String UUID;
    public User(String Name, String Branch, String UUID)
    {
        this.Name = Name;
        this.Branch = Branch;
        this.UUID = UUID;
    }

    public User(String Name, String Branch)
    {
        this.Name = Name;
        this.Branch = Branch;
    }
}
