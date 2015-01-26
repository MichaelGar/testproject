package com.example.alpha.projecttest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ResultActivity extends Activity implements ResultActivityInterface{
    ProcessTest prc;
    @ViewById Button repeat, back;
    @ViewById TextView idnrezView, maxrezView, timeView, testName, tvZatrachopis,
                       resultView;
    @ViewById ImageView medalIView;
    @Click
    void repeat() {
        prc.clean();
        QuestionActivity_.intent(this).start();
        finish();
    }
    @Click
    void back() {
        prc.clean();
        TestList_.intent(this).start();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        MyApp app = ((MyApp) getApplicationContext());
        prc = app.prc;
        prc.getResult(this);
    }

    public void showResult(String name, int maxrez, int rez, Boolean mode, long min, long sec, String mark){
        idnrezView.setText("" + rez);
        maxrezView.setText("" + maxrez);
        testName.setText(name);
        resultView.setText("Тест НЕ сдан");
        resultView.setTextColor(Color.RED);

        if (mark != null){
            resultView.setText("ТЕСТ СДАН!!!");
            resultView.setTextColor(Color.GREEN);
            if (mark == "bronze") {
                medalIView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.bronze));
            }
            if (mark == "silver") {
                medalIView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.silver));
            }
            if (mark == "gold") {
                medalIView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.gold));
            }
        }

        if (mode) {
            timeView.setText("" + min + " мин. " + sec + " сек.");
        }
        else {
            tvZatrachopis.setText("");
        }
    }
    @Override
    public void onBackPressed(){
        prc.clean();
        TestList_.intent(this).start();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
