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
public class Withdrawals {
    private Integer wid;
    private Integer wsubjectnumber;
    private Date wdatewithdrawal;
    private String wreasonwithdrawal;
    private String wdecisionby;
    private Integer wuser;
    private Date wdatecreated;

    public Withdrawals() {
    }

    public Withdrawals(Integer wid) {
        this.wid = wid;
    }

    public Withdrawals(Integer wid, Integer wsubjectnumber, Date wdatewithdrawal, String wreasonwithdrawal, String wdecisionby, Integer wuser, Date wdatecreated) {
        this.wid = wid;
        this.wsubjectnumber = wsubjectnumber;
        this.wdatewithdrawal = wdatewithdrawal;
        this.wreasonwithdrawal = wreasonwithdrawal;
        this.wdecisionby = wdecisionby;
        this.wuser = wuser;
        this.wdatecreated = wdatecreated;
    }

    public String getWdecisionby() {
        return wdecisionby;
    }

    public void setWdecisionby(String wdecisionby) {
        this.wdecisionby = wdecisionby;
    }
    

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public Integer getWsubjectnumber() {
        return wsubjectnumber;
    }

    public void setWsubjectnumber(Integer wsubjectnumber) {
        this.wsubjectnumber = wsubjectnumber;
    }

    public Date getWdatewithdrawal() {
        return wdatewithdrawal;
    }

    public void setWdatewithdrawal(Date wdatewithdrawal) {
        this.wdatewithdrawal = wdatewithdrawal;
    }

    public String getWreasonwithdrawal() {
        return wreasonwithdrawal;
    }

    public void setWreasonwithdrawal(String wreasonwithdrawal) {
        this.wreasonwithdrawal = wreasonwithdrawal;
    }

    public Integer getWuser() {
        return wuser;
    }

    public void setWuser(Integer wuser) {
        this.wuser = wuser;
    }

    public Date getWdatecreated() {
        return wdatecreated;
    }

    public void setWdatecreated(Date wdatecreated) {
        this.wdatecreated = wdatecreated;
    }
    
    
}
