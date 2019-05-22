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
public class Intervals {
    private int subjectnumber;
    private Date previousVisit;
    private Date startDate;
    private Date endDate;
    private int daysLeft;

    public Intervals(int subjectnumber, Date previousVisit) {
        this.subjectnumber = subjectnumber;
        this.previousVisit = previousVisit;
    }

    public Intervals(int subjectnumber, Date previousVisit, Date startDate, Date endDate, int daysLeft) {
        this.subjectnumber = subjectnumber;
        this.previousVisit = previousVisit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysLeft = daysLeft;
    }

    public Intervals(int subjectnumber) {
        this.subjectnumber = subjectnumber;
    }

    public int getSubjectnumber() {
        return subjectnumber;
    }

    public void setSubjectnumber(int subjectnumber) {
        this.subjectnumber = subjectnumber;
    }

    public Date getPreviousVisit() {
        return previousVisit;
    }

    public void setPreviousVisit(Date previousVisit) {
        this.previousVisit = previousVisit;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }
    
    
    
    
}
