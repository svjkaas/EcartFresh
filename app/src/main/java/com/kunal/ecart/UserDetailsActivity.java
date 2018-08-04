package com.kunal.ecart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UserDetailsActivity extends AppCompatActivity {
    ListView listView;
    static String username = "";
    static String password = "";
    static String chatWith = "";
    TextView nousers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        listView = findViewById(R.id.listview);
        nousers=findViewById(R.id.nousers);
        fetchdata();
    }

    private void fetchdata() {

        String url ;
        url = "http://searchkero.com/ankit/data2.php";
        StringRequest sr = new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonparse(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(UserDetailsActivity.this);
        requestQueue.add(sr);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chatWith=Jsonparsing.name.get(position);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(UserDetailsActivity.this,ChatActivity.class));
                    }
                });
            }
        });
    }

    private void jsonparse(String response) {
        Jsonparsing jsonparsing = new Jsonparsing(response);
        jsonparsing.jsontodo();
        MyAdapter myAdapter =new MyAdapter(this , R.layout.item1, Jsonparsing.name);
        listView.setAdapter(myAdapter);
        if(Jsonparsing.totalusers<=1)
        {
            nousers.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else
        {
            nousers.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

    }
}

