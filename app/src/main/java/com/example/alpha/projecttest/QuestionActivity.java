package com.example.alpha.projecttest;

import android.app.Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alpha.projecttest.fragments.MultipleChoiseFragment;


public class QuestionActivity extends Activity {
    TextView QuView;
    Integer ID;
    FragmentTransaction Fragrazm;
    MultipleChoiseFragment multiChoiseFr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent intent2 = getIntent();
        ID = intent2.getIntExtra("ID",0);
        QuView = (TextView) findViewById(R.id.questiontextView);
        QuView.setText(ID.toString());

        multiChoiseFr = new MultipleChoiseFragment();
        Fragrazm = getFragmentManager().beginTransaction();
        Fragrazm.add(R.id.fragotv, multiChoiseFr);

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
