package com.example.alpha.projecttest;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alpha.projecttest.models.ContainerForQuestionActivity;
import com.example.alpha.projecttest.models.Question;
import com.example.alpha.projecttest.models.Test;

import java.util.ArrayList;

//TODO: сделать програссбар пока грузится
public class QuestionActivity extends Activity {
    TextView QuView;
    Button answer;
    Test test;
    TextView maxView;//поле для вывода
    TextView idnView;//поле для вывода
    ListView lvAnswer;
    TextView tvtime, tvopis;
    int zero, time; //zero для проверки выбора ответа
    int rez;
   // public MyTask thread;
    ProcessTest prc;
    long min, sec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        QuView = (TextView) findViewById(R.id.questiontextView);
        maxView=(TextView)findViewById(R.id.maxView);
        idnView=(TextView)findViewById(R.id.idnView);
        lvAnswer = (ListView) findViewById(R.id.lvAnswer);
        tvtime = (TextView) findViewById(R.id.timecounttext);
        tvopis = (TextView) findViewById(R.id.tvOpistime);
        //lvAnswer.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);//??
      /*
        lvAnswer.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
*/

        //count = 0;
        rez = 0;

        MyApp app = ((MyApp) getApplicationContext());
        prc = app.prc;
        prc.getQuestion(this);

/*        ContainerForQuestionActivity container = (ContainerForQuestionActivity) getLastNonConfigurationInstance();
        if (container != null) {
            test = container.test;
            //count = container.count;
            rez = container.rez;
            if ( test != null){
                readyTest(test);
            }
        }
        prT = new ProcessTest();*/
        //-->Sozdaem massiv
   /*     final ArrayList<Answer> Answerlist = new ArrayList<Answer>();
        ArrayList<String> questtext = new ArrayList<String>();
        final Answer answertest = new Answer();
        int i;
        for (i = 1; i<=4; i++){

            answertest.ID = i;
            //answertest.IdQuestion = 122;
            answertest.text = "blablabla" + i;
            answertest.isRight = true;
            Answerlist.add(answertest);
            questtext.add(answertest.text);
        }
*/



        // устанавливаем режим выбора пунктов списка
    /*    if (1==1) {
            lvAnswer.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }*/
        // Создаем адаптер, используя массив из файла ресурсов
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,questtext);
       // lvAnswer.setAdapter(adapter);


       //max=test.questions.size(); //при переходе мы будем знать сколько ему присвоить;
       //idn=Integer.valueOf(idnView.getText().toString());
       //maxView.setText(""+max);

        //Intent iin= getIntent();
        //Bundle b = iin.getExtras();

        //if(b!=null) {
        //    QuView.setText(b.get("ID").toString());
        //}
        answer=(Button) findViewById(R.id.otvet_button);
       /* Intent intent2 = getIntent();
        ID = intent2.getIntExtra("ID",0);
        String date = intent2.getStringExtra("date");

        RealDataLoader f = new RealDataLoader();
        test = f.loadTest(ID,date,this);*/
       // createRequest();
        QuView = (TextView) findViewById(R.id.questiontextView);
        Context context = this;
       // QuView.setText(ID.toString());
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prc.setAnswer(lvAnswer.getCheckedItemPositions());
              /*  zero=0;
                SparseBooleanArray sbArray = lvAnswer.getCheckedItemPositions();
                for (int i = 0; i < sbArray.size(); i++) {
                    int key = sbArray.keyAt(i);
                    if (sbArray.get(key))
                    zero=zero+1;
                }
                if (zero!=0){//проверка выбран ли вариант ответа
                    Question question = test.questions.get(test.count);
                    test.grades = test.grades + prc.getGrade(question,sbArray);

                    if (test.count==(4-1)) {//Здесь проверка последний ли это вопрос
                        finishTest();
                    }

                    else{
                        test.count=test.count+1;
                        idnView.setText("" + test.count);
                        //showQuestion();
                        //обновление данного активити,загрузка нового вопроса
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Не выбран вариант ответа", Toast.LENGTH_SHORT);
                    toast.show();
                }*/
            }
        });

    }

    public void showTimer(long min, long sec) {
         tvtime.setText(""+min+" мин. "+sec+" сек.");
    }

    public void showError(){
        Toast toast = Toast.makeText(getApplicationContext(), "Не выбран вариант ответа", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showQuestion(Question question,int count,int max){
        QuView.setText(question.textQuestion);
        ArrayList<String> answersText = prc.getAnswers(question);
        if (question.qtype == 0){
            lvAnswer.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,answersText);
            lvAnswer.setAdapter(adapter);
        }
        else {
            lvAnswer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, answersText);
            lvAnswer.setAdapter(adapter);
        }
        maxView.setText("" + max);
        idnView.setText("" + count);
    }

  /* void createRequest() {
       // progressBar.setVisibility(View.VISIBLE);
        ContainerForQuestionActivity container = (ContainerForQuestionActivity) getLastNonConfigurationInstance();
        if (container != null){
        thread = (MyTask) container.myTask;}
        if (thread == null) {
            Intent intent = getIntent();
            int id = intent.getIntExtra("ID",0);
            String date = intent.getStringExtra("date");
            String name = intent.getStringExtra("name");
            int minutes = intent.getIntExtra("time", 0);
            thread = new MyTask();
            thread.execute();
            thread.setIdDateContextName(id,date,this,name,minutes);
        }
        thread.link(this);
    }*/
 /*   void readyTest(Test testX){
        test = testX;
        if (test == null){
            thread = null;
            createRequest();//по идее нужно выдавать сообщение что загрузка не удалась, а не повторять как щас
        } else {
            time = prT.getTime(test);
            if (time == 0) {//запускать или не запускать таймер если не ограничено время
                tvtime.setText("");
                tvopis.setText("Время не ограничено");
            } else {
                tvtime.setText(""+time);
                try {
                    showTimer(time*MILLIS_PER_SECOND*60);
                } catch (NumberFormatException e){
                    Log.d("TimerLog", "Облом");
                }
            }
            showQuestion();
        }
    }
*/


    //Округление

