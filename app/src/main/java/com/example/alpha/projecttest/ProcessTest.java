package com.example.alpha.projecttest;

import com.example.alpha.projecttest.models.Answer;
import com.example.alpha.projecttest.models.Question;
import com.example.alpha.projecttest.models.Test;
import android.os.CountDownTimer;
import android.util.SparseBooleanArray;
import java.util.ArrayList;
import java.util.Random;

public class ProcessTest {
    private static final int MILLIS_PER_SECOND = 1000;
    private static final double bronze = 0.6, silver = 0.8, gold = 90;
    RealDataLoader rdl;
    ArrayList<Test> listTestsHeader;
    Test test;
    TestList testList;
    QuestionActivity questionActivity;
    Thread threadListtest, threadGetquestion;
    public int position;
    CountDownTimer timer;
    long min,sec;
    Boolean mode;
    String mark;
    String imagelink;


    public void getListTests(TestList testListX) {
        threadListtest =null;
        testList = testListX;
        if (threadListtest == null) {
            threadListtest = new Thread(new Runnable() {
                public void run() {
                    if (listTestsHeader == null) {
                        //загрузка
                        listTestsHeader = rdl.loadListTests("","");
                    }
                    testList.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //тут делаем какие то взаимодействия с интерфейсом
                            testList.setListTests(listTestsHeader);
                        }
                    });
                }
            });
            threadListtest.start();
        }
    }

    public void getResult(ResultActivity resultActivity){
       double markdouble = (((double) test.grades)/( (double) test.max));
        if (markdouble >= 0.6){
            mark = "bronze";
        }
        if (markdouble >= 0.8) {
            mark = "silver";
        }
        if (markdouble >= 0.9) {
            mark = "gold";
        }

        if (sec == 0) {
            min = test.time - min;
        }
        else {
            min = test.time - min-1;
            sec = 60 - sec;
        }
        resultActivity.showResult(test.name, test.max, test.grades, mode, min, sec, mark);
        test = null;
    }

    private void startTimer(int countdownMillis) {
        if (countdownMillis!=0) {
            mode = true;
            if (timer != null) {
                timer.cancel();
            }
            timer = new CountDownTimer(countdownMillis, MILLIS_PER_SECOND) {
                @Override
                public void onTick(long millisUntilFinished) {
                    min = (millisUntilFinished - (millisUntilFinished % 60000)) / 60000;
                    sec = (millisUntilFinished - 60000 * min) / 1000;
                    questionActivity.showTimer(mode,min,sec);
                }

                @Override
                public void onFinish() {
                    sec = 0;
                    finishTest(test);
                    questionActivity.goResult();
                }
            }.start();
        }
        else{
            mode=false;
            questionActivity.showTimer(mode,0,0);
        }
    }

    private void getTest(){
        if (test == null) {
            Test test = listTestsHeader.get(position);
            int id = test.id;
            String last_modified = test.last_modified;
            this.test = rdl.loadTest(id,last_modified,questionActivity);
            this.test.name = test.name;
            this.test.max = test.max;
            this.test.time = test.time;
            this.test.count = 0;
            this.test.grades = 0;
            this.test.questions_count= test.questions_count;
            this.test = randomTest(this.test);

        }
    }

    public void getQuestion(QuestionActivity questionActivityX){
        questionActivity = questionActivityX;
        threadGetquestion = null;
        imagelink = "null";
        if (threadGetquestion == null) {
            threadGetquestion = new Thread(new Runnable() {
                public void run() {
                    getTest();
                    final Question question = test.questions.get(test.count);
                    questionActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //тут делаем какие то взаимодействия с интерфейсом
                            if (test.onTimer == false){
                                test.onTimer = true;
                                startTimer(test.time*MILLIS_PER_SECOND*60);
                            }
                            //TODO: заменить blablabla
                            if (question.image != "null") {
                                imagelink = "blablabla" + question.image;
                                imagelink = "http://www.setevic1.ru/chipset.jpg";
                            }
                            //убрать когда будет готово
                            questionActivity.showQuestion(question,test.count, test.questions_count, imagelink);
                        }
                    });
                }
            });
            threadGetquestion.start();
        }

    }

    private Test randomTest(Test test){
        ArrayList<Question> questions = test.questions;
        Random rnd = new Random();
        for (int i = questions.size() - 1; i >= 0; i--) {
            int index = rnd.nextInt(i + 1);
            Question question = questions.get(index);
            question = randomQuestion(question);
            questions.set(index, questions.get(i));
            questions.set(i, question);
        }
        test.questions = questions;
        return test;
    }

    public void setAnswer(SparseBooleanArray sbArray){
        threadGetquestion = null;
        int zero = 0;
        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key))
                zero=zero+1;
        }
        if (zero > 0) {
            test.grades = test.grades + getGrade(sbArray);
            if (test.count<test.questions_count-1) {
                test.count = test.count + 1;
                getQuestion(questionActivity);
            }
            else{
                finishTest(test);
                questionActivity.goResult();
            }
        }
        else{
            questionActivity.showMSG("Не выбрано ни одного варианта ответа");
        }
    }

    private Question randomQuestion(Question question){
        ArrayList<Answer> answers = question.answers;
        Random rnd = new Random();
        for (int i = answers.size() - 1; i >= 0; i--) {
            int index = rnd.nextInt(i + 1);
            Answer answer = answers.get(index);
            answers.set(index, answers.get(i));
            answers.set(i, answer);
        }
        question.answers = answers;
        return question;
    }


    public ArrayList<String> getAnswers(Question question){
        ArrayList<Answer> answersList= question.answers;
        ArrayList<String> answers = new ArrayList<>();
        for (int i = 0; i < answersList.size(); i++){
            Answer answer = answersList.get(i);
            answers.add(answer.text);
        }
        return answers;
    }

        private int getGrade(SparseBooleanArray sbArray){
        int grades = 0;
        ArrayList<Answer> answers = test.questions.get(test.count).answers;
        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key)){
                Answer answer = answers.get(key);
                if (answer.isRight){
                    grades++;
                }else {
                    grades--;
                }
            }
        }
        if(grades < 0){
            grades = 0;
        }
        return grades;
    }

   public Test finishTest(Test test){
        timer.cancel();
        int max = 0;
        for (int i = 0; i < test.questions.size(); i++){
            Question question = test.questions.get(i);
            for (int j = 0; j < question.answers.size(); j++){
                Answer answer = question.answers.get(j);
                if (answer.isRight){
                max++;
                }
            }
        }
    test.max = max;
    return test;
   }
}