/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bloc;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Daily;
import model.Inpatients;
import model.Visits;
import utils.mediator;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class IntervalBloc {

    mediator md = mediator.md();
    
    private static final IntervalBloc INT_BLOC = new IntervalBloc();
    
    ObservableList<Visits> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private IntervalBloc() {
        this.data = FXCollections.observableArrayList();
    }

    public static IntervalBloc intervalBloc() {
        return INT_BLOC;
    }
    
    
    public void onVisitSelected(ComboBox combo, TableView tableView){
          try {
            combo.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    if (t1.equals("All") || t1.equals("Search by Visit")) {
                        combo.getSelectionModel().clearSelection();
                    }
                    populateTable(tableView);
  
                }
            });
        } catch (Exception e) {
        }
    }
    
    public void populateTable(TableView tableView){
    
    
    
    }
    
     public void populateCombox(ComboBox combos) {
        combos.getItems().addAll("Visit 24,Visit 26");
    }
    
     
    public void autoSearch(TextField textField, TableView tableView){
     FilteredList<Visits> filteredData = new FilteredList<>(data, e -> true);
        textField.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
//            filteredData.setPredicate((Predicate<? super Visits>) daily -> {
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//                
//                return newValue;
//            });
        });
        SortedList<Visits> sortdata = new SortedList<>(filteredData);
        sortdata.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortdata);
    
    }
    
    
     public TableCell returnCell(String pattern) {
        TableCell<Visits, Date> cell = new TableCell<Visits, Date>() {

            @Override
            protected void updateItem(Date item, boolean empty) {
                try {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                } catch (Exception ex) {
                }
            }
        };
        return cell;
    }
    
}
