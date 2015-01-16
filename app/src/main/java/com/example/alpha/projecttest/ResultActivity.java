package com.example.alpha.projecttest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//TODO: продумать сохранение при повороте и подшаманить экран
public class ResultActivity extends Activity {
Button repeat, back;
    TextView indreztv,maxreztv,timetv,testname,zatrachoptv,reztv;
    int indrez,maxrez,id, alltime;
    long time;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        indreztv = (TextView) findViewById(R.id.idnrezView);
        maxreztv = (TextView) findViewById(R.id.maxrezView);
        timetv = (TextView) findViewById(R.id.textView7);
        testname = (TextView) findViewById(R.id.testName);
        zatrachoptv = (TextView) findViewById(R.id.tvZatrachopis);
        reztv = (TextView) findViewById(R.id.resultViev);

        if (time == 0){//убирает текствьюхи если время 0
            timetv.setVisibility(View.GONE);
            zatrachoptv.setVisibility(View.GONE);
        }
        else{
            timetv.setVisibility(View.VISIBLE);
            zatrachoptv.setVisibility(View.VISIBLE);
        }

        repeat=(Button)findViewById(R.id.repeat);
        back=(Button)findViewById(R.id.back);
        Intent rez = getIntent();
        id = rez.getIntExtra("id",0);
        indrez = rez.getIntExtra("grades",0);
        alltime = rez.getIntExtra("alltime",0);
        maxrez = rez.getIntExtra("max",0);
        time = rez.getLongExtra("time",0);
        name = rez.getStringExtra("name");
        long min = time / 60;
        long sec = time % 60;
        String timestr = String.valueOf(min) + ":" + String.valueOf(sec);
        indreztv.setText("" + indrez);
        maxreztv.setText("" + maxrez);
        if(indrez/maxrez>0.66) {
            reztv.setText("ТЕСТ СДАН!!!");
            reztv.setTextColor(Color.GREEN);
        }
        else{
            reztv.setText("Тест НЕ сдан!!!");
            reztv.setTextColor(Color.RED);
        }
        timetv.setText(timestr);

        testname.setText("Teст "+name);
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentrepeat = new Intent(ResultActivity.this, QuestionActivity.class);
                //intentrepeat.putExtra("ID",lng);
                intentrepeat.putExtra("ID",id);
                intentrepeat.putExtra("date", 0);
                intentrepeat.putExtra("name",name);
                intentrepeat.putExtra("time",alltime);
                startActivity(intentrepeat);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentback = new Intent(ResultActivity.this, TestList.class);
                //intentback.putExtra("login",((EditText) findViewById(R.id.editTextName)).getText().toString());
                //intentback.putExtra("password",((EditText) findViewById(R.id.editTextPswd)).getText().toString());
                startActivity(intentback);
            }
        });
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
