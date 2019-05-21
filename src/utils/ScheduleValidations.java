/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import dao.DBConnect;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class ScheduleValidations {
    
    static Integer subjectnumber;
    static Integer vsnumber;
    private static final ScheduleValidations SCHDULE = new ScheduleValidations(subjectnumber, vsnumber);

    private ScheduleValidations(Integer sub, Integer vsnum) {
        subjectnumber = sub;
        vsnumber = vsnum;
    }

    public static ScheduleValidations validate(Integer sub, Integer vsnum) {
        subjectnumber = sub;
        vsnumber = vsnum;
        return SCHDULE;
    }
    
    
    private Integer getLastVisit(){
        String lastVisit = DBConnect.getInstance().findLastVisit(subjectnumber).getVsvisit();
        String[] splitLastVisis = lastVisit.split(" ");
        Integer last = Integer.parseInt(splitLastVisis[1]);
        return last;
    }
    
    public boolean validDate(){
     if (vsnumber - getLastVisit() >1){
         int nextVisit = getLastVisit()  + 1;
         mediator.md().note("Error", "Please visit "+nextVisit +" needs to be performed");
         return false;
     }
        return true;
    }
    
    
    
}

