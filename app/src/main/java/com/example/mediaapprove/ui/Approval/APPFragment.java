package com.example.mediaapprove.ui.Approval;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mediaapprove.R;
import com.example.mediaapprove.ui.Approval.AppParam;
import com.example.mediaapprove.ui.Approval.adapter.AppAdapter;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class APPFragment extends Fragment {
    //call from delete_edit_view_events.xml
    ImageView btnview;
    //call events adapter
    AppAdapter adapter;
    //fragment objects calling
    RecyclerView recyclerView;
    public static ArrayList<AppParam> ApparrayList = new ArrayList<>();
    String url = "https://mediapprove.net/android/fetch_Approval.php";
    RecyclerView.ViewHolder holder;
    EditText searchtext;
    TextView search;
    AppParam appParam;
    //Modal variable
    private com.example.mediaapprove.ui.Approval.APPViewModel APPViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Initializing shared preference
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        String uname = sharedPreferences.getString("rid","");
        APPViewModel = new ViewModelProvider(this).get(com.example.mediaapprove.ui.Approval.APPViewModel.class);
        View root = inflater.inflate(R.layout.fragment_app, container, false);

//        retrieveData();//used to fetch database
//        fetchMCS(uname);
        recyclerView = root.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()); //to give layout of recyclerview inside the fragment
        recyclerView.setLayoutManager(layoutManager); //set the layoutmanager into a recyclerview
        adapter = new AppAdapter(getActivity(), ApparrayList); //initialize adapter
        searchtext = root.findViewById(R.id.ETsearch);//calling search textfield from xml file
        search = root.findViewById(R.id.btn_search);//calling search button from xml file
        recyclerView.setAdapter(adapter);//set adapter

        //Search function
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

//                      String Idsearch = searchtext.getText().toString();
                        String Idsearch = uname;
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[1];
                        field[0] = "getId";
                        //Creating array for data
                        String[] data = new String[1];
                        data[0] = Idsearch;
                        PutData putData = new PutData("https://mediapprove.net/android/Search/search_approve.php", "POST", field, data);

                        if (putData.startPut()) {

                            if (putData.onComplete()) {
                                //progress bar
                                String result = putData.getResult();
                                if(!result.equals("")) {
                                    ApparrayList.clear(); //will clear all the old data
                                    try{
                                        JSONObject jsonObject = new JSONObject(result);
                                        String sucess = jsonObject.getString("success");
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if(sucess.equals("1")){  //get the data if there is
                                            for(int i=0;i<jsonArray.length();i++){
                                                //get the data from the database
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                String TO = object.getString("app_TO");
                                                String DN = object.getString("app_DN");
                                                String EB = object.getString("app_EB");
                                                String EP = object.getString("app_EP");
                                                String Date = object.getString("app_Date");
                                                String Rid = object.getString("app_Rid");
                                                String Holdname = object.getString("app_Holdname");
                                                String ReqStat = object.getString("app_ReqStat");
                                                String PP = object.getString("app_PP");
                                                String PT = object.getString("app_PT");
                                                String AD = object.getString("app_AD");

                                                appParam = new AppParam(TO,DN,EB,EP,Date,Rid,Holdname,ReqStat,PP,PT,AD); //set it with events param
                                                ApparrayList.add(appParam); //and adds it into a temporary storage.
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                    catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                                else if(Idsearch.equals("")){
                                    ApparrayList.clear();
//                                    retrieveData();
                                }
                                else {
                                    Toast.makeText(getActivity(), "No Record found", Toast.LENGTH_SHORT).show();
                                    ApparrayList.clear();
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                        //End Write and Read data with URL
                    }
                });
            }
        });

        //swiperefresh section
        SwipeRefreshLayout swiperefresh =  root.findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefresh.setRefreshing(false); //to stop from refreshing
                addall(ApparrayList); //add all data from the arraylist
                adapter.notifyDataSetChanged(); //to notify the data that had been changed
//              RearrangeItems();
//                retrieveData(); //retrieve all data from database

            }

            private void addall(ArrayList<AppParam> appParams) {
                ApparrayList.clear(); //to clear all data from the arraylist
                ApparrayList.addAll(appParams); //and add all data from the database into the arraylist
                adapter.notifyDataSetChanged();

            }
        });

        adapter.notifyDataSetChanged();

        //call view from delete,edit,view.xml
        btnview = root.findViewById(R.id.view);

        APPViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    //fetch data
//    public void fetchMCS(String uname){
//
//        String Idsearch = uname;
//        //Starting Write and Read data with URL
//        //Creating array for parameters
//        String[] field = new String[1];
//        field[0] = "getId";
//        //Creating array for data
//        String[] data = new String[1];
//        data[0] = Idsearch;
//        PutData putData = new PutData("https://mediapprove.net/android/Search/search_approve.php", "POST", field, data);
//
//        if (putData.startPut()) {
//
//            if (putData.onComplete()) {
//                //progress bar
//                String result = putData.getResult();
//                if(!result.equals("")) {
//                    ApparrayList.clear(); //will clear all the old data
//                    try{
//                        JSONObject jsonObject = new JSONObject(result);
//                        String sucess = jsonObject.getString("success");
//                        JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                        if(sucess.equals("1")){  //get the data if there is
//                            for(int i=0;i<jsonArray.length();i++){
//                                //get the data from the database
//                                JSONObject object = jsonArray.getJSONObject(i);
//                                String TO = object.getString("app_TO");
//                                String DN = object.getString("app_DN");
//                                String EB = object.getString("app_EB");
//                                String EP = object.getString("app_EP");
//                                String Date = object.getString("app_Date");
//                                String Rid = object.getString("app_Rid");
//                                String Holdname = object.getString("app_Holdname");
//                                String ReqStat = object.getString("app_ReqStat");
//                                String PP = object.getString("app_PP");
//                                String PT = object.getString("app_PT");
//                                String AD = object.getString("app_AD");
//
//                                appParam = new AppParam(TO,DN,EB,EP,Date,Rid,Holdname,ReqStat,PP,PT,AD); //set it with events param
//                                ApparrayList.add(appParam); //and adds it into a temporary storage.
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                    catch (JSONException e){
//                        e.printStackTrace();
//                    }
//                }
//                else if(Idsearch.equals("")){
//                    ApparrayList.clear();
////                                    retrieveData();
//                }
//                else {
//                    Toast.makeText(getActivity(), "No Record found", Toast.LENGTH_SHORT).show();
//                    ApparrayList.clear();
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        }
//        //End Write and Read data with URL
//    }
    //RETRIEVE DATA
    public void retrieveData(){

        //request to retrieve data from the database.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ApparrayList.clear(); //will clear all the old data
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(sucess.equals("1")){  //get the data if there is
                                for(int i=0;i<jsonArray.length();i++){
                                    //get the data from the database
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String TO = object.getString("app_TO");
                                    String DN = object.getString("app_DN");
                                    String EB = object.getString("app_EB");
                                    String EP = object.getString("app_EP");
                                    String Date = object.getString("app_Date");
                                    String Rid = object.getString("app_Rid");
                                    String Holdname = object.getString("app_Holdname");
                                    String ReqStat = object.getString("app_ReqStat");
                                    String PP = object.getString("app_PP");
                                    String PT = object.getString("app_PT");
                                    String AD = object.getString("app_AD");

                                    appParam = new AppParam(TO,DN,EB,EP,Date,Rid,Holdname,ReqStat,PP,PT,AD); //set it with events param
                                    ApparrayList.add(appParam); //and adds it into a temporary storage.
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged(); //notify if there is a changed on data
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request); // will add its request
    }
    //end of fetching database
}