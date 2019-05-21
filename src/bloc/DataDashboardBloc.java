/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bloc;

import dao.DBConnect;
import java.text.SimpleDateFormat;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import model.Visits;
import utils.mediator;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class DataDashboardBloc {

    
    mediator md = mediator.md();
    Visits visits = new Visits();
    private int ttAEs, ttSAEs, ttMales, ttFemales, ttVisitPerform, ttVisitsSkipped ;
    
    private static final DataDashboardBloc DATADASHBOARD_BLOC = new DataDashboardBloc();
    ObservableList<Visits> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private DataDashboardBloc() {
        this.data = FXCollections.observableArrayList();

    }

    public static DataDashboardBloc dataDashboardBloc() {
        return DATADASHBOARD_BLOC;
    }

    
    
    
     public void loadPie(PieChart piename, int one, int two,  String... titles) {
       
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data(titles[0], one),
                new PieChart.Data(titles[1], two)
        );
        
        piename.setData(list);
        piename.setTitle(titles[2]);
        piename.setMaxSize(500, 500);
        piename.setMinSize(350, 350);
        piename.getData().stream().forEach(data -> {
            data.nameProperty().bind(
                Bindings.concat(
                        data.getName(), ":", data.pieValueProperty()
                )
              );
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            });
        });
    }
     
    public void populatePires(PieChart ...peiname){
//      Gender distribution
        ttMales = 400; ttFemales = 350; 
        loadPie(peiname[0], ttMales, ttFemales, "Male","Females","Gender distribution");
//      Visits Performed and Skipped
        ttVisitPerform = 100; ttVisitsSkipped = 150; 
        loadPie(peiname[1], ttVisitPerform, ttVisitsSkipped, "Visits Performed","Visits Skipped","Visit performed distribution");
//      AES and SAE distribution
        ttAEs = 200; ttSAEs = 500; 
        loadPie(peiname[2], ttAEs, ttSAEs, "AEs","SAEs","AE and SAE distribution");  
        
    }
    
    public void populateVisits(){
    
    
    }
     

}
