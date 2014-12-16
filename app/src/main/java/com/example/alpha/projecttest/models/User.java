package com.example.alpha.projecttest.models;

/**
 * Created by 1 on 07.12.2014.
 */
public class User {
    public int id;
    public String name;

    void newUser (String nameX){
        id = 0;
        name = nameX;
    }

    void newUserWithID (int idX, String nameX){
        id = idX;
        name = nameX;
    }

    int getId(){
        return id;
    }

    String getName(){
        return name;
    }
}
