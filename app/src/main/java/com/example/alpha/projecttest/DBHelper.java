package com.example.alpha.projecttest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 1 on 24.12.2014.
 */
public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase dbs;
    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
            dbs = this.getWritableDatabase();

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       // dbs = getWritableDatabase();
      //  db = dbs;
      //  Log.d("MyLogs", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table tableTests ("
                + "id integer primary key autoincrement,"
                + "nameID text,"
                + "date text,"
                + "JSON text"
                + ");");

        db.execSQL("create table tableAnswers ("
                + "id integer primary key autoincrement,"
                + "nameID text,"
                + "JSON text"
                + ");");
      //  Log.d("MyLogs","Созданы");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean findTest(int id, String date){
       String nameID = Integer.toString(id);
        String bdDate;
        bdDate = "";
      Cursor c = dbs.query("tableTests",  null, "nameID = ?", new String[] {nameID}, null, null, null);
        if (c.moveToFirst()) {
            bdDate = c.getString(2);
        }
        if (bdDate.equals(date)) {
            return true;
        } else {
            return false;
        }
    }

    public String getTest(int id){
        String nameID = Integer.toString(id);
        String json;
        Cursor c = dbs.query("tableTests",  null, "nameID = ?", new String[] {nameID}, null, null, null);
        c.moveToFirst();
        json = c.getString(3);
        return json;
    }

    public void setTest(int id, String date, String JSON){
        //delete по id надо добавить
        String idS = Integer.toString(id);
        int del = dbs.delete("tableTests", "nameID = " + Integer.toString(id), null);
      //  int del = dbs.delete("tableTests", "nameid = ?",  new String[] {idS});
        ContentValues cv=new ContentValues();
        cv.put("nameID", Integer.toString(id));
        cv.put("date", date);
        cv.put("JSON", JSON);
        dbs.insert("tableTests", null, cv);

    }

    public void setAnswerInBD(int id, String json){
        //delete по id надо добавить
        dbs.delete("tableAnswers", "nameID = " + Integer.toString(id), null);
        ContentValues cv=new ContentValues();
        cv.put("nameID", Integer.toString(id));
        cv.put("JSON", json);
        dbs.insert("tableAnswers", null, cv);
       // Log.d("MyLogs","Добавлен");

    }

    public String getAnswersFromBD(int id){
        String nameID = Integer.toString(id);
        String json;
        Cursor c = dbs.query("tableAnswers",  null, "nameID = ?", new String[] {nameID}, null, null, null);
        c.moveToFirst();
        json = c.getString(2);
       // Log.d("MyLogs","Взят");
        return json;
    }

}