/*
    void showQuestionn(Question question){
        //Question question = prT.getQuestion(test,rez);
        QuView.setText(question.textQuestion);
        ArrayList<String> answersText = prT.getAnswers(question);
        if (prT.getType(test)==0){
            lvAnswer.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,answersText);
            lvAnswer.setAdapter(adapter);
        }
        else {
            lvAnswer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, answersText);
            lvAnswer.setAdapter(adapter);
        }
        maxView.setText("" + test.questions.size());
        max=test.questions.size();
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    public Object onRetainNonConfigurationInstance() {
        ContainerForQuestionActivity container = new ContainerForQuestionActivity();
        container.myTask = thread;
        container.test = test;
        //container.count = count;
        container.rez = rez;
        thread.unLink();
        return container;
    }*/
    void finishTest() {
        //TODO: уничтожить таймер а то два раза срабатывает
        test = prc.finishTest(test);
        Intent intent3 = new Intent(QuestionActivity.this, ResultActivity.class);
        intent3.putExtra("grades", test.grades);
        intent3.putExtra("max", test.max);
        intent3.putExtra("id", test.id);
        intent3.putExtra("name", test.name);
        intent3.putExtra("alltime",test.time);

        long facttime = min * 60 + sec;
        facttime = time * 60 - facttime;
        intent3.putExtra("time", facttime);
        startActivity(intent3);
    }

/*

    class MyTask extends AsyncTask<Void, Void, Integer> {
        QuestionActivity activity;
        int id, time;
        String date;
        Context context;
        String name;
       // ArrayList<TestDescription> testAsync;
        Test testAsync;
        void unLink() {
            activity = null;
        }

        void link(QuestionActivity act){
            activity = act;
        }
        void setIdDateContextName(int idX, String dateX, Context contextX,String nameX,int timeX){
          id = idX;
          date = dateX;
          context = contextX;
          name = nameX;
          time = timeX;
        }
        @Override
        protected Integer doInBackground(Void... params) {
            //FakeDataLoader l = new FakeDataLoader();
            RealDataLoader l = new RealDataLoader();
            testAsync = l.loadTest(id,date,context,name,time);
            return 100500;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            activity.readyTest(testAsync);
        }
    }*/
}
