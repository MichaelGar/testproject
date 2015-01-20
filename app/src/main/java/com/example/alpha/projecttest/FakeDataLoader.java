package com.example.alpha.projecttest;

import android.content.Context;

import com.example.alpha.projecttest.models.Answer;
import com.example.alpha.projecttest.models.Question;
import com.example.alpha.projecttest.models.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 1 on 07.12.2014.
 */
public class FakeDataLoader implements DataLoaderInterface {
    public Test loadTest(int id,String date, Context context){
        DBHelper db = new DBHelper(context);
        String questionJSON;
        if (db.findTest(id,date)){
            questionJSON = db.getTest(id);
        }else {
            questionJSON = "{" +
                    "\"name\":\"Название теста\"," +
                    "\"id\":121," +
                    "\"TextQuestion\":[" +
                    "{" +
                    "\"id\":0," +
                    "\"name\":\"Test1\"," +
                    "\"textQuestion\":\"Textвопроса\"," +
                    "\"image\":\"Link\"," +
                    "\"answers\":" +
                    "{" +
                    "\"answers\":" +
                    "[" +
                    "{\"text\":\"Ответ1\"},{\"text\":\"Ответ2\"}" +
                    "]" +
                    "}" +
                    "}," +
                    "{" +
                    "\"id\":0," +
                    "\"name\":\"Test1\"," +
                    "\"textQuestion\":\"Textвопроса2\"," +
                    "\"image\":\"Ссылка2\"," +
                    "\"answers\":" +
                    "{" +
                    "\"answers\":" +
                    "[" +
                    "{\"text\":\"Ответ1\"},{\"text\":\"Ответ2\"}" +
                    "]" +
                    "}" +
                    "}" +
                    "]" +
                    "}";
            db.setTest(id,date,questionJSON);
            try { //типа грузит 10 секунд
                Thread.sleep(3000, 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
      return CreateTest(questionJSON);
    }

    public ArrayList<Test> loadListTests(String user, String password){
        try { //типа грузит 10 секунд
            Thread.sleep(3000,1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String JSONListTests = "{list:[{name:\"Test1\",id:\"122\",description:\"Описание\"},{name:\"Test2\",id:\"121\",description:\"Описани2е\"}]}";
        ArrayList<Test> listTests = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(JSONListTests);
            JSONArray jsonList = json.getJSONArray("list");
            for (int i = 0; i < jsonList.length(); i++){
                JSONObject oneTest = jsonList.getJSONObject(i);
                String nameX = oneTest.getString("name");
                String descriptionX = oneTest.getString("description");
                int idX = oneTest.getInt("id");
                Test test = new Test();
                test.name = nameX;
                test.id = idX;
                test.description = descriptionX;
                listTests.add(test);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listTests;
    }

    private Test CreateTest(String questionJSON){
        Test test = new Test();
        test.questions = new ArrayList();
        try {
            JSONObject json = new JSONObject(questionJSON);
            String nameX = json.getString("name");
            int idX = json.getInt("id");
            test.name = nameX;
            test.id = idX;
            JSONArray jsonTextQuestion = json.getJSONArray("TextQuestion");
            for (int i = 0; i < jsonTextQuestion.length(); i++){
                JSONObject oneQuestion = jsonTextQuestion.getJSONObject(i);
                Question question = new Question();
                int idQ = oneQuestion.getInt("id");
                String nameQ = oneQuestion.getString("name");
                String textQuestionQ = oneQuestion.getString("textQuestion");
                String imageQ = oneQuestion.getString("image");
                String answersQ = oneQuestion.getString("answers");
                question.id = idQ;
                question.name = nameQ;
                question.textQuestion = textQuestionQ;
                question.image = imageQ;
                this.CreateListAnswers(question,answersQ);
                test.questions.add(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return test;
    }

    private void CreateListAnswers(Question question, String answerJSON){
       question.answers = new ArrayList();
        try {
            JSONObject json = new JSONObject(answerJSON);
            JSONArray jsonTextAnswer = json.getJSONArray("answers");
            for (int i = 0; i < jsonTextAnswer.length(); i++){
                JSONObject oneAnswer = jsonTextAnswer.getJSONObject(i);
                String textA = oneAnswer.getString("text");
                Answer answer = new Answer();
                answer.text = textA;
                question.answers.add(answer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void onLoad(Test test){}
}
