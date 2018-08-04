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

public class OTP2 extends AppCompatActivity {
    com.alimuzaffar.lib.pin.PinEntryEditText otp;
    Button btn,resend;
    String vc,vc1,mobile,Message,otp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp2);
        otp=findViewById(R.id.otp);
        btn=findViewById(R.id.btn);
        vc1=getIntent().getStringExtra("otp");
        mobile=getIntent().getStringExtra("mobile");
        resend=findViewById(R.id.resend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vc=otp.getText().toString();
                if(vc.equals(vc1))
                {
                    Intent m=new Intent(OTP2.this,NewPassword.class);
                    m.putExtra("mobile",mobile);
                    startActivity(m);
                    finish();
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
                boolean flag = OTP_Generator.getInstance(OTP2.this).sendMessage(vc1, mobile);
                vc=otp.getText().toString();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent n=new Intent(OTP2.this,MainActivity.class);
        startActivity(n);
        finish();
    }



}
