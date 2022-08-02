package com.example.mediaapprove;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.content.BroadcastReceiver;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mediaapprove.ui.Approval.APPFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    TextView tvres,tvfname,tvRid,tvname,tvplan,tvamt,tvcon,tvtel,tvstat;
//    String st;
    View hView;
    private static final String KEY_UNAME = "uname";
    private AppBarConfiguration mAppBarConfiguration;
    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        broadcastReceiver = new ConnectionReceiver();
        registerNetworkBroadcast();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your feedback function ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //toast for welcome user
                Snackbar.make(view, "You Can now send your Complain", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //making an intent to move to another activity
                Intent in = new Intent(getApplicationContext(), send_feedback.class);
                //this will terminate the login activity
                //this will be the start of moving to the next activity
                startActivity(in);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        hView = navigationView.getHeaderView(0);
        tvfname = (TextView) hView.findViewById(R.id.fname);
        tvres = (TextView) hView.findViewById(R.id.resno);
        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String puname = sharedPreferences.getString("rid","");
        String pfname = sharedPreferences.getString("fname","");
        tvres.setText(puname);
        tvfname.setText(pfname);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_MBC, R.id.nav_Beneficiaries, R.id.nav_MNP, R.id.nav_APP, R.id.nav_CCI)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //this will set an action for menu tree dots
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                Dialog viewdialog = new Dialog(MainActivity.this);
                viewdialog.setContentView(R.layout.adm_activity_aboutus);
                int Vwidth = WindowManager.LayoutParams.MATCH_PARENT;
                int Vheight = WindowManager.LayoutParams.WRAP_CONTENT;
                viewdialog.getWindow().setLayout(Vwidth, Vheight);
                viewdialog.show();
                tvRid= viewdialog.findViewById(R.id.txtRid);
                tvname = viewdialog.findViewById(R.id.txtname);
                tvplan = viewdialog.findViewById(R.id.txtplan);
                tvamt = viewdialog.findViewById(R.id.txtamount);
                tvcon = viewdialog.findViewById(R.id.txtcon);
                tvtel = viewdialog.findViewById(R.id.txttel);
                tvstat = viewdialog.findViewById(R.id.txtstat);
                //session value calling
                SharedPreferences sharedPreferences = getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                String Rid = sharedPreferences.getString("uname","");
                String name = sharedPreferences.getString("fname","");
                String plans = sharedPreferences.getString("plan","");
                String amount = sharedPreferences.getString("amn","");
                String contact = sharedPreferences.getString("con","");
                String tel = sharedPreferences.getString("tel","");
                String stat = sharedPreferences.getString("stat","");
                //insertion of parameters data into TextView
                tvRid.setText("Residence No: "+Rid);
                tvname.setText("FullName: "+ name);
                tvstat.setText("Status: "+ stat);
                tvplan.setText("Plan: "+plans);
                tvamt.setText("Amount: "+ amount);
                tvcon.setText("CP#: "+ contact);
                tvtel.setText("Tel. No: "+ tel);
                return true;
            case R.id.action_logout:
                logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    //this 3 methods use to check the internet connection
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
    //process for login using online database
    public void logOut() {
        //passing input text to a converted variable
        String getFunction = "logout";
        //checking if edittext are not null
        if (!getFunction.equals("")) {
            //start of http connection api
            //the handler will handle the data that we need to pass to run function
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    //basically setting username to data for parameter passing
                    String[] field = new String[1];
                    field[0] = "out_func";
                    //Creating array for data
                    String[] data = new String[1];
                    data[0] = getFunction;

                    //this method is implemented from gradle named implementation 'com.github.VishnuSivadasVS:Advanced-HttpURLConnection:1.2'
                    //another api implementation where it will link to parcel-jet database(out website to get the php processes)
                    //the process is like you're setting a link url on where the api is stored
                    //in our case it is stored in online database which is provided in link and then the field will serve as a
                    //POST data that carries data array on it it looks like this
                    //field[0] = data[0] in process
                    PutData putData = new PutData("https://mediapprove.net/android/logout.php", "POST", field, data);
                    //starting the execution of putting data
                    if (putData.startPut()) {
                        //start of process in checking if the data is passed and some php in the server will execute
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            //progress bar
                            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.show();
                            progressDialog.setContentView(R.layout.adm_progress_dialog);
                            //set Background to transparent
                            progressDialog.getWindow().setBackgroundDrawableResource(
                                    android.R.color.transparent
                            );
                            if (result.equals("Logout Success")) {
                                //another shared preference for logout
                                SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                                //setting the shared preference(keep me login) to uncheck and intent back to login
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("remember","false");
                                editor.apply();
                                finish();
                                Intent in = new Intent(MainActivity.this, Login_account.class);
                                startActivity(in);
                                progressDialog.dismiss();
                            } else {
                                //this toast will get the return value from api if the putting is not complete this will show
//                                Snackbar.make(view, "Failed to Logout", Snackbar.LENGTH_LONG)
//                                        .setAction("Action", null).show();
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
}
