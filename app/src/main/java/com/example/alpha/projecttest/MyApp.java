package com.example.alpha.projecttest;

import android.app.Application;

public class MyApp extends Application {
    ProcessTest prc;
    @Override
   public void onCreate(){
        prc = new ProcessTest();
        prc.rdl = new RealDataLoader();
   }
}