package com.example.mediaapprove.ui.Beneficiaries;

public class BeneParam {
    private String bene_id,bene_Rid,bene_name,bene_plan,bene_amt,bene_gen,
            bene_dob,bene_no,bene_tel,bene_start,bene_exp,bene_stat,bene_ACP,
            bene_TPE,bene_THE,bene_TPOE;
    //boolean checked;

    public BeneParam(String id, String Rid,String name, String plan,String amount, String gen
            ,String dob, String contact,String tel, String start,String exp, String stat
            ,String ACP, String TPE,String THE, String TPOE)//boolean checked
    {
        //preparing the parameter
        this.bene_id = id;
        this.bene_Rid = Rid;
        this.bene_name = name;
        this.bene_plan = plan;
        this.bene_amt = amount;
        this.bene_gen = gen;
        this.bene_dob = dob;
        this.bene_no = contact;
        this.bene_tel = tel;
        this.bene_start = start;
        this.bene_exp = exp;
        this.bene_stat = stat;
        this.bene_ACP = ACP;
        this.bene_TPE = TPE;
        this.bene_THE = THE;
        this.bene_TPOE = TPOE;

    }

//    get temporary storage
    public String getId() {
        return bene_id;
    }

    public String getRid() {
        return bene_Rid;
    }

    public String getName() {
        return bene_name;
    }

    public String getPlan() {
        return bene_plan;
    }

    public String getAmount() {
        return bene_amt;
    }

    public String getGen() {
        return bene_gen;
    }

    public String getDob() {
        return bene_dob;
    }

    public String getContact() {
        return bene_no;
    }

    public String getTel() {
        return bene_tel;
    }

    public String getStart() {
        return bene_start;
    }

    public String getExp() {
        return bene_exp;
    }

    public String getStat() {
        return bene_stat;
    }

    public String getACP() {
        return bene_ACP;
    }

    public String getTPE() {
        return bene_TPE;
    }

    public String getTHE() {
        return bene_THE;
    }

    public String getTPOE() {
        return bene_TPOE;
    }

    //  public Boolean getChecked() {return checked;}

    //set temporary storage
    public void setId(String id) {
        this.bene_id = id;
    }

    public void setRid(String Rid) {
        this.bene_Rid = Rid;
    }

    public void setName(String name) {
        this.bene_name = name;
    }

    public void setPlan(String plan) {
        this.bene_plan = plan;
    }

    public void setAmount(String amount) {
        this.bene_amt = amount;
    }

    public void setGen(String gen) {
        this.bene_gen = gen;
    }

    public void setDob(String dob) {
        this.bene_dob = dob;
    }

    public void setContact(String contact) {
        this.bene_no = contact;
    }

    public void setTel(String tel) {
        this.bene_tel = tel;
    }

    public void setStart(String start) {
        this.bene_start = start;
    }

    public void setExp(String exp) {
        this.bene_exp = exp;
    }

    public void setStat(String stat) {
        this.bene_stat = stat;
    }

    public void setACP(String ACP) {
        this.bene_ACP = ACP;
    }

    public void setTPE(String TPE) {
        this.bene_TPE = TPE;
    }

    public void setTHE(String THE) {
        this.bene_THE = THE;
    }

    public void setTPOE(String TPOE) {
        this.bene_TPOE = TPOE;
    }
    

    //  public void setChecked(boolean checked) { this.checked = checked};
}

/* storing data to manipulate or become a bridge between database and system */
