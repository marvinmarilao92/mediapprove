package com.example.mediaapprove.ui.MBC;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

//import oop insertion api of adapter and parameters
import com.example.mediaapprove.ui.MBC.MBCParam;
import com.example.mediaapprove.R;
import com.example.mediaapprove.ui.MNP.MNPParam;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.mediaapprove.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

public class MBCFragment extends Fragment {

    CardView btnBronze,btnSilver,btnGold;
    public static ArrayList<MBCParam> MBCarrayList = new ArrayList<>();
    MBCParam mbcParam;
    String mbc_plan="";
    private MBCViewModel MBCViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        fetchMBC();
        MBCViewModel = new ViewModelProvider(this).get(MBCViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mbc, container, false);
        btnBronze = root.findViewById(R.id.btn_Bronze);
        btnSilver = root.findViewById(R.id.btn_Silver);
        btnGold = root.findViewById(R.id.btn_Gold);
        //button function for Bronze other details
        btnBronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view Dialog box
                Dialog viewdialog = new Dialog(root.getContext());
                viewdialog.setContentView(R.layout.modal_bronze_policy);
                int Vwidth = WindowManager.LayoutParams.MATCH_PARENT;
                int Vheight = WindowManager.LayoutParams.WRAP_CONTENT;
                viewdialog.getWindow().setLayout(Vwidth, Vheight);
                viewdialog.show();

            }
        });
        //button function for Silver other details
        btnSilver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view Dialog box
                Dialog viewdialog = new Dialog(root.getContext());
                viewdialog.setContentView(R.layout.modal_silver_policy);
                int Vwidth = WindowManager.LayoutParams.MATCH_PARENT;
                int Vheight = WindowManager.LayoutParams.WRAP_CONTENT;
                viewdialog.getWindow().setLayout(Vwidth, Vheight);
                viewdialog.show();

            }
        });
        //button function for Gold other details
        btnGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //view Dialog box
                Dialog viewdialog = new Dialog(root.getContext());
                viewdialog.setContentView(R.layout.modal_gold_policy);
                int Vwidth = WindowManager.LayoutParams.MATCH_PARENT;
                int Vheight = WindowManager.LayoutParams.WRAP_CONTENT;
                viewdialog.getWindow().setLayout(Vwidth, Vheight);
                viewdialog.show();

            }
        });

        if(mbc_plan.equals("1")){
            btnGold.setVisibility(View.VISIBLE);
        }else if(mbc_plan.equals("2")){
            btnSilver.setVisibility(View.VISIBLE);
        }
        else if(mbc_plan.equals("3")){
            btnBronze.setVisibility(View.VISIBLE);
        }

        MBCViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;

    }

    //fetch data
    public void fetchMBC(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String uname = sharedPreferences.getString("rid","");
        String Idsearch = uname;
        //Starting Write and Read data with URL
        //Creating array for parameters
        String[] field = new String[1];
        field[0] = "getId";
        //Creating array for data
        String[] data = new String[1];
        data[0] = Idsearch;
        PutData putData = new PutData("https://mediapprove.net/android/fetch_MBC.php", "POST", field, data);

        if (putData.startPut()) {

            if (putData.onComplete()) {
                //progress bar
                String result = putData.getResult();
                if(!result.equals("")) {
                    MBCarrayList.clear(); //will clear all the old data
                    try{

                        JSONObject jsonObject = new JSONObject(result);
                        String sucess = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        if(sucess.equals("1")){  //get the data if there is
                            for(int i=0;i<jsonArray.length();i++){
                                //get the data from the database
                                JSONObject object = jsonArray.getJSONObject(i);
                                String plan_mbc = object.getString("plan");
                                mbc_plan = plan_mbc;
//                                mbcParam = new MBCParam(plan); //set it with events param
//                                MBCarrayList.add(mbcParam); //and adds it into a temporary storage.
//                                Toast.makeText(getActivity(), mbc_plan, Toast.LENGTH_SHORT).show();
                            }

                        }


                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }


                }
                else if(Idsearch.equals("")){
                    MBCarrayList.clear();
//                                    retrieveData();
                }
                else {
                    Toast.makeText(getActivity(), "No Record found", Toast.LENGTH_SHORT).show();
                    MBCarrayList.clear();
                }
            }
        }
        //End Write and Read data with URL
    }
}