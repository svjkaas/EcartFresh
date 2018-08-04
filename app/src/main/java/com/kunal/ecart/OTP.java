package com.kunal.ecart;

import android.content.Intent;
import android.nfc.Tag;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msg91.sendotp.library.VerificationListener;

import java.util.HashMap;
import java.util.Map;

public class OTP extends AppCompatActivity {
    com.alimuzaffar.lib.pin.PinEntryEditText otp;
    Button btn,resend;
    String vc,vc1,name,mobile,pass,Message,otp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otp=findViewById(R.id.otp);
        btn=findViewById(R.id.btn);
        vc1=getIntent().getStringExtra("otp");
        final String url = "http://searchkero.com/ankit/insert3.php";
        name=getIntent().getStringExtra("name");
        mobile=getIntent().getStringExtra("mobile");
        pass=getIntent().getStringExtra("pass");
        resend=findViewById(R.id.resend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vc=otp.getText().toString();
                if(vc.equals(vc1))
                {
                   send(vc,url);
                }
                else
                {
                    Snackbar.make(view,"Incorrect OTP",2000).show();
                }

            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vc1 = OTP_Generator.Generate(Message);
                boolean flag = OTP_Generator.getInstance(OTP.this).sendMessage(vc1, mobile);
                vc=otp.getText().toString();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent n=new Intent(OTP.this,MainActivity.class);
        startActivity(n);
        finish();
    }

    public void send(String vc,String url)
    {
        final StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> map = new HashMap<>();
                map.put("namekey", name);
                map.put("mobilekey", mobile);
                map.put("passkey", pass);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OTP.this);
        requestQueue.add(sr);
        Toast.makeText(OTP.this, "mobile no verified", Toast.LENGTH_SHORT).show();
        Intent j=new Intent(OTP.this,MainActivity.class);
        startActivity(j);
        finish();
    }


}
