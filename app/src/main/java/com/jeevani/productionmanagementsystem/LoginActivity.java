package com.jeevani.productionmanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jeevani.productionmanagementsystem.bean.User;
import com.jeevani.productionmanagementsystem.constant.Constant;
import com.jeevani.productionmanagementsystem.database.DBHandler;
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
 * Created by Jeevani on 6/29/2016.
 */
public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button loginButton;
    TextView signupLink,forgotLink;

    String userEmail, userPassword;

    final int SUCCESS = 1;
    final int EXCEPTION = 2;
    final int EMPTY = 3;
    final int CONNECTION_ERROR = 4;
    final int NO_CONNECTIVITY = 5;
    final int CONNECTION_TIMEOUT_ERROR = 6;

    int status;
    User user = new User();
    String TAG = "LoginActivity:";

    //String URL2 = Constant.IP_ADDRESS + "login";
    //String URL1 = Constant.IP_ADDRESS + "login";

    final String TYPE = "login";

    Context mContext;

    String param;
    String decodedString, result;

    //private SystemAppPreferences mSysPrefs;
    DBHandler dbHandler = new DBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (dbHandler.isUserLoggedIn()) {

            User user = dbHandler.getUserDetail();

            // Create a Bundle of User detail to pass between the pages.
            Bundle userBundle = new Bundle();

            // Add details to the Bundle
            userBundle.putString("userId", user.getUserId());
            userBundle.putString("firstName", user.getFirstName());
            userBundle.putString("lastName", user.getLastName());
            userBundle.putString("email", user.getEmail());
            userBundle.putString("phone", user.getPhone());
            userBundle.putString("type", user.getType());



            if (user.getType().equals("LABOUR")) {

                // Create intent for moving to new Activity
                Intent loginIntent = new Intent(getApplicationContext(), LabourMainActivity.class);
                // Add Bundle to intent
                loginIntent.putExtras(userBundle);
                // Start the next Activity
                startActivity(loginIntent);
                // Finish the current Activity
                finish();

            }
            else {

                // Create intent for moving to new Activity
                Intent loginIntent = new Intent(getApplicationContext(), ManagerMainActivity.class);
                // Add Bundle to intent
                loginIntent.putExtras(userBundle);
                // Start the next Activity
                startActivity(loginIntent);
                // Finish the current Activity
                finish();


            }
        }

        /*
        mSysPrefs = SystemAppPreferences.getInstance(getApplicationContext());
        if(mSysPrefs.getUserId() != null && !mSysPrefs.getUserId().equals("")) {

            Log.d(TAG, "UserId:" + mSysPrefs.getUserId() + " TYPE:" + mSysPrefs.getType());

            if(mSysPrefs.getType().equals("LABOUR")) {
                // Create intent for moving to new Activity and Start the next Activity
                startActivity(new Intent(this, LabourMainActivity.class));
                // Finish the current Activity
                finish();
            }
            else {
                // Create intent for moving to new Activity and Start the next Activity
                startActivity(new Intent(this, ManagerMainActivity.class));
                // Finish the current Activity
                finish();
            }

        }*/

        setContentView(R.layout.activity_login);

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        loginButton=(Button)findViewById(R.id.loginButton);

        signupLink=(TextView)findViewById(R.id.signupLink);
        //View the text in italic style
        signupLink.setText(Html.fromHtml("Not Registered? <i>SIGN UP</i>"));

        forgotLink=(TextView)findViewById(R.id.forgotLink);
        //View the text in italic style
        forgotLink.setText(Html.fromHtml("Forgot Password? <i>Click Here</i>"));

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        forgotLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotIntent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(forgotIntent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    userEmail = email.getText().toString();
                    userPassword = password.getText().toString();

                    param = "email=" + userEmail +
                            "&password=" + userPassword;

                    //URL2 = URL1 + "?email="+ URLEncoder.encode(userEmail, "utf-8") +"&password="+URLEncoder.encode(userPassword, "utf-8");

                    // Request server for login Validation and get the user details
                    new LoginDown().execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
/*
    // Server Connection and Request/Response transaction block
    public String loginValidationWS2(String url) {

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
*/

    // Server Connection and Request/Response transaction block
    public int loginValidationWS(String urlParam, Context context) {

        // Assign the context to the global context object
        mContext = context;

        try {

            // Check for the network connectivity.
            NetConnectionDetector connection = NetConnectionDetector.getInstance(mContext);
            if (!connection.isConntectingToInternet())
                return NO_CONNECTIVITY;

            // Define the parameters to the String object.
            String urlParamters = urlParam;
            // Define the url to the URL object to open the connection.
            URL url = new URL(Constant.IP_ADDRESS + TYPE);
            // Open the connection and define the connection property.
            HttpURLConnection mHttpPost = (HttpURLConnection) url.openConnection();
            mHttpPost.setDoInput(true);
            mHttpPost.setDoOutput(true);
            mHttpPost.setReadTimeout(10000);
            mHttpPost.setRequestMethod("POST");
            mHttpPost.setRequestProperty("Content-Type", "application/x-www-form-urlencoded ");

            DataOutputStream out = new DataOutputStream(mHttpPost.getOutputStream());
            // Write the parameters to the requested url.
            out.writeBytes(urlParamters);
            // Flush the request
            out.flush();
            // Close the connection
            out.close();

            // Read the response data
            StringBuilder sb = new StringBuilder();
            // Convert the data to BufferedString for decoding and parsing
            BufferedReader in = new BufferedReader(new InputStreamReader(mHttpPost.getInputStream()));
            // Decode the data line by line and save it to StringBuilder
            while ((decodedString = in.readLine()) != null)
                sb.append(decodedString);

            in.close();

            // Parse the incoming response
            Log.i("Async Call Response", sb.toString());
            result = sb.toString();

            return SUCCESS;

        } catch (SocketTimeoutException e) {
            return CONNECTION_ERROR;
        } catch (ConnectTimeoutException e) {
            return CONNECTION_TIMEOUT_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return EXCEPTION;
        }

    }

    public void loginParsing(String result) {

        try {

            JSONArray array = new JSONArray(result);

            for (int i = 0; i < array.length(); i++) {

                JSONObject object = array.getJSONObject(i);

                status = object.getInt("status");

                if (status == 200) {

                    // Read add the data from the JSON object
                    user.setUserId("" + object.getInt("userId"));
                    user.setFirstName(object.getString("firstName"));
                    user.setLastName(object.getString("lastName"));
                    user.setEmail(object.getString("email"));
                    user.setPhone(object.getString("phone"));
                    user.setType(object.getString("type"));

                    dbHandler.addNewUser(user);

                    // Create a Bundle of User detail to pass between the pages.
                    Bundle userBundle = new Bundle();

                    // Add dtails to the Bundle
                    userBundle.putString("userId", user.getUserId());
                    userBundle.putString("firstName", user.getFirstName());
                    userBundle.putString("lastName", user.getLastName());
                    userBundle.putString("email", user.getEmail());
                    userBundle.putString("phone", user.getPhone());
                    userBundle.putString("type", user.getType());

                    if (user.getType().equals("LABOUR")) {

                        // Create intent for movinf to new Activity
                        Intent loginIntent = new Intent(getApplicationContext(), LabourMainActivity.class);
                        // Add Bundle to intent
                        loginIntent.putExtras(userBundle);
                        // Start the next Activity
                        startActivity(loginIntent);
                        // Finish the current Activity
                        finish();

                    }
                    else {

                        // Create intent for movinf to new Activity
                        Intent loginIntent = new Intent(getApplicationContext(), ManagerMainActivity.class);
                        // Add Bundle to intent
                        loginIntent.putExtras(userBundle);
                        // Start the next Activity
                        startActivity(loginIntent);
                        // Finish the current Activity
                        finish();


                    }

                        /*
                        // Update the user data to the Shared Preferences
                        mSysPrefs.setUserId(user.getUserId());
                        mSysPrefs.setFirstName(user.getFirstName());
                        mSysPrefs.setLastName(user.getLastName());
                        mSysPrefs.setEmail(user.getEmail());
                        mSysPrefs.setPhone(user.getPhone());
                        mSysPrefs.setType(user.getType());

                        dialog.hide();

                        if(mSysPrefs.getType().equals("LABOUR")) {
                            // Create intent for moving to new Activity and Start the next Activity
                            startActivity(new Intent(LoginActivity.this, LabourMainActivity.class));
                            // Finish the current Activity
                            finish();
                        }
                        else {
                            // Create intent for moving to new Activity and Start the next Activity
                            startActivity(new Intent(LoginActivity.this, ManagerMainActivity.class));
                            // Finish the current Activity
                            finish();
                        }
                        */

                }
                else {

                    Toast.makeText(LoginActivity.this, "INVALID USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
                    password.setText("");
                    return;

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    class LoginDown extends AsyncTask<String, String, Integer> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();// Initialize the Dialogue BOX
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Loggin, Please Wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            //return loginValidationWS(URL2);
            return loginValidationWS(param, LoginActivity.this);
        }

        @Override
        protected void onPostExecute(Integer code) {
            super.onPostExecute(code);

            dialog.hide();

            switch (code) {
                case SUCCESS:
                    loginParsing(result);
                    break;
                case EXCEPTION:
                    Toast.makeText(mContext, "There was some error. Please try again..", Toast.LENGTH_LONG).show();
                    break;
                case EMPTY:
                    Toast.makeText(mContext, "No Result Found", Toast.LENGTH_LONG).show();
                    break;
                case CONNECTION_ERROR:
                    Toast.makeText(mContext, "There is an issue while connecting to the server.\nPlease try after sometime",
                            Toast.LENGTH_LONG).show();
                    break;
                case NO_CONNECTIVITY:
                    Toast.makeText(mContext, "Internet Connection was not found.", Toast.LENGTH_LONG).show();
                    break;
                case CONNECTION_TIMEOUT_ERROR:
                    Toast.makeText(mContext, "It took too long to get the response from server. Please try again..", Toast.LENGTH_LONG)
                            .show();
                    break;
            }


        }
    }
}