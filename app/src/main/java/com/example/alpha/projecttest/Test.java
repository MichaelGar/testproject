package com.example.alpha.projecttest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 1 on 07.12.2014.
 */
public class Test {
    private int id;
    private String name;
    private ArrayList questions;
    void newTest (String nameX){
        id = 0;
        name = nameX;
    }

    void newTestWithID (int idX, String nameX){
        id = idX;
        name = nameX;
    }

    int getId(){
        return id;
    }

    String getName(){
        return name;
    }
    void CreateListQuestions(String questionJSON){
        try {
            questions = new ArrayList();
            JSONObject json = new JSONObject(questionJSON);
            int number = json.getInt("number"); //узнаем сколько всего вопросов
            JSONArray jsonTextQuestion = json.getJSONArray("TextQuestion");
            for (int i = 0; i < number; i++){
                JSONObject oneQuestion = jsonTextQuestion.getJSONObject(i);
                Question question = new Question();
                int idQ = oneQuestion.getInt("id");
                String nameQ = oneQuestion.getString("name");
                String textQuestionQ = oneQuestion.getString("textQuestion");
                String imageQ = oneQuestion.getString("image");
                String answersQ = oneQuestion.getString("answers");
                question.newQuestion(idQ,nameQ,textQuestionQ,imageQ);
                question.CreateListAnswers(answersQ);
                questions.add(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
