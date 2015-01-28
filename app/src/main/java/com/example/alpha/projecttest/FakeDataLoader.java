package com.example.alpha.projecttest;

import android.content.Context;

import com.example.alpha.projecttest.models.Answer;
import com.example.alpha.projecttest.models.Question;
import com.example.alpha.projecttest.models.Test;

import org.androidannotations.annotations.EBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by 1 on 07.12.2014.
 */
@EBean
public class FakeDataLoader implements DataLoaderInterface {

    public ArrayList<Test> loadListTests(){
       /* try { //типа грузит 10 секунд
            Thread.sleep(3000,1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //load list test
        ArrayList<Test> listTests = new ArrayList<>();
        while (listTests.size() == 0) {
            String JSONListTests =// getdata("http://tester.handh.ru/api/v1/test/?format=json");
             "{\n" +
                     "                \"meta\": {\n" +
                     "                \"limit\": 20,\n" +
                     "                        \"next\": null,\n" +
                     "                        \"offset\": 0,\n" +
                     "                        \"previous\": null,\n" +
                     "                        \"total_count\": 2\n" +
                     "            },\n" +
                     "                \"objects\": [\n" +
                     "                {\n" +
                     "                    \"attempt_count\": 1,\n" +
                     "                        \"description\": \"Пробный тест для проверки всех функций и возможностей программы.  \",\n" +
                     "                        \"id\": 1,\n" +
                     "                        \"last_modified\": \"2015-01-21T09:40:02.837750\",\n" +
                     "                        \"name\": \"Demo Test\",\n" +
                     "                        \"questions_count\": 2,\n" +
                     "                        \"resource_uri\": \"/api/v1/test/1/\",\n" +
                     "                        \"time\": 1\n" +
                     "                },\n" +
                     "                {\n" +
                     "                    \"attempt_count\": 1,\n" +
                     "                        \"description\": \"Тест для сравнения\",\n" +
                     "                        \"id\": 2,\n" +
                     "                        \"last_modified\": \"2015-01-21T20:00:35.891058\",\n" +
                     "                        \"name\": \"Beta Test\",\n" +
                     "                        \"questions_count\": 3,\n" +
                     "                        \"resource_uri\": \"/api/v1/test/2/\",\n" +
                     "                        \"time\": 0\n" +
                     "                }\n" +
                     "                ]\n" +
                     "            }";
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
            //String par = "http://tester.handh.ru/api/v1/question/?format=json&test__id=" + String.valueOf(id);
            questionJSON = //getdata(par);
                    "{\n" +
                            "\"meta\": {\n" +
                            "\"limit\": 20,\n" +
                            "\"next\": null,\n" +
                            "\"offset\": 0,\n" +
                            "\"previous\": null,\n" +
                            "\"total_count\": 2\n" +
                            "},\n" +
                            "\"objects\": [\n" +
                            "{\n" +
                            "\"id\": 2,\n" +
                            "\"img\": null,\n" +
                            "\"qtype\": 1,\n" +
                            "\"resource_uri\": \"/api/v1/question/2/\",\n" +
                            "\"test\": \"/api/v1/test/1/\",\n" +
                            "\"test_id\": \"1\",\n" +
                            "\"text\": \"Один байт содержит\"\n" +
                            "},\n" +
                            "{\n" +
                            "\"id\": 5,\n" +
                            "\"img\": null,\n" +
                            "\"qtype\": 0,\n" +
                            "\"resource_uri\": \"/api/v1/question/5/\",\n" +
                            "\"test\": \"/api/v1/test/1/\",\n" +
                            "\"test_id\": \"1\",\n" +
                            "\"text\": \"Что не относится к компьютерным комплектующим?\"\n" +
                            "}\n" +
                            "]\n" +
                            "}";
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
                            if (idQ == 2){
                                answersQ =
                                        "{\n" +
                                                "\"meta\": {\n" +
                                                "\"limit\": 20,\n" +
                                                "\"next\": null,\n" +
                                                "\"offset\": 0,\n" +
                                                "\"previous\": null,\n" +
                                                "\"total_count\": 4\n" +
                                                "},\n" +
                                                "\"objects\": [\n" +
                                                "{\n" +
                                                "\"id\": 1,\n" +
                                                "\"isRight\": false,\n" +
                                                "\"question\": \"/api/v1/question/2/\",\n" +
                                                "\"question_id\": \"2\",\n" +
                                                "\"resource_uri\": \"/api/v1/answer/1/\",\n" +
                                                "\"text\": \"1 бит\"\n" +
                                                "},\n" +
                                                "{\n" +
                                                "\"id\": 2,\n" +
                                                "\"isRight\": true,\n" +
                                                "\"question\": \"/api/v1/question/2/\",\n" +
                                                "\"question_id\": \"2\",\n" +
                                                "\"resource_uri\": \"/api/v1/answer/2/\",\n" +
                                                "\"text\": \"8 бит\"\n" +
                                                "},\n" +
                                                "{\n" +
                                                "\"id\": 3,\n" +
                                                "\"isRight\": false,\n" +
                                                "\"question\": \"/api/v1/question/2/\",\n" +
                                                "\"question_id\": \"2\",\n" +
                                                "\"resource_uri\": \"/api/v1/answer/3/\",\n" +
                                                "\"text\": \"8 Кбит\"\n" +
                                                "},\n" +
                                                "{\n" +
                                                "\"id\": 4,\n" +
                                                "\"isRight\": false,\n" +
                                                "\"question\": \"/api/v1/question/2/\",\n" +
                                                "\"question_id\": \"2\",\n" +
                                                "\"resource_uri\": \"/api/v1/answer/4/\",\n" +
                                                "\"text\": \"1 бод\"\n" +
                                                "}\n" +
                                                "]\n" +
                                                "}";
                            }
                            if (idQ == 5){
                                answersQ =
                                        "{\n" +
                                                "\"meta\": {\n" +
                                                "\"limit\": 20,\n" +
                                                "\"next\": null,\n" +
                                                "\"offset\": 0,\n" +
                                                "\"previous\": null,\n" +
                                                "\"total_count\": 4\n" +
                                                "},\n" +
                                                "\"objects\": [\n" +
                                                "{\n" +
                                                "\"id\": 13,\n" +
                                                "\"isRight\": false,\n" +
                                                "\"question\": \"/api/v1/question/5/\",\n" +
                                                "\"question_id\": \"5\",\n" +
                                                "\"resource_uri\": \"/api/v1/answer/13/\",\n" +
                                                "\"text\": \"Материнская плата\"\n" +
                                                "},\n" +
                                                "{\n" +
                                                "\"id\": 14,\n" +
                                                "\"isRight\": true,\n" +
                                                "\"question\": \"/api/v1/question/5/\",\n" +
                                                "\"question_id\": \"5\",\n" +
                                                "\"resource_uri\": \"/api/v1/answer/14/\",\n" +
                                                "\"text\": \"Пирожок\"\n" +
                                                "},\n" +
                                                "{\n" +
                                                "\"id\": 15,\n" +
                                                "\"isRight\": false,\n" +
                                                "\"question\": \"/api/v1/question/5/\",\n" +
                                                "\"question_id\": \"5\",\n" +
                                                "\"resource_uri\": \"/api/v1/answer/15/\",\n" +
                                                "\"text\": \"Жесткий диск\"\n" +
                                                "},\n" +
                                                "{\n" +
                                                "\"id\": 16,\n" +
                                                "\"isRight\": true,\n" +
                                                "\"question\": \"/api/v1/question/5/\",\n" +
                                                "\"question_id\": \"5\",\n" +
                                                "\"resource_uri\": \"/api/v1/answer/16/\",\n" +
                                                "\"text\": \"Бейсбольный мяч\"\n" +
                                                "}\n" +
                                                "]\n" +
                                                "}";
                            }
                            //answersQ = getdata("http://tester.handh.ru/api/v1/answer/?format=json&question__id=" + String.valueOf(idQ));
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


    /*private String getdata(String par){
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
    }*/


    // public Integer getTime(Test test){
    //    return test.time;
    // }
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
