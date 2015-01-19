package com.example.alpha.projecttest;

import android.content.Context;

import com.example.alpha.projecttest.models.Answer;
import com.example.alpha.projecttest.models.Question;
import com.example.alpha.projecttest.models.Test;
import com.example.alpha.projecttest.models.TestHeader;

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
import java.util.Random;

import static android.net.Uri.encode;


/**
 * Created by 1 on 07.01.2015.
 */
public class RealDataLoader implements DataLoaderInterface {

    public ArrayList<TestHeader> loadListTests(String user, String password){
       /* try { //типа грузит 10 секунд
            Thread.sleep(3000,1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //load list test
        ArrayList<TestHeader> listTests = new ArrayList<>();
        while (listTests.size() == 0) {
            String JSONListTests = getdata("http://tester.handh.ru/api/v1/test/?format=json");
            //String JSONListTests = "{list:[{name:\"Test1\",id:\"122\",description:\"Описание\"},{name:\"Test2\",id:\"121\",description:\"Описани2е\"}]}";

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
                    TestHeader testHeader = new TestHeader();
                    testHeader.name = nameX;
                    testHeader.id = idX;
                    testHeader.last_modified = last_modifiedX;
                    testHeader.description = descriptionX;
                    testHeader.time = timeX;
                    testHeader.max = max;
                    testHeader.attempt_count = attempt_countX;
                    listTests.add(testHeader);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listTests;
    }

    public Test loadTest(int id, String date,QuestionActivity context){
        Boolean fromdb;
        DBHelper db = new DBHelper(context);
        String questionJSON;
        if (db.findTest(id,date)){//
            questionJSON = db.getTest(id);
            fromdb = true;
        }else {
            String par = "http://tester.handh.ru/api/v1/question/?format=json&test__id=" + String.valueOf(id);
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
            //   test.name = nameX;
            test.id = idX;
            //  test.time = timeX;
            String answersQ = "";
            JSONArray jsonTextQuestion = json.getJSONArray("objects");
            for (int i = 0; i < jsonTextQuestion.length(); i++) {
                JSONObject oneQuestion = jsonTextQuestion.getJSONObject(i);
                Question question = new Question();
                int idQ = oneQuestion.getInt("id");
                int qtypeQ = oneQuestion.getInt("qtype");
                // String nameQ = oneQuestion.getString("name");
                String textQuestionQ = oneQuestion.getString("text");
                String imageQ = oneQuestion.getString("img");
                question.id = idQ;
                //question.name = nameQ;
                question.textQuestion = textQuestionQ;
                question.image = imageQ;
                question.qtype = qtypeQ;
                if (fromdb) {
                    answersQ = db.getAnswersFromBD(idQ);
                } else {
                    if (goodAnswer) {
                        answersQ = getdata("http://tester.handh.ru/api/v1/answer/?format=json&question__id=" + String.valueOf(idQ));
                        //int pos = answersQ.indexOf("objects");
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
        //int pos = questionJSON.indexOf("objects");
        if (!goodAnswer || (questionJSON.indexOf("objects") == -1)) {
            test = null;
        }// else {
       //     test = randomTest(test);
       // }
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


    String getdata(String par){
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


    public Integer getTime(Test test){
        return test.time;
    }
    //ребят че за херня? подругому нельзя чтоль время вытащить?

  /*  class MyTask extends AsyncTask<Void, Void, Integer> {
        RealDataLoader l;
        String login, password;
        ArrayList<TestDescription> listTests;
        //void unLink() {
       //     activity = null;
       // }

      //  void link(TestList act){
      //      activity = act;
       // }
        void setLoginPassword(String log, String pas){
            login = log;
            password = pas;
        }
        @Override
        protected Integer doInBackground(Void... params) {
            //FakeDataLoader l = new FakeDataLoader();
          //  RealDataLoader l = new RealDataLoader();
            MyApp app = ((MyApp) getApplicationContext());
            testAsync = l.loadListTests(login,password);
            return 100500;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            l.readyList(listTests);
        }
    }
*/
}
