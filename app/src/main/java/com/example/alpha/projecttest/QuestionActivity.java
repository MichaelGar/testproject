package com.example.alpha.projecttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.alpha.projecttest.models.Question;
import java.util.ArrayList;

public class QuestionActivity extends Activity {
    TextView QuView;
    Button answer;
    TextView maxView;
    TextView idnView;
    ListView lvAnswer;
    TextView tvtime, tvopis;
    int rez;
    ProcessTest prc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        QuView = (TextView) findViewById(R.id.questiontextView);
        maxView=(TextView)findViewById(R.id.maxView);
        idnView=(TextView)findViewById(R.id.idnView);
        lvAnswer = (ListView) findViewById(R.id.lvAnswer);
        tvtime = (TextView) findViewById(R.id.timecounttext);
        tvopis = (TextView) findViewById(R.id.tvOpistime);
        rez = 0;

        MyApp app = ((MyApp) getApplicationContext());
        prc = app.prc;
        prc.getQuestion(this);

        answer=(Button) findViewById(R.id.otvet_button);
        QuView = (TextView) findViewById(R.id.questiontextView);
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prc.setAnswer(lvAnswer.getCheckedItemPositions());
            }
        });

    }

    public void showTimer(boolean mode, long min, long sec) {
        if (mode==true) {
            tvtime.setText("" + min + " мин. " + sec + " сек.");
        }
        else{
            tvopis.setText("Время неограничено");
        }
    }

    public void showMSG(String str){
        Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void goResult() {
        Intent intent3 = new Intent(QuestionActivity.this, ResultActivity.class);
        startActivity(intent3);
        finish();
    }

    public void showQuestion(Question question,int count,int max){
        QuView.setText(question.textQuestion);
        ArrayList<String> answersText = prc.getAnswers(question);
        if (question.qtype == 0){
            lvAnswer.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,answersText);
            lvAnswer.setAdapter(adapter);
        }
        else {
            lvAnswer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, answersText);
            lvAnswer.setAdapter(adapter);
        }
        maxView.setText("" + max);
        idnView.setText("" + count);
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