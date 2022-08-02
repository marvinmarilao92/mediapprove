package com.example.mediaapprove;

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

import com.google.android.material.snackbar.Snackbar;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class send_feedback extends AppCompatActivity {
    //declaring variables
    EditText F_Rid, F_Message;

    @Override
    //start of the process
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_sendfeedback);

        //setting object values
        F_Rid = findViewById(R.id.F_ResID);
        F_Message = findViewById(R.id.F_Message);
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String uname = sharedPreferences.getString("rid","");
        F_Rid.setText(uname);
    }
    //onclick listener for button

    //note no restriction in mobile add later
    //also note birthday needs to adjust for 17 and above only
    public void send_func(View view) {

        //initializatiion for the params
        String getRid = F_Rid.getText().toString();
        String getMessage = F_Message.getText().toString();

        //checking if the ET is empty or over restriction
        if(getRid.equals("")) F_Rid.setError("Provide your Residence ID");
        else if(getMessage.equals("")) F_Message.setError("Provide Your Message");
        else if( //checking if all the fields have value except middle name
                !getRid.equals("") && !getMessage.equals("")){
            //api post starter
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[2];
                    field[0] = "Fid";
                    field[1] = "Fmessage";

                    //Creating array for data
                    String[] data = new String[2];
                    data[0] = getRid;
                    data[1] = getMessage;

                    //this method is implemented from gradle named implementation 'com.github.VishnuSivadasVS:Advanced-HttpURLConnection:1.2'
                    //another api implementation where it will link to parcel-jet database(out website to get the php processes)
                    //the process is like you're setting a link url on where the api is stored
                    //in our case it is stored in online database which is provided in link and then the field will serve as a
                    //POST data that carries data array on it it looks like this
                    //field[0] = data[0] in process
                    PutData putData = new PutData("https://www.mediapprove.net/android/send_feedback.php", "POST", field, data);
                    //starting the execution of putting data
                    if (putData.startPut()) {
                        //start of process in checking if the data is passed and some php in the server will execute
                        if (putData.onComplete()) {
                            //start of result checking from api
                            String result = putData.getResult();
                            //calling record fragment after the success
                            if(result.equals("Sending Success")){
                                Snackbar.make(view, "Successfully Submitted your complain", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
//                                F_Rid.setText(null);
                                F_Message.setText(null);
//                                Toast.makeText(getApplicationContext(), "Successfully Submitted your complain", Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                            //finish();
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


    public void Cancel_btn(View view) {

        //making an intent to move to another activity
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        //this will terminate the login activity
        //this will be the start of moving to the next activity
        //toast for welcome user
        Toast.makeText(send_feedback.this, "You Can now send your feedback", Toast.LENGTH_SHORT).show();
        startActivity(in);
    }
}