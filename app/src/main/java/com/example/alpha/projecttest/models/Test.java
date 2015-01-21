package com.example.alpha.projecttest.models;

import java.util.ArrayList;

/**
 * Created by 1 on 16.12.2014.
 */
public class Test {
    public String name;
    public int id;
    public String description;
    public String last_modified;
    public int attempt_count;
    public int time;
    public int max;
    public int questions_count;
    public ArrayList<Question> questions = null;
    public int grades;
    public int count;
    public Boolean onTimer = false;
}
