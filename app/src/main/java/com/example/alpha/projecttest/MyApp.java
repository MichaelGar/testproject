package com.example.alpha.projecttest;

import android.app.Application;

/**
 * Created by 1 on 19.01.2015.
 */
public class MyApp extends Application {
    ProcessTest prc;
    @Override
   public void onCreate(){
        prc = new ProcessTest();
        prc.rdl = new RealDataLoader();
   }
}
