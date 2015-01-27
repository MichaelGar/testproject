package com.example.alpha.projecttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.alpha.projecttest.models.Test;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
@EBean
public class TestListAdapter extends BaseAdapter {
    ProcessTest prc;
    //private Context ctx;
    //private LayoutInflater lInflater;
    public ArrayList <Test> objects;

    @RootContext
    Context ctx;

    @AfterInject
    void initTestListAdapter() {
        objects = prc.rdl.loadListTests("","",prc.serverURL);
    }
    //TestListAdapter(Context ctx){
        //ctx = context;
        //objects = prc.rdl.loadListTests("","",prc.serverURL);
        //lInflater = (LayoutInflater) ctx
                //.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //}

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Test getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        Test TestD;
        TestD = objects.get(position);
        return TestD.id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TestListElement testListElement;
        if (convertView == null) {
            testListElement = TestListElement_.build(ctx);
        } else {
            testListElement = (TestListElement) convertView;
        }


        testListElement.bind(getItem(position));

        return testListElement;
        /*View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.small_test_card, parent, false);
        }

        Test p = objects.get(position);
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
}
