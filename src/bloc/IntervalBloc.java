/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloc;

import dao.DBConnect;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javafx.stage.Stage;
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
    List<Intervals> lists;
    ObservableList<Intervals> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");
    private final SimpleDateFormat researchformat = new SimpleDateFormat("dd-MMM-yyyy");

    private IntervalBloc() {
        this.data = FXCollections.observableArrayList();
        this.lists = new ArrayList<>();
    }

    public static IntervalBloc intervalBloc() {
        return INT_BLOC;
    }

    public void onVisitSelected(ComboBox combo, TableView tableView, TableColumn... col) {
        try {
            combo.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    if (t1.equals("All") || t1.equals("Search by Visit")) {
                        combo.getSelectionModel().clearSelection();
                    }
                    populateTable(tableView, combo, col);

                }
            });
        } catch (Exception e) {
        }
    }

    public void populateTable(TableView tableView, ComboBox comb, TableColumn... col) {
        col[0].setCellValueFactory(new PropertyValueFactory<>("subjectnumber"));
        col[1].setCellValueFactory(new PropertyValueFactory<>("previousVisit"));
        col[2].setCellValueFactory(new PropertyValueFactory<>("startDate"));
        col[3].setCellValueFactory(new PropertyValueFactory<>("endDate"));
        col[4].setCellValueFactory(new PropertyValueFactory<>("daysLeft"));
        for (int i = 0; i < col.length; i++) {
            if (i == 1 || i == 2 || i == 3) {
                col[i].setCellFactory(column -> setCellFormat());
            }
        }
        data = FXCollections.observableArrayList();
        System.out.println("Combo selection ----> " + comb.getValue().toString());
        if (comb.getValue().toString().equals("Please Select Visit")) {
            System.out.println("Initial Entry");
        } else {
            this.lists = DBConnect.getInstance().getAllIntervals(md.dependentVisits(comb.getValue().toString()));
            System.out.println(this.lists);
            data.addAll(this.lists);
            tableView.setItems(data);
        }

    }

    private TableCell setCellFormat() {
        TableCell<Visits, Date> cell = new TableCell<Visits, Date>() {

            @Override
            protected void updateItem(Date item, boolean empty) {
                try {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText("");
                    } else {
                        setText(format.format(item));
                    }
                } catch (Exception es) {
                }
            }
        };
        return cell;
    }

    public void populateCombox(ComboBox combos) {
        combos.getItems().addAll("Visit 26", "Visit 29", "Visit 32", "Visit 35", "Visit 36", "Visit 37", "Visit 38");
        combos.setValue("Please Select Visit");
    }

    public void autoSearch(TextField textField, TableView tableView) {
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
                        setText(researchformat.format(item));
                    }
                } catch (Exception ex) {
                }
            }
        };
        return cell;
    }

    public void exportToExcel(String previousVisit, String visit) throws Exception {
        Writer writer = null;
        try {
            Stage pm = new Stage();
            String path_ = md.myPath(pm, "Choose directory");
//            path_ = "Alternative";
            if (!path_.isEmpty()) {
                File file = new File(path_+"/VisitIntervals "+visit+".csv");
                writer = new BufferedWriter(new FileWriter(file));
                String title = "PID," +previousVisit+",Start Date,End Date,Days Left"+ "\n";
                writer.write(title);
                for (Intervals interval : this.lists) {
                    String text = interval.getSubjectnumber() + "," + researchformat.format(interval.getPreviousVisit()) + "," + researchformat.format(interval.getStartDate()) + "," + researchformat.format(interval.getEndDate()) + "," + interval.getDaysLeft() + "\n";
                    writer.write(text);
                }
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(path_));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }

    public boolean validateExport(ComboBox comb) {
        if (comb.getValue() == null) {
            md.note("Error", "Sorry you can not export without visit");
            return false;
        }
        return true;
    }

}
