package com.kunal.ecart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotMobileNo extends AppCompatActivity {
    Button submit;
    EditText etmobile;
    String otp,Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_mobile_no);
        etmobile=findViewById(R.id.mbile);
        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = OTP_Generator.Generate(Message);
                boolean flag = OTP_Generator.getInstance(ForgotMobileNo.this).sendMessage(otp, etmobile.getText().toString());
                Intent i=new Intent(ForgotMobileNo.this,OTP2.class);
                i.putExtra("otp",otp);
                i.putExtra("mobile",etmobile.getText().toString());
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent j=new Intent(ForgotMobileNo.this,MainActivity.class);
        startActivity(j);
        finish();
    }
}
