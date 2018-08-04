package com.kunal.ecart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Jsonparsing {

    String json ;
    public static ArrayList<String> name = new ArrayList<>();
    String str;
    public static int totalusers;

    public Jsonparsing(String response) {
        this.json = response;

    }

    public void jsontodo()
    {
        try {
            JSONObject jo = new JSONObject(json);
            JSONArray ja  = jo.getJSONArray("data");

            for(int i=0 ; i < ja.length() ; i++ )
            {
                JSONObject job = ja.getJSONObject(i);
                str = job.getString("name");
                name.add(str);
                totalusers++;
            }
        }

        catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
