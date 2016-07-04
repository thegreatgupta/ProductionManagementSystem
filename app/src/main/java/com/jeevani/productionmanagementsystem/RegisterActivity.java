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
public class RegisterActivity extends AppCompatActivity {

    EditText fname,lname,phone,email,password,confirmpassword;
    Button signupButton;
    TextView loginLink,forgotLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);

        signupButton = (Button) findViewById(R.id.signupButton);

        loginLink = (TextView) findViewById(R.id.loginLink);
        loginLink.setText(Html.fromHtml("Already Registered? <i>SIGN IN</i>"));

        forgotLink = (TextView) findViewById(R.id.forgotLink);
        forgotLink.setText(Html.fromHtml("Forgot Password? <i>Click Here</i>"));

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        forgotLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotIntent = new Intent(RegisterActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotIntent);
            }
        });
    }
}
