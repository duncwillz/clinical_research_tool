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
public class Inpatients {
  Integer inpid;
  Date inpadmissionDate;
  Date inpdischargeDate;
  Integer inpuserid;
  Date inpdateCreated;
  Integer inpsubject;

    public Inpatients() {
    }

    public Inpatients(Integer inpid) {
        this.inpid = inpid;
    }

    public Inpatients(Integer inpid, Date inpadmissionDate, Date inpdischargeDate, Integer inpuserid, Date inpdateCreated, Integer inpsubject) {
        this.inpid = inpid;
        this.inpadmissionDate = inpadmissionDate;
        this.inpdischargeDate = inpdischargeDate;
        this.inpuserid = inpuserid;
        this.inpdateCreated = inpdateCreated;
        this.inpsubject = inpsubject;
    }

    public Integer getInpid() {
        return inpid;
    }

    public void setInpid(Integer inpid) {
        this.inpid = inpid;
    }

    public Date getInpadmissionDate() {
        return inpadmissionDate;
    }

    public void setInpadmissionDate(Date inpadmissionDate) {
        this.inpadmissionDate = inpadmissionDate;
    }

    public Date getInpdischargeDate() {
        return inpdischargeDate;
    }

    public void setInpdischargeDate(Date inpdischargeDate) {
        this.inpdischargeDate = inpdischargeDate;
    }

    public Integer getInpuserid() {
        return inpuserid;
    }

    public void setInpuserid(Integer inpuserid) {
        this.inpuserid = inpuserid;
    }

    public Date getInpdateCreated() {
        return inpdateCreated;
    }

    public void setInpdateCreated(Date inpdateCreated) {
        this.inpdateCreated = inpdateCreated;
    }

    public Integer getInpsubject() {
        return inpsubject;
    }

    public void setInpsubject(Integer inpsubject) {
        this.inpsubject = inpsubject;
    }

  
}
