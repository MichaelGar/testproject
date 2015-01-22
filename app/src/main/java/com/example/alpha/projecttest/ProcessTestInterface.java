package com.example.alpha.projecttest;

import android.util.SparseBooleanArray;

import com.example.alpha.projecttest.models.Question;
import com.example.alpha.projecttest.models.Test;

import java.util.ArrayList;

/**
 * Created by 1 on 22.01.2015.
 */
public interface ProcessTestInterface {
    public void getListTests(TestList testListX);
    public void getResult(ResultActivity resultActivity);
    public void clean();
    public void getQuestion(QuestionActivity questionActivityX);
    public void setAnswer(SparseBooleanArray sbArray);
    public ArrayList<String> getAnswers(Question question);
    public Test finishTest(Test test);
}
