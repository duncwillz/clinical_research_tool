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
public class AEReportBloc {
 
    mediator md = mediator.md();
    
    private static final AEReportBloc AEREPORT_BLOC = new AEReportBloc();
    
    ObservableList<Subjects> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private AEReportBloc() {
        this.data = FXCollections.observableArrayList();

    }

    public static AEReportBloc AEReportBloc() {
        return AEREPORT_BLOC;
    }

}
