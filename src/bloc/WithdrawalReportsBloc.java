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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Daily;
import model.Subjects;
import model.Withdrawals;
import utils.mediator;

/**
 *
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class WithdrawalReportsBloc {

    mediator md = mediator.md();

    private static final WithdrawalReportsBloc WITHDRAWAL_REPORT_BLOC = new WithdrawalReportsBloc();

    ObservableList<Withdrawals> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private WithdrawalReportsBloc() {
        this.data = FXCollections.observableArrayList();

    }

    public static WithdrawalReportsBloc withdrawalReportBloc() {
        return WITHDRAWAL_REPORT_BLOC;
    }

    public void populateTable(TableView tableView, TableColumn... col) {
        col[0].setCellValueFactory(new PropertyValueFactory<>("wsubjectnumber"));
        col[1].setCellValueFactory(new PropertyValueFactory<>("wdatewithdrawal"));
        col[1].setCellFactory(column -> {
            TableCell<Withdrawals, java.sql.Date> cell = new TableCell<Withdrawals, java.sql.Date>() {
                private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

                @Override
                protected void updateItem(java.sql.Date item, boolean empty) {
                    try {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(format.format(item));
                        }
                    } catch (Exception es) {
                    }
                }
            };
            return cell;
        });
        col[2].setCellValueFactory(new PropertyValueFactory<>("wdecisionby"));
        data = FXCollections.observableArrayList();
        data.addAll(DBConnect.getInstance().getAllWithdrawals());
        tableView.setItems(data);
    }

    public void autoSearch(TextField searchText, TableView tableView) {
        FilteredList<Withdrawals> filteredData = new FilteredList<>(data, e -> true);
        searchText.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Withdrawals>) withdrawals -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                if (Integer.toString(withdrawals.getWsubjectnumber()).contains(newValue.toLowerCase())) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Withdrawals> sortdata = new SortedList<>(filteredData);
        sortdata.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortdata);
    }

    public void setLabels(Withdrawals s, Label... l) {
        l[0].setText(s.getWsubjectnumber().toString());
        Subjects subject = DBConnect.getInstance().findSubjectsByNumber(s.getWsubjectnumber());
        l[1].setText(subject.getSname());
        l[2].setText(format.format(s.getWdatewithdrawal()));
        l[3].setText(s.getWreasonwithdrawal());
        l[4].setText(s.getWdecisionby());
        l[5].setText(DBConnect.getInstance().findUserByUserId(s.getWuser()).getUfullname());
    }

    public void onViewSelected(TableView table, Label... l) {
        if (table.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Error", "Please select at the subject to view");
        } else {
            Withdrawals s = (Withdrawals) table.getSelectionModel().getSelectedItem();
            setLabels(s, l);
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
                } else {
                    switch (event.getCode()) {
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
            }
        });

    }

    public void getTotal(Label l) {
        l.setText(DBConnect.getInstance().getTotalOfWithdrawal().toString());
    }

    public boolean exportAction() {
        Stage pm = new Stage();
        String path = md.myPath(pm, "Choose directory");
        try {
            if (!path.isEmpty()) {
                if (DBConnect.getInstance().exportAllWithdrawals(path)) {
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
