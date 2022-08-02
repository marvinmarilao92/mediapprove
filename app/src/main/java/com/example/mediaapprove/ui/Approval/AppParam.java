package com.example.mediaapprove.ui.Approval;

public class AppParam {
    private String app_TO,app_DN,app_EB,app_EP,app_Date,app_Rid
            ,app_Holdname,app_ReqStat,app_PP,app_PT,app_AD;
    //boolean checked;

    public AppParam(String TO, String DN, String EB, String EP, String Date, String Rid
            , String Holdname, String ReqStat, String PP, String PT, String AD)//boolean checked
    {
        //preparing the parameter
        this.app_TO = TO;
        this.app_DN = DN;
        this.app_EB = EB;
        this.app_EP = EP;
        this.app_Date = Date;
        this.app_Rid = Rid;
        this.app_Holdname = Holdname;
        this.app_ReqStat = ReqStat;
        this.app_PP = PP;
        this.app_PT = PT;
        this.app_AD = AD;

    }

//    get temporary storage
    public String getTO() {
        return app_TO;
    }

    public String getDN() {
        return app_DN;
    }

    public String getEB() {
        return app_EB;
    }

    public String getEP() {
        return app_EP;
    }

    public String getDate() {
        return app_Date;
    }

    public String getRid() {
        return app_Rid;
    }

    public String getHoldname() {
        return app_Holdname;
    }

    public String getReqStat() {
        return app_ReqStat;
    }

    public String getPP() {
        return app_PP;
    }

    public String getPT() {
        return app_PT;
    }

    public String getAD() {
        return app_AD;
    }


    //  public Boolean getChecked() {return checked;}

    //set temporary storage
    public void setTO(String TO) {
        this.app_TO = TO;
    }

    public void setDN(String DN) {
        this.app_DN = DN;
    }

    public void setEB(String EB) {
        this.app_EB = EB;
    }

    public void setEP(String EP) {
        this.app_EP = EP;
    }

    public void setDate(String Date) {
        this.app_Date = Date;
    }

    public void setRid(String Rid) {
        this.app_Rid = Rid;
    }

    public void setHoldname(String Holdname) {
        this.app_Holdname = Holdname;
    }

    public void setReqStat(String ReqStat) {
        this.app_ReqStat = ReqStat;
    }

    public void setPP(String PP) {
        this.app_PP = PP;
    }

    public void setPT(String PT) {
        this.app_PT = PT;
    }

    public void setAD(String AD) {
        this.app_AD = AD;
    }

    //  public void setChecked(boolean checked) { this.checked = checked};
}

/* storing data to manipulate or become a bridge between database and system */
