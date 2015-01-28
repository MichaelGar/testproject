package com.example.alpha.projecttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.alpha.projecttest.models.Test;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_test_list)
public class TestList extends Activity implements TestListInterface {
    private ArrayList<Test> tests;
    ProgressBar progressBar;
    AlertDialog.Builder ad;
    ProcessTest prc;

    @ViewById ListView lvMain;

    @Bean
    TestListAdapter adapter;

    @AfterViews
    void bindAdapter() {
        lvMain.setAdapter(adapter);
    }

    @ItemClick
    void lvMainItemClicked(Test selectedTest) {
        final int position = selectedTest.id-1;
        String title = selectedTest.name;
        String message = "Желаете начать тест?";
        String button1String = "Да, начать";
        String button2String = "Нет, вернуться";
        ad = new AlertDialog.Builder(TestList.this);
        ad.setTitle(title);  // заголовок
        ad.setMessage(message); // сообщение
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                prc.position = position;
                QuestionActivity_.intent(TestList.this).start();
                finish();
            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1){}
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {}
        });
        ad.show();//Вызов диалога
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        MyApp app = ((MyApp) getApplicationContext());
        prc = app.prc;
        prc.getListTests(this);// сделали запрос на список тестов
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void setListTests(ArrayList<Test> testsX){
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
}
