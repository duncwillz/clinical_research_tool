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
public class ProScheduleReportBloc {
  mediator md = mediator.md();
    
    private static final ProScheduleReportBloc PRO_SCHEDULING_BLOC = new ProScheduleReportBloc();
    
    ObservableList<Subjects> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private ProScheduleReportBloc() {
        this.data = FXCollections.observableArrayList();

    }

    public static ProScheduleReportBloc proScheduleReportBloc() {
        return PRO_SCHEDULING_BLOC;
    }
}
