package com.example.alpha.projecttest;

import com.example.alpha.projecttest.models.Answer;
import com.example.alpha.projecttest.models.Question;
import com.example.alpha.projecttest.models.Test;
import com.example.alpha.projecttest.models.TestHeader;

import android.os.CountDownTimer;
import android.util.SparseBooleanArray;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 1 on 12.01.2015.
 */
//TODO:Сделать ФИНИШ NAHOOY
public class ProcessTest {
    private static final int MILLIS_PER_SECOND = 1000;
    RealDataLoader rdl;
    ArrayList<TestHeader> listTestsHeader;
    Test test;
    TestList testList;
    QuestionActivity questionActivity;
    Thread thread1,thread2;
    public int position;
    CountDownTimer timer;
    long min,sec;
    Boolean mode;

    public void getListTests(TestList testListX) {
        testList = testListX;
        if (thread1 == null) {
            thread1 = new Thread(new Runnable() {
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
            thread1.start();
        }
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
                    //finishTest();
                    questionActivity.showError("ТЕСТ ЗАКОНЧЕН!!!");
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
            TestHeader testHeader = listTestsHeader.get(position);
            int id = testHeader.id;
            String last_modified = testHeader.last_modified;
            //  String name = testHeader.name;
          //  int minutes = testHeader.time;
            test = rdl.loadTest(id,last_modified,questionActivity);
            test.name = testHeader.name;
            test.max = testHeader.max;
            test.time = testHeader.time;
            test.count = 0;
            test.grades = 0;
            test.questions_count=testHeader.questions_count;
            test = randomTest(test);

        }
    }

    public void getQuestion(QuestionActivity questionActivityX){
        questionActivity = questionActivityX;
        if (thread2 == null) {
            thread2 = new Thread(new Runnable() {
                public void run() {
                    getTest();
                    final Question question = test.questions.get(test.count);
                    questionActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //тут делаем какие то взаимодействия с интерфейсом
                            if (test.count==0){
                                startTimer(test.time*MILLIS_PER_SECOND*60);
                            }
                            questionActivity.showQuestion(question,test.count, test.max);
                        }
                    });
                }
            });
            thread2.start();
        }

    }

    private Test randomTest(Test test){
        ArrayList<Question> questions = test.questions;
        Random rnd = new Random();
        for (int i = questions.size() - 1; i >= 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Question question = questions.get(index);
            question = randomQuestion(question);
            questions.set(index, questions.get(i));
            // ar[index] = ar[i];
            questions.set(i, question);

            // ar[i] = a;
        }
        test.questions = questions;
        return test;
    }

    public void setAnswer(SparseBooleanArray sbArray){
        thread2 = null;
        int zero = 0;
        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key))
                zero=zero+1;
        }
        if (zero > 0) {
            if (test.count<test.questions_count-1) {
                test.grades = test.grades + getGrade(sbArray);
                test.count = test.count + 1;
                getQuestion(questionActivity);
            }
            else{
                //finishTest();
                questionActivity.showError("ТЕСТ ЗАКОНЧЕН!!!");
            }
        }
        else{
            questionActivity.showError("Не выбрано ни одного варианта ответа");
        }
    }

    private Question randomQuestion(Question question){
        ArrayList<Answer> answers = question.answers;
        Random rnd = new Random();
        for (int i = answers.size() - 1; i >= 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Answer answer = answers.get(index);
            //answer = randomQuestion(question);
            answers.set(index, answers.get(i));
            // ar[index] = ar[i];
            answers.set(i, answer);

            // ar[i] = a;
        }
        question.answers = answers;
        return question;
    }

    public Question getQuestion(Test test, int rez){
       ArrayList<Question> questions = test.questions;
       int all = questions.size();
       if (test.count == all) {
           return null;
       } else {
       return questions.get(test.count);}
   }

    public Integer getType(Test test){
        ArrayList<Question> questions = test.questions;
        return questions.get(test.count).qtype;
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
