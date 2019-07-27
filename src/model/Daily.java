/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


/**
 *
 * @author kwakuadjei
 */

public class Daily  {


    private Integer did;
    private double dweight;
    private double dtemp;
    private int dsubjectnumber;
    private String dcasetype;
    private String dfeverstate;
    private java.sql.Timestamp ddatecreated;
    private Time dtime;
    private Date ddate;
    private int duser;

    public Daily() {
    }

    public Daily(Integer did, double dweight, double dtemp, int dsubjectnumber, String dcasetype, String dfeverstate, Timestamp ddatecreated, Time dtime, Date ddate, int duser) {
        this.did = did;
        this.dweight = dweight;
        this.dtemp = dtemp;
        this.dsubjectnumber = dsubjectnumber;
        this.dcasetype = dcasetype;
        this.dfeverstate = dfeverstate;
        this.ddatecreated = ddatecreated;
        this.dtime = dtime;
        this.ddate = ddate;
        this.duser = duser;
    }

    public Daily(double dweight, double dtemp, int dsubjectnumber, String dcasetype, String dfeverstate, Timestamp ddatecreated, Time dtime, Date ddate, int duser) {
        this.dweight = dweight;
        this.dtemp = dtemp;
        this.dsubjectnumber = dsubjectnumber;
        this.dcasetype = dcasetype;
        this.dfeverstate = dfeverstate;
        this.ddatecreated = ddatecreated;
        this.dtime = dtime;
        this.ddate = ddate;
        this.duser = duser;
    }

    public Date getDdate() {
        return ddate;
    }

    public void setDdate(Date ddate) {
        this.ddate = ddate;
    }

    

    public Time getDtime() {
        return dtime;
    }

    public void setDtime(Time dtime) {
        this.dtime = dtime;
    }

    
    
    public Daily(Integer did) {
        this.did = did;
    }

  
    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public double getDweight() {
        return dweight;
    }

    public void setDweight(double dweight) {
        this.dweight = dweight;
    }

    public double getDtemp() {
        return dtemp;
    }

    public void setDtemp(double dtemp) {
        this.dtemp = dtemp;
    }

    public int getDsubjectnumber() {
        return dsubjectnumber;
    }

    public void setDsubjectnumber(int dsubjectnumber) {
        this.dsubjectnumber = dsubjectnumber;
    }

    public String getDcasetype() {
        return dcasetype;
    }

    public void setDcasetype(String dcasetype) {
        this.dcasetype = dcasetype;
    }

    public String getDfeverstate() {
        return dfeverstate;
    }

    public void setDfeverstate(String dfeverstate) {
        this.dfeverstate = dfeverstate;
    }

    public java.sql.Timestamp getDdatecreated() {
        return ddatecreated;
    }

    public void setDdatecreated(java.sql.Timestamp ddatecreated) {
        this.ddatecreated = ddatecreated;
    }

    public int getDuser() {
        return duser;
    }

    public void setDuser(int duser) {
        this.duser = duser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (did != null ? did.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Daily)) {
            return false;
        }
        Daily other = (Daily) object;
        if ((this.did == null && other.did != null) || (this.did != null && !this.did.equals(other.did))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Daily[ did=" + did + " ]";
    }
    
}
