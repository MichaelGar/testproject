package com.example.alpha.projecttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alpha.projecttest.models.Test;
import com.example.alpha.projecttest.models.TestDescription;

import java.util.ArrayList;

/**
 * Created by user on 11.12.14.
 */
public class TestListAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    public ArrayList <TestDescription> objects;

    TestListAdapter(Context context, ArrayList<TestDescription> tests){
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
        TestDescription TestD;
        TestD = objects.get (position);
        return TestD.id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.small_test_card, parent, false);
        }

        TestDescription p = objects.get(position);
        if (p.time==0){
            ((TextView) view.findViewById(R.id.test_time)).setText("Время неограничено");
        }
        else {
            ((TextView) view.findViewById(R.id.test_time)).setText(p.time + " минут");
        }
        //TODO:переделать attempt count в колво вопросов
        ((TextView) view.findViewById(R.id.test_qcount)).setText("Вопросов: "+p.attempt_count);

        ((TextView) view.findViewById(R.id.test_name)).setText(p.name);
        ((TextView) view.findViewById(R.id.test_descr)).setText(p.description);
        return view;
    }
}
