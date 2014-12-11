package com.example.alpha.projecttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);


        // TODO: Replace to fake loader
        Test test = new Test("test");
        test.newTest("Test");

        // TODO: Replace to fake loader
        String testJSONQuest =
                "{" +
                    "\"number\":2," +
                    "\"TextQuestion\":[" +
                        "{" +
                            "\"id\":0," +
                            "\"name\":\"Test1\"," +
                            "\"textQuestion\":\"Textвопроса\"," +
                            "\"image\":\"Link\"," +
                            "\"answers\":" +
                                "{" +
                                    "\"number\":2," +
                                    "\"answers\":" +
                                    "[" +
                                        "{\"text\":\"Ответ1\"},{\"text\":\"Ответ2\"}" +
                                    "]" +
                                "}" +
                        "},"+
                        "{" +
                            "\"id\":0," +
                            "\"name\":\"Test1\"," +
                            "\"textQuestion\":\"Textвопроса2\"," +
                            "\"image\":\"Ссылка2\"," +
                            "\"answers\":" +
                                "{" +
                                    "\"number\":2," +
                                    "\"answers\":" +
                                    "[" +
                                        "{\"text\":\"Ответ1\"},{\"text\":\"Ответ2\"}" +
                                    "]" +
                                "}" +
                        "}" +
                    "]" +
                "}";
       test.CreateListQuestions(testJSONQuest);
        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogintap();

            }
        });

        FakeDataLoaderLoader l = new FakeDataLoaderLoader() {
          @Override
          void onLoad(JSONArray data){

          }
        };
        l.loadData();
    }

    void onLogintap() {

        Intent intent = new Intent(this, TestList.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
