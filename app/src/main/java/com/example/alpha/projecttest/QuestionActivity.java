package com.example.alpha.projecttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.alpha.projecttest.models.Question;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.net.URI;
import java.util.ArrayList;

public class QuestionActivity extends Activity {
    TextView QuView, tvtime, tvopis, maxView, idnView;
    Button answer;
    ListView lvAnswer;
    ImageView qimage;
    AlertDialog.Builder ad;
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
        qimage = (ImageView) findViewById(R.id.imageView2);
        //rez = 0;

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

    public void showQuestion(Question question,int count,int max, String imagelink){
        qimage.setImageBitmap(null);
        if (imagelink != "null") {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
            ImageLoader il = ImageLoader.getInstance();
            il.init(config);
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .resetViewBeforeLoading(true)
                    .cacheOnDisk(true)
                    .build();
            il.displayImage(imagelink, qimage, options);
        }
        QuView.setText(question.textQuestion);
        //:TODO картинка (qimage)
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
    public void onBackPressed(){
        String title = prc.test.name;
        String message = "Желаете закончить тест?";
        String button1String = "Да, закончить";
        String button2String = "Нет, вернуться";
        ad = new AlertDialog.Builder(QuestionActivity.this);
        ad.setTitle(title);  // заголовок
        ad.setMessage(message); // сообщение
        ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                prc.finishTest(prc.test);
                goResult();
            }
        });
        ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1){}
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {}
        });
        ad.show();
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