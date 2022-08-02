package com.example.mediaapprove.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediaapprove.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class OTP_confirm extends AppCompatActivity {
    String mobile_no="";
    EditText con_otp,mobile_num;

    @Override
    //start of the process
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_otp_confirm);
        con_otp = findViewById(R.id.otp_code);
        mobile_num = findViewById(R.id.mob);
        mobile_num.setEnabled(false);
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String mobilenum = sharedPreferences.getString("mobile","");
        mobile_num.setText(mobilenum);
    }
    //onclick listener for button
    public void func_otp(View view) {

        //initializatiion for the params

        String getOTP = con_otp.getText().toString();
        String getNo = mobile_num.getText().toString();
        //checking if the ET is empty or over restriction
        if(getOTP.equals("")){con_otp.setError("Enter your OTP");con_otp.requestFocus();}
        else if( //checking if all the fields have value except middle name
                !getOTP.equals("")){

            //api post starter
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    //Starting Write and Read data with URL
                    //Creating array for parameters

                    String[] field = new String[2];
                    field[0] = "otp";
                    field[1] = "mobileno";

                    //Creating array for data
                    String[] data = new String[2];
                    data[0] = getOTP;
                    data[1] = getNo;

                    //this method is implemented from gradle named implementation 'com.github.VishnuSivadasVS:Advanced-HttpURLConnection:1.2'
                    //another api implementation where it will link to parcel-jet database(out website to get the php processes)
                    //the process is like you're setting a link url on where the api is stored
                    //in our case it is stored in online database which is provided in link and then the field will serve as a
                    //POST data that carries data array on it it looks like this
                    //field[0] = data[0] in process
                    PutData putData = new PutData("https://mediapprove.net/android/otp/register_otp_verify.php", "POST", field, data);
                    //starting the execution of putting data
                    if (putData.startPut()) {
                        //start of process in checking if the data is passed and some php in the server will execute
                        if (putData.onComplete()) {
                            //start of result checking from api
                            String result = putData.getResult();
                            //calling record fragment after the success
                            if(result.equals("otp checked")){
//                                SharedPreferences sharedPreferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.putString("mobileno",null);
                                Toast.makeText(getApplicationContext(), "Check your message inbox for otp code", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("mobile_con",getNo);
                                editor.putString("mobile",null);
                                editor.apply();//create your session
                                Toast.makeText(getApplicationContext(), "Validate Credentials", Toast.LENGTH_SHORT).show();
                                //making an intent to move to another activity
                                Intent in = new Intent(getApplicationContext(), AccountChecker.class);
                                //this will terminate the login activity
                                //this will be the start of moving to the next activity
                                startActivity(in);

                            }else if(result.equals("Invalid Otp")){
                                Toast.makeText(getApplicationContext(), "Wrong OTP code", Toast.LENGTH_SHORT).show();
                                con_otp.setText(null);
                            }else{
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                //finish();
                            }

                        }
                    }
                    //End Write and Read data with URL
                }
            });


        }else Toast.makeText(this, "There's something wrong with your credentials", Toast.LENGTH_SHORT).show();
    }
    //End of method
    @Override
    public void onBackPressed() {
        finish();
    }
}