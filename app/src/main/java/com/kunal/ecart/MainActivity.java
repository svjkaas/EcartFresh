package com.kunal.ecart;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements TextWatcher{
    Button b1,b2,b3;
    String resp;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    CheckBox checkBoxTerms;
    CheckBox remember_me ;
    PasswordStrength str ;
    View view1;
    EditText etname, etmobile, etpass, etcpass,etpass1,etemail;
    String Message;
    String otp;
    VerticalTextView txtSignIn, txtRegister;
    LinearLayout llSignIn, llRegister;
    String name,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        etname=findViewById(R.id.uname);
        etmobile=findViewById(R.id.inPhone);
        etpass=findViewById(R.id.inPassword);
        etcpass=findViewById(R.id.inConfirmPassword);
        etpass1=findViewById(R.id.Password);
        etemail=findViewById(R.id.email);
        checkBoxTerms = (CheckBox)findViewById(R.id.checkBoxTerms);
        remember_me = (CheckBox)findViewById(R.id.rememberme);
        b3=findViewById(R.id.btnForgotPassword);
        etpass.addTextChangedListener(this);

        llSignIn = (LinearLayout) findViewById(R.id.llSign_in);
        llRegister = (LinearLayout) findViewById(R.id.llRegister);

        txtRegister = (VerticalTextView) findViewById(R.id.txtRegister);
        txtSignIn = (VerticalTextView) findViewById(R.id.txtSignIn);

        b2 = (Button) findViewById(R.id.btnSignIn);
        b1 = (Button) findViewById(R.id.btnRegister);


        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInForm();
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterForm();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String url = "http://searchkero.com/ankit/check.php";
                if(checkBoxTerms.isChecked()) {

                    if (etname.getText().toString().equals("") || etmobile.getText().toString().equals("") ||
                            etpass.getText().toString().equals("") || etcpass.getText().toString().equals("")) {
                        Snackbar.make(view, "All information is required to be filled", 3000).show();
                        etname.setText("");
                        etmobile.setText("");
                        etpass.setText("");
                        etcpass.setText("");

                    } else if (str.getText(MainActivity.this).toString().equals("Weak")) {
                        Toast.makeText(MainActivity.this, "Password is too weak to proceed", Toast.LENGTH_SHORT).show();
                        etname.setText("");
                        etmobile.setText("");
                        etpass.setText("");
                        etcpass.setText("");
                    } else if (!etpass.getText().toString().equals(etcpass.getText().toString())) {
                        Snackbar.make(view, "Passwords don't match", 3000).show();
                        etname.setText("");
                        etmobile.setText("");
                        etpass.setText("");
                        etcpass.setText("");

                    } else {
                        progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Signing Up...");
                        progressDialog.show();
                        final StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("db connectedmobile already registerd!!")) {
                                    Snackbar.make(view, "Mobile already registered", 3000).show();
                                    progressDialog.hide();
                                } else if (response.equals("db connectedname already registered")) {
                                    Snackbar.make(view, "Username already registered", 3000).show();
                                    progressDialog.hide();
                                } else if (response.equals("db connected")) {
                                    otp = OTP_Generator.Generate(Message);
                                    boolean flag = OTP_Generator.getInstance(MainActivity.this).sendMessage(otp, etmobile.getText().toString());
                                    Intent i = new Intent(MainActivity.this, OTP.class);
                                    i.putExtra("otp", otp);
                                    i.putExtra("name", etname.getText().toString());
                                    i.putExtra("mobile", etmobile.getText().toString());
                                    i.putExtra("pass", etcpass.getText().toString());
                                    startActivity(i);
                                    finish();

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("namekey", etname.getText().toString());
                                map.put("mobilekey", etmobile.getText().toString());
                                return map;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                        requestQueue.add(sr);


                    }


                }
                else
                {

                    Snackbar.make(view, "Accept Terms and Conditions First", 3000).show();
                }


            }


        });

        StrictMode.ThreadPolicy st=new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(st);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                name=etemail.getText().toString();
                pass=etpass1.getText().toString();


                if (name.equals("") || pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                } else {
                    String url1;
                    url1="http://searchkero.com/ankit/login2.php";
                    StringRequest sr=new StringRequest(1, url1, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setMessage("Signing In...");
                            progressDialog.show();
                            if(response.equals("db connectedlogin"))
                            {
                                if(remember_me.isChecked()) {
                                    SharedPreferences sp =
                                            getSharedPreferences("logDetails", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("UserName", name);
                                    editor.putString("Password", pass);
                                    editor.commit();
                                }
                                Intent j=new Intent(MainActivity.this,Display2.class);
                                startActivity(j);
                                finish();

                            }
                            else
                            {
                                Snackbar.make(view,"Invalid Credentials",3000).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>map=new HashMap<>();
                            map.put("namekey",etemail.getText().toString());
                            map.put("passkey",etpass1.getText().toString());
                            return map;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                    requestQueue.add(sr);

                }


            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l=new Intent(MainActivity.this,ForgotMobileNo.class);
                startActivity(l);
                finish();
            }
        });
    }

    private void showSignInForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llRegister.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llRegister.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignIn.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignIn.requestLayout();

        txtRegister.setVisibility(View.VISIBLE);
        txtSignIn.setVisibility(View.GONE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignIn.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        b2.startAnimation(clockwise);

    }

    private void showRegisterForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignIn.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignIn.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llRegister.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llRegister.requestLayout();

        txtRegister.setVisibility(View.GONE);
        txtSignIn.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llRegister.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        b1.startAnimation(clockwise);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        updatePasswordStrengthView(charSequence.toString());
    }



    @Override
    public void afterTextChanged(Editable editable) {

    }
    private void updatePasswordStrengthView(String password) {

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView strengthView = (TextView) findViewById(R.id.password_strength);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            progressBar.setProgress(0);
            return;
        }

        str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(this));
        strengthView.setTextColor(str.getColor());

        progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (str.getText(this).equals("Weak")) {
            progressBar.setProgress(25);
        } else if (str.getText(this).equals("Medium")) {
            progressBar.setProgress(50);
        } else if (str.getText(this).equals("Strong")) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }
}

