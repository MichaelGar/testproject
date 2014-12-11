package com.example.alpha.projecttest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 1 on 07.12.2014.
 */
public class Test {
    public int id;
    public String name;
    public ArrayList<Question> questions;



    // TODO: Replcae to loader
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
               // question.newQuestion(idQ,nameQ,textQuestionQ,imageQ);
                question.id = idQ;
                question.name = nameQ;
                question.textQuestion = textQuestionQ;
                question.image = imageQ;
                question.CreateListAnswers(answersQ);
                questions.add(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
