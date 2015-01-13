package com.example.alpha.projecttest.models;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 1 on 07.12.2014.
 */
public class Question {
    public int id;
    public String name;
    public String textQuestion;
    public String image;
    public int qtype;
    public ArrayList<Answer> answers;
}
