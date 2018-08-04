package com.kunal.ecart;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import java.util.Random;

public class OTP_Generator implements VerificationListener{
    private static String Message="";
    static OTP_Generator myGenerator;
    Context context;
    boolean flag = false;

    OTP_Generator(Context context) {
        this.context = context;
    }

    public static String Generate(String Message) {
        Random generate = new Random();
        char[] otp = new char[4];
        for (int i = 0; i < 4; i++) {
            int x = generate.nextInt(10);
            otp[i] = (char) (x + '0');
        }
        Message = String.valueOf(otp);
        return Message;
    }
    public boolean sendMessage(String OTP,String Contact)
    {
        Verification Verify = SendOtpVerification.createSmsVerification(
                SendOtpVerification
                        .config("91"+Contact)  //specify the mobile number ID here
                        .context(context)
                        .autoVerification(true)
                        .expiry("2")
                        .senderId("ECARTO")
                        .otp(OTP)
                        .otplength("4")
                        .build(),this);

        Verify.initiate();
        return flag;

    }

    @Override
    public void onInitiated(String response) {
        Log.d("data1", "Initialized!" + response);
        //OTP successfully resent/sent.
        //Toast.makeText(context, "Initialized!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitiationFailed(Exception paramException) {
        Log.e("data2", "Verification initialization failed: " + paramException.getMessage());
        //sending otp failed.
        //Toast.makeText(context, "Verification initialization failed:", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerified(String response) {
        Log.d("data3", "Verified!\n" + response);
        //OTP verified successfully.
        //Toast.makeText(context, "Verified!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerificationFailed(Exception paramException) {
        Log.e("data4", "Verification failed: " + paramException.getMessage());
        //OTP  verification failed.
    }

    public static OTP_Generator getInstance(Context context)
    {
        if(myGenerator == null)
        {
            myGenerator = new OTP_Generator(context);
        }
        return myGenerator;
    }
}

