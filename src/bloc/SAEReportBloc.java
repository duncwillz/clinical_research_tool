/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bloc;

import java.text.SimpleDateFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Subjects;
import utils.mediator;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class SAEReportBloc {
  
    mediator md = mediator.md();
    
    private static final SAEReportBloc SEAREPORT_BLOC = new SAEReportBloc();
    
    ObservableList<Subjects> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private SAEReportBloc() {
        this.data = FXCollections.observableArrayList();

    }

    public static SAEReportBloc SAEReportBloc() {
        return SEAREPORT_BLOC;
    }

}
