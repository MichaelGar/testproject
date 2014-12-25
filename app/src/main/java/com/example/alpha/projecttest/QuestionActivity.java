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


public class QuestionActivity extends Activity {
    TextView QuView;
    Button answer;
    Long ID;
    FragmentTransaction Fragrazm;
    MultipleChoiseFragment multiChoiseFr;
    Integer max;//Будет отвечать за количество вопросов в тесте
    TextView maxView;//поле для вывода
    Integer idn;//Текущий вопрос
    TextView idnView;//поле для вывода
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

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
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
