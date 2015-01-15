package com.example.alpha.projecttest;

import com.example.alpha.projecttest.models.Answer;
import com.example.alpha.projecttest.models.Question;
import com.example.alpha.projecttest.models.Test;
import android.util.SparseBooleanArray;
import java.util.ArrayList;

/**
 * Created by 1 on 12.01.2015.
 */
//TODO:Сделать ФИНИШ NAHOOY
public class ProcessTest {
    public Question getQuestion(Test test, int rez){
       ArrayList<Question> questions = test.questions;
       int all = questions.size();
       if (test.count == all) {
           return null;
       } else {
       return questions.get(test.count);}
   }

    public Integer getType(Test test){
        ArrayList<Question> questions = test.questions;
        return questions.get(test.count).qtype;
    }

    public Integer getTime(Test test){
        return test.time;
    }
        //ребят че за херня? подругому нельзя чтоль время вытащить?
    public ArrayList<String> getAnswers(Question question){
        ArrayList<Answer> answersList= question.answers;
        ArrayList<String> answers = new ArrayList<>();
        for (int i = 0; i < answersList.size(); i++){
            Answer answer = answersList.get(i);
            answers.add(answer.text);
        }
        return answers;
    }

   public int getGrade(Question question, SparseBooleanArray sbArray){
        int grades = 0;
        ArrayList<Answer> answers = question.answers;
        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)){
                Answer answer = answers.get(key);
                if (answer.isRight){
                    grades++;
                }else {
                    grades--;
                }
            }
        }
        if(grades < 0){
            grades = 0;
        }
        return grades;
    }

   public Test finishTest(Test test){
        int max = 0;
        for (int i = 0; i < test.questions.size(); i++){
            Question question = test.questions.get(i);
            for (int j = 0; j < question.answers.size(); j++){
                Answer answer = question.answers.get(j);
                if (answer.isRight){
                max++;
                }
            }
        }
    test.max = max;
    return test;
   }
}
