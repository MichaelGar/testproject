package com.example.alpha.projecttest;

import com.example.alpha.projecttest.models.Answer;
import com.example.alpha.projecttest.models.Question;
import com.example.alpha.projecttest.models.Test;

import java.util.ArrayList;

/**
 * Created by 1 on 12.01.2015.
 */
public class ProcessTest {
   public Question getQuestion(Test test, int count, int rez){
       ArrayList<Question> questions = test.questions;
       int all = questions.size();
       if (count == all) {
           return null;
       } else {
       return questions.get(count);}
   }

    public Integer getType(Test test, int count){
        ArrayList<Question> questions = test.questions;
            return questions.get(count).qtype;
    }

    public Integer getTime(Test test){
        return test.time;
    }

    public ArrayList<String> getAnswers(Question question){
        ArrayList<Answer> answersList= question.answers;
        ArrayList<String> answers = new ArrayList<>();
        for (int i = 0; i < answersList.size(); i++){
            Answer answer = answersList.get(i);
            answers.add(answer.text);
        }
        return answers;
    }
}
