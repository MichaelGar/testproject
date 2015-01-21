package com.example.alpha.projecttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alpha.projecttest.models.Answer;
import com.example.alpha.projecttest.models.Test;

import java.text.ChoiceFormat;
import java.util.ArrayList;

/**
 * Created by user on 21.01.2015.
 */
public class AnswerListAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    public ArrayList<String> objects;
    //ArrayList<String> answers;

    AnswerListAdapter(Context context, ArrayList<String> answers) {
        ctx = context;
        objects = answers;
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
            view = lInflater.inflate(R.layout.answer_list_element, parent, false);
        }
        String a = objects.get(position);
        ((TextView) view.findViewById(R.id.textView3)).setText(a);

        return view;
    }
}
