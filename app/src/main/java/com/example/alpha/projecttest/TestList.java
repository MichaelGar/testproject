package com.example.alpha.projecttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.alpha.projecttest.models.Test;
import com.example.alpha.projecttest.models.TestDescription;
import java.util.ArrayList;
import java.util.Objects;

public class TestList extends Activity {
    private ArrayList<TestDescription> tests;
    private TestListAdapter testListAdapter;
    public Handler h;
    public MyTask thread;
    ListView lv;
    ProgressBar progressBar;
    AlertDialog.Builder ad;
    Context context;
    Intent intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
           progressBar = (ProgressBar) findViewById(R.id.progressBar);
         /*FakeDataLoader l = new FakeDataLoader() {
            @Override
            void onLoad(Test test){
                Log.d("MyLogs", test.name);
                Log.d("MyLogs","123");
            }
        };*/
        createRequest();
        lv = (ListView) findViewById(R.id.lvMain);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestDescription obj;
                obj = tests.get(position);
                //Формируем диалог(начало)
                context = TestList.this;
                final int idX = obj.id;
                final String nameX = obj.name;
                final String date = obj.last_modified;
                final int timeX = obj.time;
                String title = nameX;
                String message = "Желаете начать тест?";
                String button1String = "Да, начать";
                String button2String = "Нет, вернуться";
                ad = new AlertDialog.Builder(context);
                ad.setTitle(title);  // заголовок
                ad.setMessage(message); // сообщение
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Intent intent2 = new Intent(TestList.this, QuestionActivity.class);
                        intent2.putExtra("ID",idX);
                        intent2.putExtra("date",date);
                        intent2.putExtra("name",nameX);
                        intent2.putExtra("time",timeX);
                        startActivity(intent2);
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
                ad.setCancelable(true);
                ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                    }
                });
                //(конец)

                ad.show();//Вызов диалога
            }
        });

        

    }

    void createRequest() {
        progressBar.setVisibility(View.VISIBLE);
        thread = (MyTask) getLastNonConfigurationInstance();
        if (thread == null) {
            Intent intent = getIntent();
            String login = intent.getStringExtra("login");
            String password = intent.getStringExtra("password");
            thread = new MyTask();
            thread.execute();
            thread.setLoginPassword(login,password);
        }
        thread.link(this);
    }

    public void readyList(ArrayList<TestDescription> testsAsync){
        tests = testsAsync;
        testListAdapter = new TestListAdapter(this, tests);
        lv.setAdapter(testListAdapter);
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_list, menu);
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
    public Object onRetainNonConfigurationInstance() {
        thread.unLink();
        return thread;
    }

    class MyTask extends AsyncTask<Void, Void, Integer> {
        TestList activity;
        String login, password;
        ArrayList<TestDescription> testAsync;
        void unLink() {
            activity = null;
        }

        void link(TestList act){
            activity = act;
        }
        void setLoginPassword(String log, String pas){
            login = log;
            password = pas;
        }
        @Override
        protected Integer doInBackground(Void... params) {
            //FakeDataLoader l = new FakeDataLoader();
            RealDataLoader l = new RealDataLoader();
            testAsync = l.loadListTests(login,password);
            return 100500;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            activity.readyList(testAsync);
        }
    }
}
