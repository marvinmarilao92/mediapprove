package com.example.mediaapprove.ui.Beneficiaries.adapter;

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
import com.example.mediaapprove.ui.Beneficiaries.BeneFragment;
import com.example.mediaapprove.ui.Beneficiaries.BeneParam;

import java.util.List;

public class BeneAdapter extends RecyclerView.Adapter<BeneAdapter.MyViewHolder> {

    Context context;  //It lets newly-created objects understand what has been going on.
    private List<BeneParam> ArrayListEvents;   //call BeneParam


    public BeneAdapter(@NonNull Context context, List<BeneParam> arrayListEvents) {

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

        BeneParam data = ArrayListEvents.get(position); //position of the data from the recyclerview
        holder.texttitle.setText(data.getName()); //set title into a recycler view
        holder.textdate.setText(data.getPlan()); //set date into a recyclerview

        //BUTTON VIEW
        //if you click the view toggle the dialog will appear and shows the EventsFragment details
        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            //Object used for view admin fragments
            TextView tvid,tvlogo,tvRid,tvfname,tvplan,tvamount,tvgen,tvdob,tvcontact,tvtel,tvstart,tvexp,tvstat,tvACP,tvTPE,tvTHE,tvTPOE,btn_show;
            @Override
            public void onClick(View v) {

                BeneParam beneParam = ArrayListEvents.get(holder.getAdapterPosition());
                Dialog dialog = new Dialog(context);
                //set Content view
                dialog.setContentView(R.layout.view_bene);
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
                tvstat = dialog.findViewById(R.id.txtstat);
                tvACP = dialog.findViewById(R.id.txtACP);
                tvTPE = dialog.findViewById(R.id.txtTPE);
                tvTHE = dialog.findViewById(R.id.txtTHE);
                tvTPOE = dialog.findViewById(R.id.txtTPOE);
                btn_show = dialog.findViewById(R.id.showVIewTxt);

                //insertion of parameters data into TextView
                tvRid.setText("Residence No: "+ BeneFragment.BenearrayList.get(position).getRid());
                tvfname.setText("FullName: "+ BeneFragment.BenearrayList.get(position).getName());
                tvplan.setText("Plan: "+ BeneFragment.BenearrayList.get(position).getPlan());

                tvamount.setText("Amount: "+ BeneFragment.BenearrayList.get(position).getAmount());
                //tvgen.setText("Gen: "+ BeneFragment.BenearrayList.get(position).getCourse());
                tvdob.setText("Date: "+ BeneFragment.BenearrayList.get(position).getDob());
                tvcontact.setText("Contact: "+ BeneFragment.BenearrayList.get(position).getContact());
                tvtel.setText("Telephone: "+ BeneFragment.BenearrayList.get(position).getTel());
                tvstart.setText("Start: "+ BeneFragment.BenearrayList.get(position).getStart());
                tvexp.setText("Expiration: "+ BeneFragment.BenearrayList.get(position).getExp());
                tvstat.setText("Status: "+ BeneFragment.BenearrayList.get(position).getStat());
                tvACP.setText("Covered: "+ BeneFragment.BenearrayList.get(position).getACP());
                tvTPE.setText("Procedure end: "+ BeneFragment.BenearrayList.get(position).getTPE());
                tvTHE.setText("Hospital end: "+ BeneFragment.BenearrayList.get(position).getTHE());
                tvTPOE.setText("Optical End: "+ BeneFragment.BenearrayList.get(position).getTPOE());

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
                tvstat.setVisibility(View.VISIBLE);
                tvACP.setVisibility(View.VISIBLE);
                tvTPE.setVisibility(View.VISIBLE);
                tvTHE.setVisibility(View.VISIBLE);
                tvTPOE.setVisibility(View.VISIBLE);

                btn_show.setText("Hide Additional Info");
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
                tvstat.setVisibility(View.GONE);
                tvACP.setVisibility(View.GONE);
                tvTPE.setVisibility(View.GONE);
                tvTHE.setVisibility(View.GONE);
                tvTPOE.setVisibility(View.GONE);

                btn_show.setText("Show Additional Info");
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

