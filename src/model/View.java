/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class View {

   private String title;
   private String details;

    public View(String title, String details) {
        this.title = title;
        this.details = details;
    }
    
    public View(){
    }
   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
   
    
}
