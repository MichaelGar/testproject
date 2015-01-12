package com.example.alpha.projecttest;

import android.app.Activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.alpha.projecttest.models.Test;
import com.example.alpha.projecttest.models.Answer;
import com.example.alpha.projecttest.models.TestDescription;

import java.util.ArrayList;


public class QuestionActivity extends Activity {
    TextView QuView;
    Button answer;
    Integer ID;
    Test test;
    Integer max;//Будет отвечать за количество вопросов в тесте
    TextView maxView;//поле для вывода
    Integer idn;//Текущий вопрос
    TextView idnView;//поле для вывода
    ListView lvAnswer;
    public MyTask thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //-->Sozdaem massiv
        final ArrayList<Answer> Answerlist = new ArrayList<Answer>();
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



        lvAnswer = (ListView) findViewById(R.id.lvAnswer);
        // устанавливаем режим выбора пунктов списка
        if (1==1) {
            lvAnswer.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }
        // Создаем адаптер, используя массив из файла ресурсов
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,questtext);
        lvAnswer.setAdapter(adapter);

        maxView=(TextView)findViewById(R.id.maxView);
        idnView=(TextView)findViewById(R.id.idnView);
        max=5;//при переходе мы будем знать сколько ему присвоить;
        idn=Integer.valueOf(idnView.getText().toString());
        maxView.setText(""+max);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null) {
            QuView = (TextView) findViewById(R.id.questiontextView);
            QuView.setText(b.get("ID").toString());
        }
        answer=(Button) findViewById(R.id.otvet_button);
       /* Intent intent2 = getIntent();
        ID = intent2.getIntExtra("ID",0);
        String date = intent2.getStringExtra("date");

        RealDataLoader f = new RealDataLoader();
        test = f.loadTest(ID,date,this);*/
        createRequest();
        QuView = (TextView) findViewById(R.id.questiontextView);
       // QuView.setText(ID.toString());
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyLog", "checked: ");
                SparseBooleanArray sbArray = lvAnswer.getCheckedItemPositions();
                for (int i = 0; i < sbArray.size(); i++) {
                    int key = sbArray.keyAt(i);
                    if (sbArray.get(key))
                        Log.d("MyLog",""+key);
                }
                if (1==1){//здесь будет проверка выбран ли вариант ответа
                    //запись в базу результатов
                    if (idn==max){//Здесь проверка последний ли это вопрос
                        Intent intent3 = new Intent(QuestionActivity.this, ResultActivity.class);
                        //intent3.putExtra("key",values);
                        startActivity(intent3);
                    }
                    else{
                        idn=idn+1;
                        idnView.setText(""+idn);
                        //обновление данного активити,загрузка нового вопроса
                    }
                }
            }
        });

    }
    void createRequest() {
       // progressBar.setVisibility(View.VISIBLE);
        thread = (MyTask) getLastNonConfigurationInstance();
        if (thread == null) {
            Intent intent = getIntent();
            int id = intent.getIntExtra("ID",0);
            String date = intent.getStringExtra("date");
            String name = intent.getStringExtra("name");
            thread = new MyTask();
            thread.execute();
            thread.setIdDateContextName(id,date,this,name);
        }
        thread.link(this);
    }
    void readyTest(Test test){
       if (1 == 1){
           test = null;

       }

    }

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

    class MyTask extends AsyncTask<Void, Void, Integer> {
        QuestionActivity activity;
        int id;
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
        void setIdDateContextName(int idX, String dateX, Context contextX,String nameX){
          id = idX;
          date = dateX;
          context = contextX;
            name = nameX;
        }
        @Override
        protected Integer doInBackground(Void... params) {
            //FakeDataLoader l = new FakeDataLoader();
            RealDataLoader l = new RealDataLoader();
            testAsync = l.loadTest(id,date,context,name);
            return 100500;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            activity.readyTest(testAsync);
        }
    }
}
