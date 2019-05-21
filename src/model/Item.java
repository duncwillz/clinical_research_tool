/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.Date;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Item {
     private int iid;
     private String iname;
     private Date idate;
     private int iuser;

    public Item(int iid, String iname, Date idate, int iuser) {
        this.iid = iid;
        this.iname = iname;
        this.idate = idate;
        this.iuser = iuser;
    }

    public Item() {
    }
    
    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public Date getIdate() {
        return idate;
    }

    public void setIdate(Date idate) {
        this.idate = idate;
    }

    public int getIuser() {
        return iuser;
    }

    public void setIuser(int iuser) {
        this.iuser = iuser;
    }
             
    
}
