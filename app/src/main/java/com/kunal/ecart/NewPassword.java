package com.kunal.ecart;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class NewPassword extends AppCompatActivity {
    String mobile,pass,cpass;
    Button submit;
    EditText etpass,etcpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        etcpass=findViewById(R.id.cpass);
        etpass=findViewById(R.id.new_pass);
        submit=findViewById(R.id.submit2);
        mobile=getIntent().getStringExtra("mobile");
        final String url = "http://searchkero.com/ankit/reset.php";

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass=etpass.getText().toString();
                cpass=etcpass.getText().toString();

                if(pass.equals("") || cpass.equals(""))
                {
                    Snackbar.make(v,"All Information is required to be filled",2000).show();
                }
                else
                    if(!cpass.equals(pass))
                    {
                        Snackbar.make(v,"Passwords don't match",2000).show();
                    }
                    else {

                        final StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("db connectedrecords updated successfully"))
                                {
                                    Toast.makeText(NewPassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                    Intent j = new Intent(NewPassword.this, MainActivity.class);
                                    startActivity(j);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(NewPassword.this, "This mobile number was not linked to your account", Toast.LENGTH_SHORT).show();
                                    Intent l=new Intent(NewPassword.this,ForgotMobileNo.class);
                                    startActivity(l);
                                    finish();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }) {
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("mobilekey", mobile);
                                map.put("passkey", pass);

                                return map;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(NewPassword.this);
                        requestQueue.add(sr);
                    }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(NewPassword.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
