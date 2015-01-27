package com.example.alpha.projecttest;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alpha.projecttest.models.Test;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.small_test_card)
public class TestListElement extends LinearLayout{
    @ViewById TextView test_time, test_qcount, test_name, test_descr;

    public TestListElement(Context context) {
        super(context);
    }
    public void bind (Test test){
        if (test.time==0){
            test_time.setText("Время не ограничено");
        }
        else {
            test_time.setText(test.time + " мин.");
        }
        test_qcount.setText("Вопросов: "+test.questions_count);
        test_name.setText(test.name);
        test_descr.setText(test.description);
    }
}
