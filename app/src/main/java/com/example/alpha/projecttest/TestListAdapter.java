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
    List<Test> tests;

    @Bean(RealDataLoader.class)
    DataLoaderInterface dataLoaderInterface;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        tests = dataLoaderInterface.loadListTests();
    }

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
