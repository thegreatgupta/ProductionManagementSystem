package com.jeevani.productionmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Jeevani on 6/29/2016.
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    Button forgotButton;
    TextView signupLink,loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email=(EditText)findViewById(R.id.email);

        forgotButton=(Button)findViewById(R.id.forgotButton);

        loginLink=(TextView)findViewById(R.id.loginLink);
        loginLink.setText(Html.fromHtml("Already Registered? <i>SIGN IN</i>"));

        signupLink=(TextView)findViewById(R.id.signupLink);
        signupLink.setText(Html.fromHtml("Not Registered? <i>SIGN UP</i>"));

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(ForgotPasswordActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

    }
}
