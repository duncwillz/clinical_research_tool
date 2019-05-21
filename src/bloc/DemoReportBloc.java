/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloc;

import dao.DBConnect;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Subjects;
import model.Visits;
import model.Withdrawals;
import utils.mediator;

/**
 *
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class DemoReportBloc {

    mediator md = mediator.md();

    private static final DemoReportBloc DEMOREPORT_BLOC = new DemoReportBloc();

    ObservableList<Subjects> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private DemoReportBloc() {
        this.data = FXCollections.observableArrayList();

    }

    public static DemoReportBloc demoReportBloc() {
        return DEMOREPORT_BLOC;
    }

    public void populateTable(TableView tableView, TableColumn... col) {
        col[0].setCellValueFactory(new PropertyValueFactory<>("snumber"));
        col[1].setCellValueFactory(new PropertyValueFactory<>("sname"));
        data = FXCollections.observableArrayList();
        data.addAll(DBConnect.getInstance().findAllSubjects());
        tableView.setItems(data);
    }

    public void autoSearch(TextField searchText, TableView tableView) {
        FilteredList<Subjects> filteredData = new FilteredList<>(data, e -> true);
        searchText.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Subjects>) subject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                if (Integer.toString(subject.getSnumber()).contains(newValue.toLowerCase())) {
                    return true;
                }
                if (subject.getSname().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Subjects> sortdata = new SortedList<>(filteredData);
        sortdata.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortdata);
    }

    public void setLabels(Subjects s, String status, Label... l) {
        l[0].setText(s.getSnumber().toString());
        l[1].setText(s.getSname());
        l[2].setText(format.format(s.getSdob()));
        l[3].setText(s.getSgender());
        l[4].setText(s.getScommunity());
        l[5].setText("Not added yet");
        l[6].setText(s.getSgroup());
        l[7].setText("Not added yet");
        l[8].setText(md.getCurrentAgeFromDOB(s.getSdob()));
        l[9].setText(status);
        if (status.equals("Withdrawn")) {
            l[9].setTextFill(Color.web("#FF0000"));
        } else {
            l[9].setTextFill(Color.web("#32CD32"));
        }
    }

    public void onViewSelected(TableView table, Label... l) {
        if (table.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Error", "Please select at the subject to view");
        } else {
            List<Withdrawals> w = DBConnect.getInstance().getAllWithdrawals();
            List<Integer> withnumber = new ArrayList<>();
            Subjects s = (Subjects) table.getSelectionModel().getSelectedItem();
            w.forEach((n) -> withnumber.add(n.getWsubjectnumber()));
            if (withnumber.contains(s.getSnumber())) {
                setLabels(s, "Withdrawn", l);
            } else {
                setLabels(s, "Valid", l);
            }
        }
    }

    public void refreshAll(Label... l) {
        for (Label l1 : l) {
            l1.setText("Not selected");
        }
    }

    public void onChangeKey(TableView table, Label... l) {
        table.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (null == event.getCode()) {
                    System.out.println("This key not known");
                }
                else switch (event.getCode()) {
                    case UP:
                        onViewSelected(table, l);
                        break;
                    case DOWN:
                        onViewSelected(table, l);
                        break;
                    default:
                        System.out.println("This key not known");
                        break;
                }
            }
        });

    }

    public boolean exportAction() {
        Stage pm = new Stage();
        String path = md.myPath(pm, "Choose directory");
        try {
            if (!path.isEmpty()) {
                if (DBConnect.getInstance().exportAllSubjects(path)) {
                    if (md.AlertSelected(Alert.AlertType.INFORMATION, "Success", "Exported successfully", "Click Ok to open your root directory to get the excel file", "Ok")) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            if (desktop.isSupported(Desktop.Action.OPEN)) {
                                desktop.open(new File(path));//                            desktop.open(new File("/Users/kwakuadjei/Desktop/Exports/"));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (NullPointerException il) {

        }
        return true;
    }

}
