package com.example.alpha.projecttest;

import android.content.Context;
import com.example.alpha.projecttest.models.Answer;
import com.example.alpha.projecttest.models.Question;
import com.example.alpha.projecttest.models.Test;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;



public class RealDataLoader implements DataLoaderInterface {

    public ArrayList<Test> loadListTests(String user, String password, String serverURL){
        ArrayList<Test> listTests = new ArrayList<>();
        while (listTests.size() == 0) {
            String JSONListTests = getdata(serverURL + "/api/v1/test/?format=json");
            listTests = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(JSONListTests);
                JSONArray jsonList = json.getJSONArray("objects");
                for (int i = 0; i < jsonList.length(); i++) {
                    JSONObject oneTest = jsonList.getJSONObject(i);
                    String nameX = oneTest.getString("name");
                    String last_modifiedX = oneTest.getString("last_modified");
                    String descriptionX = oneTest.getString("description");
                    int timeX = oneTest.getInt("time");
                    int max = oneTest.getInt("questions_count");
                    int attempt_countX = oneTest.getInt("attempt_count");
                    int idX = oneTest.getInt("id");
                    int questions_countX = oneTest.getInt("questions_count");
                    Test test = new Test();
                    test.name = nameX;
                    test.id = idX;
                    test.last_modified = last_modifiedX;
                    test.description = descriptionX;
                    test.time = timeX;
                    test.max = max;
                    test.questions_count = questions_countX;
                    test.attempt_count = attempt_countX;
                    listTests.add(test);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listTests;
    }

    public Test loadTest(int id, String date,QuestionActivity context,String serverURL){
        Boolean fromdb;
        DBHelper db = new DBHelper(context);
        String questionJSON;
        if (db.findTest(id,date)){//
            questionJSON = db.getTest(id);
            fromdb = true;
        }else {
            String par = serverURL + "/api/v1/question/?format=json&test__id=" + String.valueOf(id);
            questionJSON = getdata(par);
            fromdb = false;
        }
        return CreateTest(id,date, questionJSON, fromdb, context);
    }

    private Test CreateTest(int idX,String date,String questionJSON,boolean fromdb,Context context) {
        DBHelper db = new DBHelper(context);
        Test test = null;
        while (test == null){
        test = new Test();
        test.grades = 0;
        test.questions = new ArrayList();
        boolean goodAnswer = true;
        try {
            JSONObject json = new JSONObject(questionJSON);
            test.id = idX;
            String answersQ = "";
            JSONArray jsonTextQuestion = json.getJSONArray("objects");
            for (int i = 0; i < jsonTextQuestion.length(); i++) {
                JSONObject oneQuestion = jsonTextQuestion.getJSONObject(i);
                Question question = new Question();
                int idQ = oneQuestion.getInt("id");
                int qtypeQ = oneQuestion.getInt("qtype");
                String textQuestionQ = oneQuestion.getString("text");
                String imageQ = oneQuestion.getString("img");
                question.id = idQ;
                question.textQuestion = textQuestionQ;
                question.image = imageQ;
                question.qtype = qtypeQ;
                if (fromdb) {
                    answersQ = db.getAnswersFromBD(idQ);
                } else {
                    if (goodAnswer) {
                        answersQ = getdata("http://tester.handh.ru/api/v1/answer/?format=json&question__id=" + String.valueOf(idQ));
                        if (answersQ.indexOf("objects") > -1) {
                            db.setAnswerInBD(idQ, answersQ);
                        } else {
                            goodAnswer = false;
                        }
                    }
                }
                if (goodAnswer) {
                    this.CreateListAnswers(question, answersQ);
                }
                test.questions.add(question);
            }
            if (!fromdb && goodAnswer) {
                db.setTest(idX, date, questionJSON);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!goodAnswer || (questionJSON.indexOf("objects") == -1)) {
            test = null;
        }
    }
        return test;
    }

    private void CreateListAnswers(Question question, String answerJSON){
        question.answers = new ArrayList();
        try {
            JSONObject json = new JSONObject(answerJSON);
            JSONArray jsonTextAnswer = json.getJSONArray("objects");
            for (int i = 0; i < jsonTextAnswer.length(); i++){
                JSONObject oneAnswer = jsonTextAnswer.getJSONObject(i);
                int idA = oneAnswer.getInt("id");
                String textA = oneAnswer.getString("text");
                boolean isRightA = oneAnswer.getBoolean("isRight");
                Answer answer = new Answer();
                answer.ID = idA;
                answer.isRight = isRightA;
                answer.text = textA;
                question.answers.add(answer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private String getdata(String par){
        String str = "";
        try {

            HttpClient hc = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpGet http = new HttpGet(par);
            HttpResponse response = hc.execute(http);
            HttpEntity entity = response.getEntity();
            str = EntityUtils.toString(entity, "UTF-8");
//получаем ответ от сервера
        } catch (IOException e) {
            e.printStackTrace();

        }

        return str;
    }
}
