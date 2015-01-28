package com.example.alpha.projecttest;

import com.example.alpha.projecttest.models.Test;

import java.util.ArrayList;

public interface DataLoaderInterface {
    //public Test loadTest(int id, String date, Context context);
    public ArrayList<Test> loadListTests();
    public Test loadTest(int id, String date,QuestionActivity context, String serverURL);
}