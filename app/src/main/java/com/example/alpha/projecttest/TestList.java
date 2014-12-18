package com.example.alpha.projecttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alpha.projecttest.models.TestDescription;
import java.util.ArrayList;


public class TestList extends Activity {
    private ArrayList<TestDescription> tests;
    private TestListAdapter testListAdapter;
    AlertDialog.Builder ad;
    Context context;

    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
         /*FakeDataLoader l = new FakeDataLoader() {
            @Override
            void onLoad(Test test){
                Log.d("MyLogs", test.name);
                Log.d("MyLogs","123");
            }
        };*/
        Intent intent = getIntent();
        String login = intent.getStringExtra("login");
        String password = intent.getStringExtra("password");

        FakeDataLoader l = new FakeDataLoader();
        tests = l.loadListTests(login, password);

        testListAdapter = new TestListAdapter(this, tests);
        ListView lv = (ListView) findViewById(R.id.lvMain);
        lv.setAdapter(testListAdapter);
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


        bt = (Button) findViewById(R.id.start_test_button);
        context = TestList.this;
        String title = "Тестирование:";
        String message = "Желаете начать тест?";
        String button1String = "Да, начать";
        String button2String = "Нет, вернуться";
        /*bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
            }
        });*/
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
