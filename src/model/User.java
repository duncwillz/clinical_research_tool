/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Collection;
import java.util.Date;

/**
 *
 * @author kwakuadjei
 */

public class User  {

   
    private Integer uid;
    private String uname;
    private String upass;
    private String ufullname;
    private String udepartment;
    private Date udate;

    private Collection<Daily> dailyCollection;

    public User() {
    }

    public User(Integer uid) {
        this.uid = uid;
    }

    public User(Integer uid, String uname, String upass, String ufullname, String udepartment, Date udate) {
        this.uid = uid;
        this.uname = uname;
        this.upass = upass;
        this.ufullname = ufullname;
        this.udepartment = udepartment;
        this.udate = udate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public String getUfullname() {
        return ufullname;
    }

    public void setUfullname(String ufullname) {
        this.ufullname = ufullname;
    }

    public String getUdepartment() {
        return udepartment;
    }

    public void setUdepartment(String udepartment) {
        this.udepartment = udepartment;
    }

    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }

    public Collection<Daily> getDailyCollection() {
        return dailyCollection;
    }

    public void setDailyCollection(Collection<Daily> dailyCollection) {
        this.dailyCollection = dailyCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.User[ uid=" + uid + " ]";
    }
    
}
