package com.example.alpha.projecttest;

import com.example.alpha.projecttest.models.TestHeader;

import java.util.ArrayList;

/**
 * Created by 1 on 07.12.2014.
 */
public interface DataLoaderInterface {
    //public Test loadTest(int id, String date, Context context);
    public ArrayList<TestHeader> loadListTests(String user, String password);
}
