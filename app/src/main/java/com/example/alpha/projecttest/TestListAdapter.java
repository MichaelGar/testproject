package com.example.alpha.projecttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 11.12.14.
 */
public class TestListAdapter extends BaseAdapter {
    // TODO: private
    Context ctx;
    LayoutInflater lInflater;
    ArrayList <Test> objects;

    TestListAdapter(Context context, ArrayList<Test> tests){
        ctx = context;
        objects = tests;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.small_test_card, parent, false);
        }

        Test p = getTest(position);

        ((TextView) view.findViewById(R.id.test_name)).setText(p.name);

        return view;
    }

    Test getTest (int position){
        return ((Test) getItem(position));
    }
}
