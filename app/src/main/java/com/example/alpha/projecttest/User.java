package com.example.alpha.projecttest;

/**
 * Created by 1 on 07.12.2014.
 */
public class User {
    private int id;
    private String name;

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
