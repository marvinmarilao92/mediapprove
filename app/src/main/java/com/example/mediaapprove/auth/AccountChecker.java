package com.example.mediaapprove.auth;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediaapprove.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AccountChecker extends AppCompatActivity {
    //declaring variables
    EditText rid_et ,num_et,bday_et ;
    TextView btn_register;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    //start of the process
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_checker);

        //setting object values
        rid_et = findViewById(R.id.et_Rid);
        num_et = findViewById(R.id.et_num);
        bday_et = findViewById(R.id.et_bday);

        //session contact number
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String mobilenum = sharedPreferences.getString("mobile_con","");
        num_et.setText(mobilenum);
        num_et.setEnabled(false);
        btn_register = findViewById(R.id.btn_save);

        bday_et.setFocusableInTouchMode(false);
        bday_et.setFocusable(false);

        bday_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    birthdayget(v);
                }else{

                }

            }
        });
    }
    //onclick listener for button

    //note no restriction in mobile add later
    //also note birthday needs to adjust for 17 and above only
    public void CheckAccount(View view) {

//        initializatiion for the params

        String getRi = rid_et.getText().toString();
        String getNum = num_et.getText().toString();
        String getBday = bday_et.getText().toString();

        //checking if the ET is empty or over restriction
        if(getRi.equals("")){rid_et.setError("Provide a First name");rid_et.requestFocus();}
        else if(getNum.equals("")){num_et.setError("Provide a Last Name");num_et.requestFocus();}
        else if(getBday.equals("")){bday_et.setError("Please enter your Birth date");bday_et.requestFocus();}
        else if( //checking if all the fields have value except middle name
                !rid_et.equals("") &&  !num_et.equals("")){
            //api post starter
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    //Starting Write and Read data with URL
                    //Creating array for parameters

                    String[] field = new String[3];
                    field[0] = "residence_id";
                    field[1] = "contactno";
                    field[2] = "dob";

                    //Creating array for data
                    String[] data = new String[3];
                    data[0] = getRi;
                    data[1] = getNum;
                    data[2] = getBday;
                    //this method is implemented from gradle named implementation 'com.github.VishnuSivadasVS:Advanced-HttpURLConnection:1.2'
                    //another api implementation where it will link to parcel-jet database(out website to get the php processes)
                    //the process is like you're setting a link url on where the api is stored
                    //in our case it is stored in online database which is provided in link and then the field will serve as a
                    //POST data that carries data array on it it looks like this
                    //field[0] = data[0] in process
                    PutData putData = new PutData("https://mediapprove.net/android/otp/register.php", "POST", field, data);
                    //starting the execution of putting data
                    if (putData.startPut()) {
                        //start of process in checking if the data is passed and some php in the server will execute
                        if (putData.onComplete()) {
                            //start of result checking from api
                            String result = putData.getResult();
                            //calling record fragment after the success
                            if(result.equals("Success")){
                                Toast.makeText(getApplicationContext(), "Successfully added user", Toast.LENGTH_SHORT).show();
                                //removing shared preference
                                SharedPreferences sharedPreferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("mobile_con",null);
                                editor.putString("mobile_checked",getNum);
                                editor.apply();//create your session
                                //making an intent to move to another activity
                                Intent in = new Intent(getApplicationContext(), AccountRegistration.class);
                                //this will terminate the login activity
                                //this will be the start of moving to the next activity
                                startActivity(in);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                rid_et.setText(null);
                                bday_et.setText(null);
                                //finish();
                            }
//
                        }
                    }
                    //End Write and Read data with URL
                }
            });



        }else Toast.makeText(this, "There's something wrong with your credentials", Toast.LENGTH_SHORT).show();
    }

    //    Birthday Methods for calendar picker
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setBirth();
        }

    };
    //action listener for birthday
    public void birthdayget(View v) {
        new DatePickerDialog(AccountChecker.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }
    //setting birthday format
    private void setBirth() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        bday_et.setText(sdf.format(myCalendar.getTime()));
    }


    //End of method
    @Override
    public void onBackPressed() {
        finish();
    }
}