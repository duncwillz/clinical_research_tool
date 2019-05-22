/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bloc;

import dao.DBConnect;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Daily;
import model.Inpatients;
import model.Intervals;
import model.Visits;
import utils.mediator;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class IntervalBloc {

    mediator md = mediator.md();
    
    private static final IntervalBloc INT_BLOC = new IntervalBloc();
    
    ObservableList<Intervals> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private IntervalBloc() {
        this.data = FXCollections.observableArrayList();
    }

    public static IntervalBloc intervalBloc() {
        return INT_BLOC;
    }
    
    
    public void onVisitSelected(ComboBox combo, TableView tableView, TableColumn... col){
          try {
            combo.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    if (t1.equals("All") || t1.equals("Search by Visit")) {
                        combo.getSelectionModel().clearSelection();
                    }
                    populateTable(tableView,combo, col);
  
                }
            });
        } catch (Exception e) {
        }
    }
    
    public void populateTable(TableView tableView, ComboBox comb, TableColumn... col){
        col[0].setCellValueFactory(new PropertyValueFactory<>("vssubjectnumber"));
        col[1].setCellValueFactory(new PropertyValueFactory<>("vsvisitdate"));
        col[2].setCellValueFactory(new PropertyValueFactory<>("startDate"));
        col[3].setCellValueFactory(new PropertyValueFactory<>("endDate"));
        col[4].setCellValueFactory(new PropertyValueFactory<>("daysLeft"));
        data = FXCollections.observableArrayList();
        List<Intervals> lists = DBConnect.getInstance().getAllIntervals(comb.getValue().toString());
        System.out.println(lists);
        data.addAll(lists);
        tableView.setItems(data);
      }
    
     public void populateCombox(ComboBox combos) {
        combos.getItems().addAll("Visit 6");
        combos.setValue("Select");
    }
    
     
    public void autoSearch(TextField textField, TableView tableView){
     FilteredList<Intervals> filteredData = new FilteredList<>(data, e -> true);
        textField.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Intervals>) intervals -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return Integer.toString(intervals.getSubjectnumber()).toLowerCase().contains(newValue.toLowerCase());
            });
        });
        SortedList<Intervals> sortdata = new SortedList<>(filteredData);
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
