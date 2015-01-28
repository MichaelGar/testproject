package com.example.alpha.projecttest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.alpha.projecttest.models.Test;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class TestListAdapter extends BaseAdapter {
    //List<Test> tests;
    //ProcessTest prc;
    //private Context ctx;
    //private LayoutInflater lInflater;

    List<Test> tests;


    //public TestListAdapter() {
    //    this.tests = tests;
    //}

    @Bean(ProcessTest.class)
    ProcessTest
            processTestInterface;

    @RootContext
    Context context;



    @AfterInject
    void initAdapter() {
        //tests = dataLoaderInterface.loadListTests("","","http://tester.handh.ru");
        tests = processTestInterface.rdl.loadListTests();
    }
    //TestListAdapter(Context ctx){
        //ctx = context;
        //objects = prc.rdl.loadListTests("","",prc.serverURL);
        //lInflater = (LayoutInflater) ctx
                //.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TestListElement testListElement;
        if (convertView == null) {
            testListElement = TestListElement_.build(context);
        } else {
            testListElement = (TestListElement) convertView;
        }


        testListElement.bind(getItem(position));

        return testListElement;
        /*
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.small_test_card, parent, false);
        }

        Test p = tests.get(position);
        if (p.time==0){
            ((TextView) view.findViewById(R.id.test_time)).setText("Время не ограничено");
        }
        else {
            ((TextView) view.findViewById(R.id.test_time)).setText(p.time + " мин.");
        }
        ((TextView) view.findViewById(R.id.test_qcount)).setText("Вопросов: "+p.questions_count);
        ((TextView) view.findViewById(R.id.test_name)).setText(p.name);
        ((TextView) view.findViewById(R.id.test_descr)).setText(p.description);
        return view;*/
    }

    @Override
    public int getCount() {
        return tests.size();
    }

    @Override
    public Test getItem(int position) {
        return tests.get(position);
    }

    @Override
    public long getItemId(int position) {
        Test TestD;
        TestD = tests.get(position);
        return TestD.id;
    }


}
