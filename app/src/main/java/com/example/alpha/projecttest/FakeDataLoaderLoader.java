package com.example.alpha.projecttest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 1 on 07.12.2014.
 */
public class FakeDataLoaderLoader implements DataLoaderInterface {

    public void loadTest(int id){
        String questionJSON = "{" +
                      "\"number\":2," +
                      "\"TextQuestion\":[" +
                      "{" +
                      "\"id\":0," +
                      "\"name\":\"Test1\"," +
                      "\"textQuestion\":\"Textвопроса\"," +
                      "\"image\":\"Link\"," +
                      "\"answers\":" +
                      "{" +
                      "\"number\":2," +
                      "\"answers\":" +
                      "[" +
                      "{\"text\":\"Ответ1\"},{\"text\":\"Ответ2\"}" +
                      "]" +
                      "}" +
                      "},"+
                      "{" +
                      "\"id\":0," +
                      "\"name\":\"Test1\"," +
                      "\"textQuestion\":\"Textвопроса2\"," +
                      "\"image\":\"Ссылка2\"," +
                      "\"answers\":" +
                      "{" +
                      "\"number\":2," +
                      "\"answers\":" +
                      "[" +
                      "{\"text\":\"Ответ1\"},{\"text\":\"Ответ2\"}" +
                      "]" +
                      "}" +
                      "}" +
                      "]" +
                      "}";
        createTest(questionJSON);
    }

    private Test createTest(String questionJSON){
        Test test = new Test();
        test.name = "Test";
        test.id = 0;
        this.CreateListQuestions(test,questionJSON);
        return test;
    }

    public String loadListTests(String user, String password){
        return "";
    }

    private void CreateListQuestions(Test test,String questionJSON){
        try {
            test.questions = new ArrayList();
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
                this.CreateListAnswers(question,answersQ);
                test.questions.add(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void CreateListAnswers(Question question, String answerJSON){
       question.answers = new ArrayList();
        try {
            JSONObject json = new JSONObject(answerJSON);
            int number = json.getInt("number"); //узнаем сколько всего ответов
            JSONArray jsonTextAnswer = json.getJSONArray("answers");
            for (int i = 0; i < number; i++){
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
