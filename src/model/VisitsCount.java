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
public class VisitsCount {
    private String visit;
    private Integer counts;

    public VisitsCount(String visit, Integer counts) {
        this.visit = visit;
        this.counts = counts;
    }

    public VisitsCount() {
    }

    
    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }
    
}
