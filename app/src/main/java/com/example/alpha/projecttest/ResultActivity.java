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

public class ResultActivity extends Activity {
    Button repeat, back;
    ProcessTest prc;
    TextView indreztv,maxreztv,timetv,testname,zatrachoptv,reztv;
    ImageView medalImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        MyApp app = ((MyApp) getApplicationContext());
        prc = app.prc;
        indreztv = (TextView) findViewById(R.id.idnrezView);
        maxreztv = (TextView) findViewById(R.id.maxrezView);
        timetv = (TextView) findViewById(R.id.textView7);
        testname = (TextView) findViewById(R.id.testName);
        zatrachoptv = (TextView) findViewById(R.id.tvZatrachopis);
        reztv = (TextView) findViewById(R.id.resultViev);
        repeat=(Button)findViewById(R.id.repeat);
        back=(Button)findViewById(R.id.back);
        medalImage = (ImageView) findViewById(R.id.imageView3);

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentrepeat = new Intent(ResultActivity.this, QuestionActivity.class);
                startActivity(intentrepeat);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentback = new Intent(ResultActivity.this, TestList.class);
                startActivity(intentback);
                finish();
            }
        });
        prc.getResult(this);
    }

    public void showResult(String name, int maxrez, int rez, Boolean mode, long min, long sec, String mark){
        indreztv.setText("" + rez);
        maxreztv.setText("" + maxrez);
        testname.setText(name);
        reztv.setText("Тест НЕ сдан");
        reztv.setTextColor(Color.RED);

        if (mark != null){
            reztv.setText("ТЕСТ СДАН!!!");
            reztv.setTextColor(Color.GREEN);
            if (mark == "bronze") {
                medalImage.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.bronze));
            }
            if (mark == "silver") {
                medalImage.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.silver));
            }
            if (mark == "gold") {
                medalImage.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.gold));
            }
        }

        if (mode) {
            timetv.setText("" + min + " мин. " + sec + " сек.");
        }
        else {
            zatrachoptv.setText("");
        }
    }
    @Override
    public void onBackPressed(){
        Intent intentontestlist = new Intent(ResultActivity.this, TestList.class);
        startActivity(intentontestlist);
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
