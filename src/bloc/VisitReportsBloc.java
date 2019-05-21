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
public class VisitReportsBloc {
  
    mediator md = mediator.md();
    
    private static final VisitReportsBloc VISITREPORT_BLOC = new VisitReportsBloc();
    
    ObservableList<Subjects> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private VisitReportsBloc() {
        this.data = FXCollections.observableArrayList();

    }

    public static VisitReportsBloc visitReportBloc() {
        return VISITREPORT_BLOC;
    }

}
