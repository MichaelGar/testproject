package com.example.alpha.projecttest;

import android.content.Context;

import com.example.alpha.projecttest.models.Question;
import com.example.alpha.projecttest.models.Test;
import com.example.alpha.projecttest.models.TestDescription;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import java.util.ArrayList;

import static android.net.Uri.encode;


/**
 * Created by 1 on 07.01.2015.
 */
public class RealDataLoader implements DataLoaderInterface {

    public ArrayList<TestDescription> loadListTests(String user, String password){
       /* try { //типа грузит 10 секунд
            Thread.sleep(3000,1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //load list test
        String JSONListTests = getdata("http://tester.handh.ru/api/v1/test/?format=json");
        //String JSONListTests = "{list:[{name:\"Test1\",id:\"122\",description:\"Описание\"},{name:\"Test2\",id:\"121\",description:\"Описани2е\"}]}";
        ArrayList<TestDescription> listTests = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(JSONListTests);
            JSONArray jsonList = json.getJSONArray("objects");
            for (int i = 0; i < jsonList.length(); i++){
                JSONObject oneTest = jsonList.getJSONObject(i);
                String nameX = oneTest.getString("name");
                String last_modifiedX = oneTest.getString("last_modified");
                String descriptionX = oneTest.getString("resource_uri");
                int idX = oneTest.getInt("id");
                TestDescription testDescription = new TestDescription();
                testDescription.name = nameX;
                testDescription.id = idX;
                testDescription.last_modified = last_modifiedX;
                testDescription.description = descriptionX;
                listTests.add(testDescription);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listTests;
    }

    public Test loadTest(int id, String date, Context context, String name){
        DBHelper db = new DBHelper(context);
        String questionJSON;
        if (db.findTest(id,date)){
            questionJSON = db.getTest(id);
        }else {

            /*questionJSON = "{" +
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
                    "}";*/
            String par = "http://tester.handh.ru/api/v1/question/?format=json&test__id=" + String.valueOf(id);
            questionJSON = getdata(par);



            db.setTest(id,date,questionJSON);
           /* try { //типа грузит 10 секунд
                Thread.sleep(3000, 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        return CreateTest(id, name, questionJSON);
        //return null;

    }
    private Test CreateTest(int idX,String nameX,String questionJSON){
        Test test = new Test();
        test.questions = new ArrayList();
        try {
            JSONObject json = new JSONObject(questionJSON);
            //String nameX = json.getString("name");
            //int idX = json.getInt("id");
            test.name = nameX;
            test.id = idX;
            JSONArray jsonTextQuestion = json.getJSONArray("objects");
            for (int i = 0; i < jsonTextQuestion.length(); i++){
                JSONObject oneQuestion = jsonTextQuestion.getJSONObject(i);
                Question question = new Question();
                int idQ = oneQuestion.getInt("id");
               // String nameQ = oneQuestion.getString("name");
                String textQuestionQ = oneQuestion.getString("text");
                String imageQ = oneQuestion.getString("img");
                //String answersQ = oneQuestion.getString("answers");
                question.id = idQ;
               //question.name = nameQ;
                question.textQuestion = textQuestionQ;
                question.image = imageQ;
                //this.CreateListAnswers(question,answersQ);
                test.questions.add(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return test;
    }


    String getdata(String par){
        String str = "";
        try {
        /*    //DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(par);
            HttpResponse httpResponse = null;
           // httpGet.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
         //   str = httpEntity.getContent().toString();
            str = httpClient.execute(httpGet).toString();
*/
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpGet http = new HttpGet(par);

//получаем ответ от сервера
            str = hc.execute(http, res);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }




}
