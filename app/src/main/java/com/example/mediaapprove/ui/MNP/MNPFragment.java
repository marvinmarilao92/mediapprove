package com.example.mediaapprove.ui.MNP;

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

// import library for volley method
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//import oop insertion api of adapter and parameters
import com.example.mediaapprove.ui.MNP.adapter.MNPAdapter;
import com.example.mediaapprove.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MNPFragment extends Fragment {
    //call from delete_edit_view_events.xml
    ImageView btnview;
    //call events adapter
    MNPAdapter adapter;
    //fragment objects calling
    RecyclerView recyclerView;
    public static ArrayList<MNPParam> MNParrayList = new ArrayList<>();
    String url = "https://mediapprove.net/android/fetch_MNP.php";
    RecyclerView.ViewHolder holder;
    EditText searchtext;
    TextView search;
    String st;
    MNPParam mnpParam;
    //Modal variable
    private MNPViewModel MNPViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        retrieveData();
        MNPViewModel = new ViewModelProvider(this).get(MNPViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mnp, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()); //to give layout of recyclerview inside the fragment
        recyclerView.setLayoutManager(layoutManager); //set the layoutmanager into a recyclerview
        adapter = new MNPAdapter(getActivity(), MNParrayList); //initialize adapter
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
                        PutData putData = new PutData("https://mediapprove.net/android/Search/search_MNP.php", "POST", field, data);

                        if (putData.startPut()) {

                            if (putData.onComplete()) {
                                //progress bar
                                String result = putData.getResult();
                                if(!result.equals("")) {
                                    MNParrayList.clear(); //will clear all the old data
                                    try{
                                        JSONObject jsonObject = new JSONObject(result);
                                        String sucess = jsonObject.getString("success");
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                                        if(sucess.equals("1")){  //get the data if there is
                                            for(int i=0;i<jsonArray.length();i++){
                                                //get the data from the database
                                                JSONObject object = jsonArray.getJSONObject(i);
                                                String reg = object.getString("region");
                                                String city = object.getString("city");
                                                String prov = object.getString("provides");
                                                String type = object.getString("type");
                                                String add = object.getString("address");
                                                String tel = object.getString("telno");
                                                String open = object.getString("open");

                                                mnpParam = new MNPParam(reg,city,prov,type,add,tel,open); //set it with events param
                                                MNParrayList.add(mnpParam); //and adds it into a temporary storage.
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                    catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                                else if(Idsearch.equals("")){
                                    MNParrayList.clear();
//                                    retrieveData();
                                }
                                else {
                                    Toast.makeText(getActivity(), "No Record found", Toast.LENGTH_SHORT).show();
                                    MNParrayList.clear();
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
                addall(MNParrayList); //add all data from the arraylist
                adapter.notifyDataSetChanged(); //to notify the data that had been changed
//              RearrangeItems();
                retrieveData(); //retrieve all data from database

            }

            private void addall(ArrayList<MNPParam> eventParams) {
                MNParrayList.clear(); //to clear all data from the arraylist
                MNParrayList.addAll(eventParams); //and add all data from the database into the arraylist
                adapter.notifyDataSetChanged();

            }
        });

        adapter.notifyDataSetChanged();
        //call view from delete,edit,view.xml
        btnview = root.findViewById(R.id.view);
        MNPViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
                        MNParrayList.clear(); //will clear all the old data
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(sucess.equals("1")){  //get the data if there is
                                for(int i=0;i<jsonArray.length();i++){
                                    //get the data from the database
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String reg = object.getString("region");
                                    String city = object.getString("city");
                                    String prov = object.getString("provides");
                                    String type = object.getString("type");
                                    String add = object.getString("address");
                                    String tel = object.getString("telno");
                                    String open = object.getString("open");
                                    mnpParam = new MNPParam(reg,city,prov,type,add,tel,open); //set it with events param
                                    MNParrayList.add(mnpParam); //and adds it into a temporary storage.
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
}