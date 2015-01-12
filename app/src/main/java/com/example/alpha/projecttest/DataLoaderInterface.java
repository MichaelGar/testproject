package com.example.alpha.projecttest;

import android.content.Context;

import com.example.alpha.projecttest.models.Test;
import com.example.alpha.projecttest.models.TestDescription;

import java.util.ArrayList;

/**
 * Created by 1 on 07.12.2014.
 */
public interface DataLoaderInterface {
    //public Test loadTest(int id, String date, Context context);
    public ArrayList<TestDescription> loadListTests(String user, String password);
}
