package com.example.alpha.projecttest.models;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 1 on 07.12.2014.
 */
public class Question {
    public int id;
    public String name;
    public String textQuestion;
    public String image;
    public ArrayList<Answer> answers;

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
                answer.text = textA;
                answers.add(answer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
