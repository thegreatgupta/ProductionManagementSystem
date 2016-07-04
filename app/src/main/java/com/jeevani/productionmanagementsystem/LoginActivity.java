package com.jeevani.productionmanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jeevani.productionmanagementsystem.bean.User;
import com.jeevani.productionmanagementsystem.constant.Constant;
import com.jeevani.productionmanagementsystem.util.NetConnectionDetector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Chandra Prakash Gupta on 6/29/2016.
 */
public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button loginButton;
    TextView signupLink,forgotLink;

    boolean status = false;
    User user = new User();
    String TAG = "Login_Activity:";

    String URL = Constant.IP_ADDRESS + "login";
    String URL1 = Constant.IP_ADDRESS + "login";

    private SystemAppPreferences mSysPrefs;

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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,LabourMainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Server Connection and Request/Response transaction block
    public String loginValidationWS(String url) {

        // It is use to create the output string for JSON parsing
        StringBuilder stringBuilder = new StringBuilder();
        // Create the connection
        HttpClient client = new DefaultHttpClient();
        // Defines the request and response method ex. POST or GET and sets the URL
        HttpPost post = new HttpPost(url);

        try {

            // Iy is used to get the response from the server
            HttpResponse response = client.execute(post);
            // Get the statusCode. For successful executeion the statusCode is 200 and other are 400, 404, 500 and etc
            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {

                // Parse the entity from the reponse
                HttpEntity entity = response.getEntity();
                // Parse the entity into the stream
                InputStream inputStream = entity.getContent();

                // It will store the stream into buffer for simple execute or extraction
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String output;

                // It will add line by line to the output string
                while ((output = reader.readLine()) != null) {
                    stringBuilder.append(output);
                }

                Log.d(TAG, "Success to Download File");

            }
            else {

                Toast.makeText(LoginActivity.this, "Failed to Connect to Server", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Failed to Download File from URL-" + url);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the output data into string and return to the onPostExecution method
        return stringBuilder.toString();

    }
    class LoginDown extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return loginValidationWS(URL);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONArray array = new JSONArray(result);

                for (int i = 0; i < array.length(); i++) {

                    JSONObject object = array.getJSONObject(i);

                    status = object.getBoolean("status");

                    if (status) {

                        user.setEmail(object.getString("email"));
                        user.setPassword(object.getString("password"));

                        // Create DBHandle object to perform CRUD operation
                        DBHandler db = new DBHandler(Login.this, null, null, 1);
                        // Add the user details to the database for fast and easy login in future
                        db.addNewUser(user);

                        // Create a Bundle of User detail to pass between the pages.
                        Bundle userBundle = new Bundle();

                        // Add dtails to the Bundle
                        userBundle.putString("email", user.getEmail());

                        dialog.hide();


                        if (user.getType().equals("USER")) {

                            // Create intent for movinf to new Activity
                            Intent loginIntent = new Intent(getApplicationContext(), UserHome.class);
                            // Add Bundle to intent
                            loginIntent.putExtras(userBundle);
                            // Start the next Activity
                            startActivity(loginIntent);
                            // Finish the current Activity
                            //finish();

                        }
                        else {

                            // Create intent for movinf to new Activity
                            Intent loginIntent = new Intent(getApplicationContext(), AdminHome.class);
                            // Add Bundle to intent
                            loginIntent.putExtras(userBundle);
                            // Start the next Activity
                            startActivity(loginIntent);
                            // Finish the current Activity
                            //finish();

                        }

                    }
                    else {

                        dialog.hide();

                        Toast.makeText(LoginActivity.this, "INVALID USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
                        password.setText("");
                        return;
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
