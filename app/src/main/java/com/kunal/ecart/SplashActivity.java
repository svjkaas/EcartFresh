package com.kunal.ecart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIME_OUT=2000;
    private String UserName,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getNetworkState();
                //startActivity(new Intent(SplashActivity.this,ScreenSlideActivity.class));
                finish();
            }
        },SPLASH_TIME_OUT);


    }
    private void getNetworkState() {
        //  Boolean State = CheckConnection.getInstance(this).getNetworkStatus();
        Boolean State = true;
        if (State)  //internet is connected
        {
            CheckUserCredentials();

        }

    }
    public void CheckUserCredentials()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("logDetails",
                Context.MODE_PRIVATE);
         UserName = sharedPreferences.getString("UserName",null);
        Password = sharedPreferences.getString("Password",null);
        Log.d("HAR","Username:"+UserName+" Password:"+Password);
        if(UserName != null && Password != null) {
            // Services.getInstance(context).Validate(UserName,Password);
            startActivity(new Intent(SplashActivity.this, Display2.class));

        }
        else
        {
            Log.d("HAR","Shared preference not found");
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
    }

}

