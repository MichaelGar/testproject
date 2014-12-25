package com.example.alpha.projecttest;

import android.app.Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alpha.projecttest.fragments.MultipleChoiseFragment;
import com.example.alpha.projecttest.models.Test;


public class QuestionActivity extends Activity {
    TextView QuView;
    Button answer;
    Integer ID;
    FragmentTransaction Fragrazm;
    MultipleChoiseFragment multiChoiseFr;
    Test test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        answer=(Button) findViewById(R.id.otvet_button);
        Intent intent2 = getIntent();
        ID = intent2.getIntExtra("ID",0);
        String date = intent2.getStringExtra("date");
        FakeDataLoader f = new FakeDataLoader();
        test = f.loadTest(ID,date,this);

        QuView = (TextView) findViewById(R.id.questiontextView);
        QuView.setText(ID.toString());
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1==1){//здесь будет проверка выбран ли вариант ответа
                    //запись в базу результатов
                    if (2==2){//Здесь проверка последний ли это вопрос
                        //вызов активити с результатами, intent
                    }
                    else{
                        //обновление данного активити,загрузка нового вопроса
                    }
                }
            }
        });

        multiChoiseFr = new MultipleChoiseFragment();
        Fragrazm = getFragmentManager().beginTransaction();
        Fragrazm.add(R.id.fragotv, multiChoiseFr);
        Fragrazm.commit();

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


}
