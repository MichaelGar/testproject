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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.alpha.projecttest.models.TestDescription;
import java.util.ArrayList;

public class TestList extends Activity {
    private ArrayList<TestDescription> tests;
    private TestListAdapter testListAdapter;
    public Handler h;
    public AsyncTask thread;
    ListView lv;
    ProgressBar progressBar;
    AlertDialog.Builder ad;
    Context context;

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
        createHandler();
        createRequest();
        lv = (ListView) findViewById(R.id.lvMain);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MyLogs", ""+position);
                TestDescription obj = tests.get(position);
                Log.d("MyLogs", "idTest"+obj.id);
              //  FakeDataLoader f = new FakeDataLoader();
              //  Test test = f.loadTest(121);
              //  Log.d("MyLogs",test.name);
            }
        });


        context = TestList.this;
        String title = "Тест:";
        String message = "Желаете начать тест?";
        String button1String = "Да, начать";
        String button2String = "Нет, вернуться";
        ad = new AlertDialog.Builder(context);
        ad.setTitle(title);  // заголовок
        ad.setMessage(message); // сообщение
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(context, "Начало теста",
                        Toast.LENGTH_LONG).show();
            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(context, "Возврат", Toast.LENGTH_LONG)
                        .show();
            }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context, "Мимо",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    void createRequest(){
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        final String login = intent.getStringExtra("login");
        final String password = intent.getStringExtra("password");
        thread = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                FakeDataLoader l = new FakeDataLoader();
                tests = l.loadListTests(login, password);
                h.sendEmptyMessage(0);
                return null;
            }
        };
        thread.execute();
    }

    void readyList(){
        testListAdapter = new TestListAdapter(this, tests);
        lv.setAdapter(testListAdapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    void createHandler() {
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                    // обновляем TextView
                    //tvInfo.setText("Закачано файлов: " + msg.what);
                    //  if (msg.what == 10) btnStart.setEnabled(true);
                readyList();
            }
        };
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
}
