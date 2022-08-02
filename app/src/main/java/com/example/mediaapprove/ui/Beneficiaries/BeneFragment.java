package com.example.mediaapprove.ui.Beneficiaries;

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

import com.example.mediaapprove.R;

// import library for volley method
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//import oop insertion api of adapter and parameters
import com.example.mediaapprove.ui.Beneficiaries.BeneParam;
import com.example.mediaapprove.ui.Beneficiaries.adapter.BeneAdapter;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BeneFragment extends Fragment {
    //call from delete_edit_view_events.xml
    ImageView btnview;
    //call events adapter
    BeneAdapter adapter;
    //fragment objects calling
    RecyclerView recyclerView;
    public static ArrayList<BeneParam> BenearrayList = new ArrayList<>();
    String url = "https://mediapprove.net/android/fetch_Bene.php";
    RecyclerView.ViewHolder holder;
    EditText searchtext;
    TextView search;
    BeneParam beneParam;
    //Modal variable

    private BeneViewModel beneViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        beneViewModel = new ViewModelProvider(this).get(BeneViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bene, container, false);

        retrieveData();//used to fetch database
        recyclerView = root.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()); //to give layout of recyclerview inside the fragment
        recyclerView.setLayoutManager(layoutManager); //set the layoutmanager into a recyclerview
        adapter = new BeneAdapter(getActivity(), BenearrayList); //initialize adapter
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

                        String Idsearch = searchtext.getText().toString();

                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[1];
                        field[0] = "getId";
                        //Creating array for data
                        String[] data = new String[1];
                        data[0] = Idsearch;
                        PutData putData = new PutData("https://mediapprove.net/android/Search/search_bene.php", "POST", field, data);

                        if (putData.startPut()) {

                            if (putData.onComplete()) {
                                //progress bar
                                String result = putData.getResult();
                                if(!result.equals("")) {
                                    BenearrayList.clear(); //will clear all the old data
                                    try{
                                        JSONObject jsonObject = new JSONObject(result);
                                        String sucess = jsonObject.getString("success");
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if(sucess.equals("1")){  //get the data if there is
                                            for(int i=0;i<jsonArray.length();i++){
                                                //get the data from the database
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                String id = object.getString("bene_id");
                                                String Rid = object.getString("bene_Rid");
                                                String name = object.getString("bene_name");
                                                String plan = object.getString("bene_plan");
                                                String amount = object.getString("bene_amount");
                                                String gen = object.getString("bene_gen");
                                                String dob = object.getString("bene_dob");
                                                String contact = object.getString("bene_no");
                                                String tel = object.getString("bene_tel");
                                                String start = object.getString("bene_dStarted");
                                                String exp = object.getString("bene_dExpire");
                                                String stat = object.getString("bene_status");
                                                String ACP = object.getString("bene_ACP");
                                                String TPE = object.getString("bene_TPE");
                                                String THE = object.getString("bene_THE");
                                                String TPOE = object.getString("bene_TPOE");

                                                beneParam = new BeneParam(id,Rid,name,plan,amount,gen,dob,contact
                                                        ,tel,start,exp,stat,ACP,TPE,THE,TPOE); //set it with events param
                                                BenearrayList.add(beneParam); //and adds it into a temporary storage.
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                    catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                                else if(Idsearch.equals("")){
                                    BenearrayList.clear();
                                    retrieveData();
                                }
                                else {
                                    Toast.makeText(getActivity(), "No Record found", Toast.LENGTH_SHORT).show();
                                    BenearrayList.clear();
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
                addall(BenearrayList); //add all data from the arraylist
                adapter.notifyDataSetChanged(); //to notify the data that had been changed
//              RearrangeItems();
                retrieveData(); //retrieve all data from database

            }

            private void addall(ArrayList<BeneParam> eventParams) {
                BenearrayList.clear(); //to clear all data from the arraylist
                BenearrayList.addAll(eventParams); //and add all data from the database into the arraylist
                adapter.notifyDataSetChanged();

            }
        });

        adapter.notifyDataSetChanged();

        //call view from delete,edit,view.xml
        btnview = root.findViewById(R.id.view);

        beneViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    //RETRIEVE DATA
    public void retrieveData(){

        //request to retrieve data from the database.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        BenearrayList.clear(); //will clear all the old data
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(sucess.equals("1")){  //get the data if there is
                                for(int i=0;i<jsonArray.length();i++){
                                    //get the data from the database
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("bene_id");
                                    String Rid = object.getString("bene_Rid");
                                    String name = object.getString("bene_name");
                                    String plan = object.getString("bene_plan");
                                    String amount = object.getString("bene_amount");
                                    String gen = object.getString("bene_gen");
                                    String dob = object.getString("bene_dob");
                                    String contact = object.getString("bene_no");
                                    String tel = object.getString("bene_tel");
                                    String start = object.getString("bene_dStarted");
                                    String exp = object.getString("bene_dExpire");
                                    String stat = object.getString("bene_status");
                                    String ACP = object.getString("bene_ACP");
                                    String TPE = object.getString("bene_TPE");
                                    String THE = object.getString("bene_THE");
                                    String TPOE = object.getString("bene_TPOE");


                                    beneParam = new BeneParam(id,Rid,name,plan,amount,gen,dob,contact
                                            ,tel,start,exp,stat,ACP,TPE,THE,TPOE); //set it with events param
                                    BenearrayList.add(beneParam); //and adds it into a temporary storage.
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