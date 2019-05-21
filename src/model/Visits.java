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
public class Visits {
 private Integer vsid;
 private Integer vsuserid;
 private Integer vssubjectnumber;
 private String vsvisit;
 private Date vsvisitdate;
 private Date vsdatecreated;
 private Integer vsskipped;

    public Visits() {
    }

    
    public Visits(Integer vsid) {
        this.vsid = vsid;
    }

    public Visits(Integer vsid, Integer vsuserid, Integer vssubjectnumber, String vsvisit, Date vsvisitdate, Date vsdatecreated, Integer vsskipped) {
        this.vsid = vsid;
        this.vsuserid = vsuserid;
        this.vssubjectnumber = vssubjectnumber;
        this.vsvisit = vsvisit;
        this.vsvisitdate = vsvisitdate;
        this.vsdatecreated = vsdatecreated;
        this.vsskipped = vsskipped;
    }

    public Integer getVsskipped() {
        return vsskipped;
    }

    public void setVsskipped(Integer vsskipped) {
        this.vsskipped = vsskipped;
    }

    
 
    public Integer getVsid() {
        return vsid;
    }

    public void setVsid(Integer vsid) {
        this.vsid = vsid;
    }

    public Integer getVsuserid() {
        return vsuserid;
    }

    public void setVsuserid(Integer vsuserid) {
        this.vsuserid = vsuserid;
    }

    public Integer getVssubjectnumber() {
        return vssubjectnumber;
    }

    public void setVssubjectnumber(Integer vssubjectnumber) {
        this.vssubjectnumber = vssubjectnumber;
    }

    public String getVsvisit() {
        return vsvisit;
    }

    public void setVsvisit(String vsvisit) {
        this.vsvisit = vsvisit;
    }

    public Date getVsvisitdate() {
        return vsvisitdate;
    }

    public void setVsvisitdate(Date vsvisitdate) {
        this.vsvisitdate = vsvisitdate;
    }

    public Date getVsdatecreated() {
        return vsdatecreated;
    }

    public void setVsdatecreated(Date vsdatecreated) {
        this.vsdatecreated = vsdatecreated;
    }
 
 
 
}
