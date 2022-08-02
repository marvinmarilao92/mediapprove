package com.example.mediaapprove;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediaapprove.auth.AccountChecker;
import com.example.mediaapprove.auth.AccountRegistration;
import com.example.mediaapprove.auth.CP_OTP;
import com.example.mediaapprove.auth.CP_OTP_confirm;
import com.example.mediaapprove.auth.ChangePass;
import com.example.mediaapprove.auth.OTP;
import com.example.mediaapprove.auth.OTP_confirm;
import com.google.android.material.snackbar.Snackbar;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login_account extends AppCompatActivity {
    //declaring objects
    private TextView lin,reg,btn_fp;
    private long backPressedTime;
    private Toast backToast;
    EditText adminUser;
    EditText adminPassword;
    BroadcastReceiver broadcastReceiver;
    String Rid_P,fname_P,plan_P,amn_P,con_P,tel_P,stat_P = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //network Checker
        broadcastReceiver = new ConnectionReceiver();
        registerNetworkBroadcast();
       //end of network checker

        //setting objects
        lin = findViewById(R.id.lin);
        reg = findViewById(R.id.btn_reg);
        btn_fp = findViewById(R.id.btn_FP);
        lin.requestFocus();
        adminUser = findViewById(R.id.usrusr);
        adminUser.requestFocus();
//        adm_UN = findViewById(R.id.adm_UN);
        adminPassword = findViewById(R.id.pswrdd);
        //this is for the keep me log in function it keeps the user logged in because of sharedpreferences it is like a
        //storage to check if a user is logged in or not it stores data from your key value that pass a string and check afterwards
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String checkbox = sharedPreferences.getString("remember","");
        String mobilenum = sharedPreferences.getString("mobile","");
        String mobilecon = sharedPreferences.getString("mobile_con","");
        String mobilechecked = sharedPreferences.getString("mobile_checked","");
        String changepass = sharedPreferences.getString("mobile_CP","");
        String cp_con = sharedPreferences.getString("Con_mobile_CP","");
        if (checkbox.equals("true")){
            Intent intent = new Intent(Login_account.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else if (!mobilenum.equals("")){
            Intent intent = new Intent(Login_account.this, OTP_confirm.class);
            startActivity(intent);
            finish();
        }else if (!mobilecon.equals("")){
            Intent intent = new Intent(Login_account.this, AccountChecker.class);
            startActivity(intent);
            finish();
        }else if (!mobilechecked.equals("")){
            Intent intent = new Intent(Login_account.this, AccountRegistration.class);
            startActivity(intent);
            finish();
        }else if (!changepass.equals("")){
            Intent intent = new Intent(Login_account.this, CP_OTP_confirm.class);
            startActivity(intent);
            finish();
        }else if (!cp_con.equals("")){
            Intent intent = new Intent(Login_account.this, ChangePass.class);
            startActivity(intent);
            finish();
        }else if (checkbox.equals("false")){
//            Toast.makeText(this,"Please save earth.",Toast.LENGTH_SHORT).show();
        }
        //this is a button listener for login
        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin(v);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //making an intent to move to another activity
                Intent in = new Intent(getApplicationContext(), OTP.class);
                //this will terminate the login activity
                //this will be the start of moving to the next activity
                startActivity(in);
            }
        });
        btn_fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //making an intent to move to another activity
                Intent in = new Intent(getApplicationContext(), CP_OTP.class);
                //this will terminate the login activity
                //this will be the start of moving to the next activity
                startActivity(in);
            }
        });
    }

    //process for login using online database
    public void onLogin(View view) {
        //passing input text to a converted variable
        String getAdminUser = adminUser.getText().toString();
        String getAdminPassword = adminPassword.getText().toString();
        //checking if edittext are not null
        if(getAdminUser.equals(""))  adminUser.setError("Username is Required");
        else if(getAdminPassword.equals("")) adminPassword.setError("Password is Required");
        else if (!getAdminUser.equals("") && !getAdminPassword.equals("")) {
            //start of http connection api
            //the handler will handle the data that we need to pass to run function
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    //basically setting username to data for parameter passing
                    String[] field = new String[2];
                    field[0] = "username";
                    field[1] = "password";
                    //Creating array for data
                    String[] data = new String[2];
                    data[0] = getAdminUser;
                    data[1] = getAdminPassword;

                        //this method is implemented from gradle named implementation 'com.github.VishnuSivadasVS:Advanced-HttpURLConnection:1.2'
                        //another api implementation where it will link to parcel-jet database(out website to get the php processes)
                        //the process is like you're setting a link url on where the api is stored
                        //in our case it is stored in online database which is provided in link and then the field will serve as a
                        //POST data that carries data array on it it looks like this
                        //field[0] = data[0] in process
                        PutData putData = new PutData("https://mediapprove.net/android/login_function.php", "POST", field, data);
                         //starting the execution of putting data
                        if (putData.startPut()) {
                            //start of process in checking if the data is passed and some php in the server will execute
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                //progress bar
                                ProgressDialog progressDialog = new ProgressDialog(Login_account.this);
                                progressDialog.show();
                                progressDialog.setContentView(R.layout.adm_progress_dialog);
                                //set Background to transparent
                                progressDialog.getWindow().setBackgroundDrawableResource(
                                        android.R.color.transparent
                                );
//                                if (result.equals("Login Success")) {
                                if(!result.equals("")) {
                                        //fetch data
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        String sucess = jsonObject.getString("success");
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                                        if (sucess.equals("1")) {  //get the data if there is
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                //get the data from the database
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                String P_Rid = object.getString("Rid");
                                                String P_fname = object.getString("fname");
                                                String P_plan = object.getString("plan");
                                                String P_amount = object.getString("amount");
                                                String P_con_no = object.getString("con_no");
                                                String P_Tel_no = object.getString("Tel_no");
                                                String P_Stat = object.getString("Stat");
                                                //making it global variable
                                                Rid_P = P_Rid;
                                                fname_P = P_fname;
                                                plan_P = P_plan;
                                                amn_P = P_amount;
                                                con_P = P_con_no;
                                                tel_P = P_Tel_no;
                                                stat_P = P_Stat;
                                                SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("remember","true");
                                                editor.putString("uname",getAdminUser);
                                                editor.putString("fname",fname_P);
                                                editor.putString("plan",plan_P);
                                                editor.putString("amn",amn_P);
                                                editor.putString("con",con_P);
                                                editor.putString("tel",tel_P);
                                                editor.putString("stat",stat_P);
                                                editor.putString("rid",Rid_P);
                                                editor.apply();
                                                //toast for welcome user
//                                              Toast.makeText(Login_account.this, "Welcome to Media Approve" + getAdminUser, Toast.LENGTH_SHORT).show();
                                                Snackbar.make(view, result, Snackbar.LENGTH_LONG)
                                                        .setAction("Welcome to Media Approve", null).show();
                                                //making an intent to move to another activity
                                                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                                                //this will terminate the login activity
                                                //this will be the start of moving to the next activity
                                                startActivity(in);
                                                progressDialog.dismiss();
                                                finish();
                                            }

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                } else {
                                    //this toast will get the return value from api if the putting is not complete this will show
                                    Snackbar.make(view, "Wrong Username or Password", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
//                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                                //End ProgressBar (Set visibility to GONE)
                            }
                        }
                        //End Write and Read data with URL
                } // end public run
            });
        }
    }//end of onLogin
    protected void registerNetworkBroadcast(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    protected void unregisterNetwork(){
      try{
          unregisterReceiver(broadcastReceiver);
      }catch (IllegalArgumentException e){
          e.printStackTrace();
      }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetwork();
    }

    //if the shared preference return true (keep me login checkbox)
    public void redirecttoHomePage(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    //if the back press initiate then this will execute
    //when back is press
    @Override
    public void onBackPressed() {
// this is for the double click to exit function
        if (backPressedTime + 2000>System.currentTimeMillis()){
            super.finish();
            Intent intent = new Intent(Login_account.this, Login_account.class);
            startActivity(intent);
            backToast.cancel();

            return;
        }else {
            backToast = Toast.makeText(getBaseContext(),"Press again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}


/*the process of login are simple first it will call the xml files to get the inputs and pass it to a variable,
next we will connect to our database using a connector to access the api
the api serve as our gateway connection to our database in order to add data the api will do it
the parameters and put data are from implementation
*/




