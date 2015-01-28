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

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
@EActivity
public class QuestionActivity extends Activity implements QuestionActivityInterface {
    @ViewById TextView questiontextView, maxView, idnView, timecounttext, tvOpistime;
    @ViewById Button otvet_button;
    @ViewById ListView lvAnswer;
    @ViewById ImageView imageView2;

    AlertDialog.Builder ad;
    ProcessTest prc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        MyApp app = ((MyApp) getApplicationContext());
        prc = app.prc;
        prc.getQuestion(this);
    }

    @Click
    void otvet_button() {
        prc.setAnswer(lvAnswer.getCheckedItemPositions());
    }

    public void showTimer(boolean mode, long min, long sec) {
        if (mode==true) {
            timecounttext.setText("" + min + " мин. " + sec + " сек.");
        }
        else{
            tvOpistime.setText("Время неограничено");
        }
    }

    public void showMSG(String str){
        Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void goResult() {
        ResultActivity_.intent(this).start();
        finish();
    }

    public void showQuestion(Question question, int count, int max, String imagelink){
        imageView2.setImageBitmap(null);
        if (imagelink != "null") {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
            ImageLoader il = ImageLoader.getInstance();
            il.init(config);
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .resetViewBeforeLoading(true)
                    .cacheOnDisk(true)
                    .build();
            il.displayImage(imagelink, imageView2, options);
        }
        questiontextView.setText(question.textQuestion);
        ArrayList<String> answersText = prc.getAnswers(question);
        if (question.qtype == 0){
            lvAnswer.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.answer_list_element, answersText);
            lvAnswer.setAdapter(adapter);
        }
        else {
            lvAnswer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.answer_list_element, answersText);
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