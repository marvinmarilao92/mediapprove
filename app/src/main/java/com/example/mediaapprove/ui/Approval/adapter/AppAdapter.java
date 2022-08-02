package com.example.mediaapprove.ui.Approval.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediaapprove.R;
import com.example.mediaapprove.ui.Approval.APPFragment;
import com.example.mediaapprove.ui.Approval.AppParam;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.MyViewHolder> {

    Context context;  //It lets newly-created objects understand what has been going on.
    private List<AppParam> ArrayListEvents;   //call BeneParam


    public AppAdapter(@NonNull Context context, List<AppParam> arrayListEvents) {

        this.context = context;
        this.ArrayListEvents = arrayListEvents;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemlist_bene,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AppParam data = ArrayListEvents.get(position); //position of the data from the recyclerview
        holder.texttitle.setText(data.getEB()); //set title into a recycler view
        holder.textdate.setText(data.getReqStat()); //set date into a recyclerview

        //BUTTON VIEW
        //if you click the view toggle the dialog will appear and shows the EventsFragment details
        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            //Object used for view admin fragments
            TextView tvid,tvlogo,tvRid,tvfname,tvplan,tvamount,tvgen,tvdob,tvcontact,tvtel,tvstart,tvexp,btn_show;
            @Override
            public void onClick(View v) {

                AppParam beneParam = ArrayListEvents.get(holder.getAdapterPosition());
                Dialog dialog = new Dialog(context);
                //set Content view
                dialog.setContentView(R.layout.view_app);
                //initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //set layout
                dialog.getWindow().setLayout(width, height);
                notifyDataSetChanged();
                //show dialog
                dialog.show();
                //Initializing Views Object form xml file
                tvRid = dialog.findViewById(R.id.txtRid);
                tvfname = dialog.findViewById(R.id.txtname);
                tvplan = dialog.findViewById(R.id.txtplan);
//                tvlogo = dialog.findViewById(R.id.txtlogo1);

                tvamount = dialog.findViewById(R.id.txtamount);
                //tvgen = dialog.findViewById(R.id.txtgen);
                tvdob= dialog.findViewById(R.id.txtdob);
                tvcontact = dialog.findViewById(R.id.txtcontact);
                tvtel = dialog.findViewById(R.id.txtel);
                tvstart = dialog.findViewById(R.id.txtstart);
                tvexp = dialog.findViewById(R.id.txtexp);
                btn_show = dialog.findViewById(R.id.showVIewTxt);

                //insertion of parameters data into TextView
                tvRid.setText("Approval No: "+ APPFragment.ApparrayList.get(position).getTO());
                tvfname.setText("Provider: "+ APPFragment.ApparrayList.get(position).getEB());
                tvplan.setText("Status: "+ APPFragment.ApparrayList.get(position).getReqStat());

                tvamount.setText("Position: "+ APPFragment.ApparrayList.get(position).getEP());
                //tvgen.setText("Gen: "+ BeneFragment.BenearrayList.get(position).getCourse());
                tvdob.setText("Date: "+ APPFragment.ApparrayList.get(position).getDate());
                tvcontact.setText("Residence Id: "+ APPFragment.ApparrayList.get(position).getRid());
                tvtel.setText("Holder Name: "+ APPFragment.ApparrayList.get(position).getHoldname());
                tvstart.setText("Partial Payment: "+ APPFragment.ApparrayList.get(position).getPP());
                tvexp.setText("Balance: "+ APPFragment.ApparrayList.get(position).getPT());

                btn_show.setOnClickListener(this::btn_show);//function for show other info

            }
            //function used for View Fragments
            //use to show personal information
            public void btn_show(View view) {
//                tvlogo.setVisibility(View.VISIBLE);
                tvamount.setVisibility(View.VISIBLE);
                tvdob.setVisibility(View.VISIBLE);
                tvcontact.setVisibility(View.VISIBLE);
                tvtel.setVisibility(View.VISIBLE);
                tvstart.setVisibility(View.VISIBLE);
                tvexp.setVisibility(View.VISIBLE);

                btn_show.setText("Hide Additional Details");
                btn_show.setOnClickListener(this::btn_hide);
            }
            //use to hide personal information
            public void btn_hide(View view) {
//                tvlogo.setVisibility(View.GONE);
                tvamount.setVisibility(View.GONE);
                tvdob.setVisibility(View.GONE);
                tvcontact.setVisibility(View.GONE);
                tvtel.setVisibility(View.GONE);
                tvstart.setVisibility(View.GONE);
                tvexp.setVisibility(View.GONE);

                btn_show.setText("Show Additional Details");
                btn_show.setOnClickListener(this::btn_show);
            }
            //end of the view function
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

