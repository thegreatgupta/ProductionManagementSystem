package com.jeevani.productionmanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.jeevani.productionmanagementsystem.util.NetConnectionDetector;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Jeevani on 6/29/2016.
 */
public class RegisterActivity extends AppCompatActivity {

    EditText fname,lname,phone,email,password,confirmpassword;
    Button signupButton;
    TextView loginLink,forgotLink;

    String userFirstName, userLastName, userEmail, userPhone;
    String userDevice, userPassword, userConfirmPassword;

    final int SUCCESS = 1;
    final int EXCEPTION = 2;
    final int EMPTY = 3;
    final int CONNECTION_ERROR = 4;
    final int NO_CONNECTIVITY = 5;
    final int CONNECTION_TIMEOUT_ERROR = 6;

    int status;
    User user = new User();
    String TAG = "RegisterActivity:";

    final String TYPE = "register";

    Context mContext;

    String param;
    String decodedString, result;

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

                    param = "firstName=" + userFirstName +
                            "&lastName=" + userLastName +
                            "&email=" + userEmail +
                            "&password=" + userPassword +
                            "&phone=" + userPhone +
                            "&joinDate=07-07-2016" +
                            "&device=" + userDevice;

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
    public int registerValidationWS(String urlParam, Context context) {

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

    public void registerParsing(String result) {

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

                }
                else {

                    Toast.makeText(RegisterActivity.this, "USER ALREADY EXIST, PLEASE USE OTHER EMAIL", Toast.LENGTH_SHORT).show();
                    password.setText("");
                    confirmpassword.setText("");
                    email.setText("");
                    return;

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    class RegisterDown extends AsyncTask<String, String, Integer> {

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
        protected Integer doInBackground(String... params) {

            return registerValidationWS(param, RegisterActivity.this);

        }

        @Override
        protected void onPostExecute(Integer code) {
            super.onPostExecute(code);

            dialog.hide();

            switch (code) {
                case SUCCESS:
                    registerParsing(result);
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
