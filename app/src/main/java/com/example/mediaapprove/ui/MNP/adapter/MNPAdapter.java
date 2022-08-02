package com.example.mediaapprove.ui.MNP.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaapprove.ui.MNP.MNPFragment;
import com.example.mediaapprove.ui.MNP.MNPParam;
import com.example.mediaapprove.R;

import java.util.List;

public class MNPAdapter extends RecyclerView.Adapter<MNPAdapter.MyViewHolder> {

    Context context;  //It lets newly-created objects understand what has been going on.
    private List<MNPParam> ArrayListEvents;   //call BeneParam


    public MNPAdapter(@NonNull Context context, List<MNPParam> arrayListEvents) {

        this.context = context;
        this.ArrayListEvents = arrayListEvents;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemlist_mnp,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MNPParam data = ArrayListEvents.get(position); //position of the data from the recyclerview
        holder.texttitle.setText(data.getProv()); //set title into a recycler view
        holder.textdate.setText(data.getType()); //set date into a recyclerview


        //BUTTON VIEW
        //if you click the view toggle the dialog will appear and shows the EventsFragment details
        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MNPParam events = ArrayListEvents.get(holder.getAdapterPosition());
                Dialog dialog = new Dialog(context);
                //set Content view
                dialog.setContentView(R.layout.view_mnp);
                //initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //set layout
                dialog.getWindow().setLayout(width, height);
                notifyDataSetChanged();
                //show dialog
                dialog.show();
                //set variables to ids
                TextView tv_prov = dialog.findViewById(R.id.view_prov);
                TextView tv_type = dialog.findViewById(R.id.view_type);
                TextView tv_tel = dialog.findViewById(R.id.view_tel);
                TextView tv_req = dialog.findViewById(R.id.view_reg);
                TextView tv_city = dialog.findViewById(R.id.view_city);
                TextView tv_add = dialog.findViewById(R.id.view_add);
                TextView tv_open = dialog.findViewById(R.id.view_open);
                Button btnok = dialog.findViewById(R.id.btn_ok);
                //getdata from calevents and appear dialog on EventsFragment.java
                tv_prov.setText("Name: " + MNPFragment.MNParrayList.get(position).getProv());
                tv_type.setText("Type: " + MNPFragment.MNParrayList.get(position).getType());
                tv_tel.setText("Tel No.: " + MNPFragment.MNParrayList.get(position).getTel());
                tv_req.setText("Region: " + MNPFragment.MNParrayList.get(position).getReg());
                tv_city.setText("City: " + MNPFragment.MNParrayList.get(position).getCity());
                tv_add.setText("Address: " + MNPFragment.MNParrayList.get(position).getAdd());
                tv_open.setText("Open Hours: " + MNPFragment.MNParrayList.get(position).getOpen());
                //btnok if done viewing
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        //will dismiss the dialog from appearing
                    }
                });
            }
        });

    }// end pf onbindviewholder

    @Override
    public int getItemCount() {
        return ArrayListEvents.size();
    } //to count the size of the data


    public class MyViewHolder extends RecyclerView.ViewHolder {
        //give variables to call ids from delete_edit_view_events.xml
        public TextView texttitle, textdate;
        ImageView btn_view, btn_edit, btn_delete;
        RecyclerView recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            texttitle = itemView.findViewById(R.id.text_title);
            textdate = itemView.findViewById(R.id.text_date);
            btn_view = itemView.findViewById(R.id.view);
        }
    }

} //end

/*this is where recyclerview functionalities store. clickable view,edit,delete functions in recyclerview
 * and show it into a dialog form to execute all the process.  */

