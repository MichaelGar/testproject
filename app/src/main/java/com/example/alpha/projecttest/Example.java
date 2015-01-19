package com.example.alpha.projecttest;

import java.util.ArrayList;

/**
 * Created by user on 15.01.15.


interface TestListActivityInterface{
    public showTestList(ArrayList<Test> testList);
}

public class Example implements TestListActivityInterface{

    private ProcessTest testModel;

    void onCreate(){

        setContentView();
        testModel = new ProcessTest();
        testModel.loader = new RealDataLoader("server.address");
        testModel.testListActivity = self;

        testModel.loadTest(list);

    }

}

interface DataLoaderDelegateInterface {

    testRecived(ArrayList<Test> testLis);

}


class ProcessTestt implements  DataLoaderDelegateInterface{
    DataLoaderInterface loader;
    TestListActivityInterface testListActivity;

    public loadTest(){
        loader.delegate = self;
        loader.loadListTests();
    }

    testRecived(ArrayList<Test> testLis)
    {
        self.testListActivity.showTestList(testList);
    }
}*/