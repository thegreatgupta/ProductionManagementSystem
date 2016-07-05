package com.jeevani.productionmanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * Created by Jeevani on 6/29/2016.
 */
public class RegisterActivity extends AppCompatActivity {

    EditText fname,lname,phone,email,password,confirmpassword;
    Button signupButton;
    TextView loginLink,forgotLink;

    String userFirstName, userLastName, userEmail, userPhone;
    String userDevice, userPassword, userConfirmPassword;

    int status;
    User user = new User();
    String TAG = "RegisterActivity:";

    String URL = Constant.IP_ADDRESS + "register";
    String URL1 = Constant.IP_ADDRESS + "register";

    //private SystemAppPreferences mSysPrefs;
    DBHandler dbHandler = new DBHandler(this, null, null, 1);

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

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    userFirstName = fname.getText().toString();
                    userLastName = lname.getText().toString();
                    userEmail = email.getText().toString();
                    userPhone = phone.getText().toString();
                    userPassword = password.getText().toString();
                    userConfirmPassword = confirmpassword.getText().toString();
                    userDevice = fetchSystemDetails();

                    // Validation

                    URL = URL1 +
                            "?firstName=" + URLEncoder.encode(userFirstName, "utf-8") +
                            "&lastName=asd4" + URLEncoder.encode(userLastName, "utf-8") +
                            "&email=asd4@asd.com" +URLEncoder.encode(userEmail, "utf-8") +
                            "&password=12345678" +URLEncoder.encode(userPassword, "utf-8") +
                            "&phone=8527419630" +URLEncoder.encode(userPhone, "utf-8") +
                            "&joinDate=65165184561" +URLEncoder.encode("06-07-2016", "utf-8") +
                            "&device=Postman 2" +URLEncoder.encode(userDevice, "utf-8");

                    // Request server for Registration Validation and get the user details
                    new RegisterDown().execute();

                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public String fetchSystemDetails() {
        String phoneModel = Build.MODEL;
        String androidVersion = Build.VERSION.RELEASE;
        String manufacture = Build.MANUFACTURER;
        return manufacture + ", " + phoneModel + ", v" + androidVersion;
    }

    // Server Connection and Request/Response transaction block
    public String registerValidationWS(String url) {

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

                Toast.makeText(RegisterActivity.this, "Failed to Connect to Server", Toast.LENGTH_SHORT).show();

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
    class RegisterDown extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();// Initialize the Dialogue BOX
            dialog = new ProgressDialog(RegisterActivity.this);
            dialog.setMessage("Signing UP, Please Wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return registerValidationWS(URL);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

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

                        dialog.hide();

                        Toast.makeText(RegisterActivity.this, "LABOUR ALREADY EXIST, PLEASE TRY ANOTHER EMAIL", Toast.LENGTH_SHORT).show();
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
