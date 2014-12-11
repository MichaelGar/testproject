package com.example.alpha.projecttest;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 1 on 07.12.2014.
 */
public class Question {
    // TODO: Public
    private int id;
    private String name;
    private String textQuestion;
    private String image;
    private ArrayList<Answer> answers;

    // TODO: Remove all
    void newQuestion (int idX, String nameX, String textQuestionX, String imageX){
        id = idX;
        name = nameX;
        textQuestion = textQuestionX;
        image = imageX;
    }

    int getId(){
        return id;
    }

    String getName(){
        return name;
    }

    String getTextQuestion(){
        return textQuestion;
    }

    String getImage(){
        return image;
    }

    // TODO: Replace to data loader
    void CreateListAnswers(String answerJSON){
        answers = new ArrayList();
        try {
            JSONObject json = new JSONObject(answerJSON);
            int number = json.getInt("number"); //узнаем сколько всего ответов
            JSONArray jsonTextAnswer = json.getJSONArray("answers");
            for (int i = 0; i < number; i++){
                JSONObject oneAnswer = jsonTextAnswer.getJSONObject(i);
                String textA = oneAnswer.getString("text");
                Answer answer = new Answer();
                answer.newAnswer(textA);
                answers.add(answer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
