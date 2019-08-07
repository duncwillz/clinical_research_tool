/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.Date;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class Supplier {

    private Integer suid;
    private int suitem;
    private int susubjectid;
    private Date sudate;
    private int suuserid;
    private String suprescriber;
    private String sudescription;
    private Date sudatecreated;
    private int suitemnumber;

    public Supplier() {
    }

    public Supplier(Integer suid) {
        this.suid = suid;
    }

    public Supplier(Integer suid, int suitem, int susubjectid, Date sudate, int suuserid, Date sudatecreated, int suitemnumber) {
        this.suid = suid;
        this.suitem = suitem;
        this.susubjectid = susubjectid;
        this.sudate = sudate;
        this.suuserid = suuserid;
        this.sudatecreated = sudatecreated;
        this.suitemnumber = suitemnumber;
    }

    public Supplier(int suitem, int susubjectid, Date sudate, int suuserid, String suprescriber, String sudescription, Date sudatecreated, int suitemnumber) {
        this.suitem = suitem;
        this.susubjectid = susubjectid;
        this.sudate = sudate;
        this.suuserid = suuserid;
        this.suprescriber = suprescriber;
        this.sudescription = sudescription;
        this.sudatecreated = sudatecreated;
        this.suitemnumber = suitemnumber;
    }
    
    
    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public int getSuitem() {
        return suitem;
    }

    public void setSuitem(int suitem) {
        this.suitem = suitem;
    }

    public int getSusubjectid() {
        return susubjectid;
    }

    public void setSusubjectid(int susubjectid) {
        this.susubjectid = susubjectid;
    }

    public Date getSudate() {
        return sudate;
    }

    public void setSudate(Date sudate) {
        this.sudate = sudate;
    }

    public int getSuuserid() {
        return suuserid;
    }

    public void setSuuserid(int suuserid) {
        this.suuserid = suuserid;
    }

    public String getSuprescriber() {
        return suprescriber;
    }

    public void setSuprescriber(String suprescriber) {
        this.suprescriber = suprescriber;
    }

    public String getSudescription() {
        return sudescription;
    }

    public void setSudescription(String sudescription) {
        this.sudescription = sudescription;
    }

    public Date getSudatecreated() {
        return sudatecreated;
    }

    public void setSudatecreated(Date sudatecreated) {
        this.sudatecreated = sudatecreated;
    }

    public int getSuitemnumber() {
        return suitemnumber;
    }

    public void setSuitemnumber(int suitemnumber) {
        this.suitemnumber = suitemnumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (suid != null ? suid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((this.suid == null && other.suid != null) || (this.suid != null && !this.suid.equals(other.suid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Supplier[ suid=" + suid + " ]";
    }

}
