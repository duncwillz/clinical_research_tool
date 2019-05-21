/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class Messaging {
 private Integer mid;
 private String mname;
 private String mphonenumber;
 private String memail;

    public Messaging(Integer mid) {
        this.mid = mid;
    }

    public Messaging(Integer mid, String mname, String mphonenumber, String memail) {
        this.mid = mid;
        this.mname = mname;
        this.mphonenumber = mphonenumber;
        this.memail = memail;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMphonenumber() {
        return mphonenumber;
    }

    public void setMphonenumber(String mphonenumber) {
        this.mphonenumber = mphonenumber;
    }

    public String getMemail() {
        return memail;
    }

    public void setMemail(String memail) {
        this.memail = memail;
    }
 
 
}
