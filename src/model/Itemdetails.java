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
public class Itemdetails {
    private int idid;
    private int iditem;
    private int idtotalleft;
    private int iddate;

    public Itemdetails() {
    }

    public Itemdetails(int idid, int iditem, int idtotalleft, int iddate) {
        this.idid = idid;
        this.iditem = iditem;
        this.idtotalleft = idtotalleft;
        this.iddate = iddate;
    }

    public int getIdid() {
        return idid;
    }

    public void setIdid(int idid) {
        this.idid = idid;
    }

    public int getIditem() {
        return iditem;
    }

    public void setIditem(int iditem) {
        this.iditem = iditem;
    }

    public int getIdtotalleft() {
        return idtotalleft;
    }

    public void setIdtotalleft(int idtotalleft) {
        this.idtotalleft = idtotalleft;
    }

    public int getIddate() {
        return iddate;
    }

    public void setIddate(int iddate) {
        this.iddate = iddate;
    }
    
    
}
