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
public class Receive {
    private int riid;
    private String risupplier;
    private int riinvoice;
    private int rinumb;
    private int riuser;
    private Date riexpiry;
    private Date ridate;
    private int riitem;
    private String ridescription;
    private String other;

    public Receive() {
    }

    public Receive(int riid, String risupplier, int riinvoice, int rinumb, int riuser, Date riexpiry, Date ridate, int riitem, String ridescription, String other) {
        this.riid = riid;
        this.risupplier = risupplier;
        this.riinvoice = riinvoice;
        this.rinumb = rinumb;
        this.riuser = riuser;
        this.riexpiry = riexpiry;
        this.ridate = ridate;
        this.riitem = riitem;
        this.ridescription = ridescription;
        this.other = other;
    }

    public int getRiid() {
        return riid;
    }

    public void setRiid(int riid) {
        this.riid = riid;
    }

    public String getRisupplier() {
        return risupplier;
    }

    public void setRisupplier(String risupplier) {
        this.risupplier = risupplier;
    }

    public int getRiinvoice() {
        return riinvoice;
    }

    public void setRiinvoice(int riinvoice) {
        this.riinvoice = riinvoice;
    }

    public int getRinumb() {
        return rinumb;
    }

    public void setRinumb(int rinumb) {
        this.rinumb = rinumb;
    }

    public int getRiuser() {
        return riuser;
    }

    public void setRiuser(int riuser) {
        this.riuser = riuser;
    }

    public Date getRiexpiry() {
        return riexpiry;
    }

    public void setRiexpiry(Date riexpiry) {
        this.riexpiry = riexpiry;
    }

    public Date getRidate() {
        return ridate;
    }

    public void setRidate(Date ridate) {
        this.ridate = ridate;
    }

    public int getRiitem() {
        return riitem;
    }

    public void setRiitem(int riitem) {
        this.riitem = riitem;
    }

    public String getRidescription() {
        return ridescription;
    }

    public void setRidescription(String ridescription) {
        this.ridescription = ridescription;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
    
    
}
