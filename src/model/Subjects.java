/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.Date;
/**
 * 
 * @author kwakuadjei
 */

public class Subjects {

  
    private Integer sid;
    private Integer snumber;
    private String sgroup;
    private String sname;
    private Date sdob;
    private String sgender;
    private String scommunity;
    private Date sdatecreated;

    public Subjects() {
    }

    public Subjects(Integer sid) {
        this.sid = sid;
    }

    public String getSgender() {
        return sgender;
    }

    public void setSgender(String sgender) {
        this.sgender = sgender;
    }
    
    

    public Subjects(Integer sid, Integer snumber, String sgroup, String sname, Date sdob, String sgender, String scommunity, Date sdatecreated) {
        this.sid = sid;
        this.snumber = snumber;
        this.sgroup = sgroup;
        this.sname = sname;
        this.sdob = sdob;
        this.sgender = sgender;
        this.scommunity = scommunity;
        this.sdatecreated = sdatecreated;
    }

 

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSgroup() {
        return sgroup;
    }

    public void setSgroup(String sgroup) {
        this.sgroup = sgroup;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Date getSdob() {
        return sdob;
    }

    public void setSdob(Date sdob) {
        this.sdob = sdob;
    }

    public String getScommunity() {
        return scommunity;
    }

    public Integer getSnumber() {
        return snumber;
    }

    public void setSnumber(Integer snumber) {
        this.snumber = snumber;
    }

    public void setScommunity(String scommunity) {
        this.scommunity = scommunity;
    }

    public Date getSdatecreated() {
        return sdatecreated;
    }

    public void setSdatecreated(Date sdatecreated) {
        this.sdatecreated = sdatecreated;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sid != null ? sid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subjects)) {
            return false;
        }
        Subjects other = (Subjects) object;
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Subjects[ sid=" + sid + " ]";
    }

}
