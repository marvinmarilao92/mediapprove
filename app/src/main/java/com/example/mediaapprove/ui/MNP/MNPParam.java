package com.example.mediaapprove.ui.MNP;

public class MNPParam {
    private String mnp_region,mnp_city,mnp_prov,mnp_type,mnp_add,mnp_tel,mnp_open;
    //boolean checked;

    public MNPParam( String reg, String city,String prov, String type,String add, String tel, String open)//boolean checked
    {
        //preparing the parameter
        this.mnp_region = reg;
        this.mnp_city = city;
        this.mnp_prov = prov;
        this.mnp_type = type;
        this.mnp_add = add;
        this.mnp_tel = tel;
        this.mnp_open = open;
        //  this.checked = checked;

    }

//    get storage

    public String getReg() {
        return mnp_region;
    }

    public String getCity() {
        return mnp_city;
    }

    public String getProv() {
        return mnp_prov;
    }

    public String getType() {
        return mnp_type;
    }

    public String getAdd() {
        return mnp_add;
    }

    public String getTel() {
        return mnp_tel;
    }

    public String getOpen() {
        return mnp_open;
    }

    //  public Boolean getChecked() {return checked; }

    //set params
    public void setReg(String reg) {
        this.mnp_region = reg;
    }

    public void setCity(String city) {
        this.mnp_city = city;
    }

    public void setProv(String prov) {
        this.mnp_prov = prov;
    }

    public void setType(String type) {
        this.mnp_type = type;
    }

    public void setAdd(String add) {
        this.mnp_add = add;
    }

    public void setTel(String tel) {
        this.mnp_tel = tel;
    }

    public void setOpen(String open) {
        this.mnp_open = open;
    }
    

    //  public void setChecked(boolean checked) { this.checked = checked;
}


/* storing data to manipulate or become a bridge between database and system */
