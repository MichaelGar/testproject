package com.example.alpha.projecttest;

import com.example.alpha.projecttest.models.Question;

/**
 * Created by 1 on 22.01.2015.
 */
public interface QuestionActivityInterface {
    public void showTimer(boolean mode, long min, long sec);
    public void showMSG(String str);
    public void goResult();
    public void showQuestion(Question question,int count,int max, String imagelink);
}
