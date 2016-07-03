package com.jeevani.productionmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Jeevani on 6/29/2016.
 */
public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button loginButton;
    TextView signupLink,forgotLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        loginButton=(Button)findViewById(R.id.loginButton);

        signupLink=(TextView)findViewById(R.id.signupLink);
        forgotLink=(TextView)findViewById(R.id.forgotLink);

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        forgotLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotIntent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(forgotIntent);
            }
        });
    }
}
