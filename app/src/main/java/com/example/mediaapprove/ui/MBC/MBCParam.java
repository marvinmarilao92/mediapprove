package com.example.mediaapprove.ui.MBC;

public class MBCParam {
    private String mbc_plan;
    //boolean checked;


    public MBCParam(String plan)//boolean checked
    {
        //preparing the parameter
        this.mbc_plan = plan;
        //  this.checked = checked;

    }

//    get storage

    public String getPlan() {
        return mbc_plan;
    }

    

    //  public Boolean getChecked() {return checked; }

    //set params
    public void setPlan(String plan) {
        this.mbc_plan = plan;
    }


    //  public void setChecked(boolean checked) { this.checked = checked;
}


/* storing data to manipulate or become a bridge between database and system */
