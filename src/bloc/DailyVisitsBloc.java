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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Item;
import model.Subjects;
import model.View;
import model.Visits;
import utils.ScheduleValidations;
import utils.mediator;

/**
 *
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class DailyVisitsBloc {

    mediator md = mediator.md();
    Visits visits = new Visits();
    private static final DailyVisitsBloc DAILYVISIT_BLOC = new DailyVisitsBloc();
    ObservableList<Visits> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private DailyVisitsBloc() {
        this.data = FXCollections.observableArrayList();

    }

    public static DailyVisitsBloc dvBloc() {
        return DAILYVISIT_BLOC;
    }

    public void populateCombox(ComboBox... combos) {
        combos[1].getItems().clear();
        combos[0].getItems().clear();
        combos[1].getItems().addAll(returnAllVisits());
        combos[0].getItems().addAll(returnAllSelectVisits());

    }

    public List<String> returnAllVisits() {
        List<String> visits = new ArrayList<>();
        for (int i = 0; i < 39; i++) {
            if (i == 0) {
                visits.add("All");
            } else {
                visits.add("Visit " + i);
            }
        }
        return visits;
    }

    public List<String> returnAllSelectVisits() {
        List<String> visits = new ArrayList<>();
        for (int i = 1; i < 39; i++) {
            visits.add("Visit " + i);
        }
        return visits;
    }

    public void onSelectVisit(Label lab, ComboBox<String>... combos) {
        combos[0].valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                lab.setText(t1);
//                if (combos[0].getSelectionModel().getSelectedIndex() < 0) {
//                    lab.setText("Select Visit Number");
//                } else {
//                    lab.setText(t1);
//                }
            }
        });

    }

    public void onSelectDate(Label lab, DatePicker date) {
        date.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if (date.getValue() != null) {
                    lab.setText(format.format(md.convert(newValue)));
                } else {
                    lab.setText("Pick date");
                }
            }
        });
    }

    public void populateTable(TableView tableView, ComboBox<String> visitType, TableColumn... col) {
        col[0].setCellValueFactory(new PropertyValueFactory<>("vssubjectnumber"));
        col[1].setCellValueFactory(new PropertyValueFactory<>("vsvisit"));
        col[2].setCellValueFactory(new PropertyValueFactory<>("vsvisitdate"));
        col[2].setCellFactory(column -> {
            TableCell<Visits, Date> cell = new TableCell<Visits, Date>() {
                private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

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
        });
        col[3].setCellValueFactory(new PropertyValueFactory<>("vsskipped"));
        col[3].setCellFactory(column -> {
            TableCell<Visits, Integer> cell = new TableCell<Visits, Integer>() {

                @Override
                protected void updateItem(Integer item, boolean empty) {
                    try {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            if (item == 1) {
                                setText("Skipped Visit");

                            } else if (item == 0) {
                                setText("Performed");
                            }
                        }
                    } catch (Exception es) {
                    }
                }
            };
            return cell;
        });
        col[4].setCellValueFactory(new PropertyValueFactory<>("vsuserid"));
        data = FXCollections.observableArrayList();
        if (visitType.getSelectionModel().getSelectedIndex() < 0) {
            data.addAll(DBConnect.getInstance().findAllVisits());
        } else {

            data.addAll(DBConnect.getInstance().findAllVisitsByType(visitType.getValue()));
        }
        tableView.setItems(data);
    }

    public void onSelectedSearchVisit(ComboBox combos, TableView tableView, String visitType, TextField searchText, TableColumn... col) {
        try {
            combos.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    if (t1.equals("All") || t1.equals("Search by Visit")) {
                        combos.getSelectionModel().clearSelection();
                    }
                    populateTable(tableView, combos, col);
                    autoSearch(searchText, tableView);
                }
            });
        } catch (Exception e) {
        }
    }

    public void autoSearch(TextField searchText, TableView tableView) {
        FilteredList<Visits> filteredData = new FilteredList<>(data, e -> true);
        searchText.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Visits>) visit -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return Integer.toString(visit.getVssubjectnumber()).toLowerCase().contains(newValue.toLowerCase());
            });
        });
        SortedList<Visits> sortdata = new SortedList<>(filteredData);
        sortdata.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortdata);
    }

    public void showView(GridPane... grid) {
        if (grid[0].isVisible()) {
            grid[0].setVisible(false);
            grid[1].setVisible(true);
            md.Translate(grid[1]);
        } else {
            grid[1].setVisible(false);
            grid[0].setVisible(true);
            md.Translate(grid[0]);
        }
    }

    public void visitsToShow(TableView tableView, Visits visits, TableColumn... col) {
        String date, skipped = "";
        if (visits.getVsvisitdate() == null) {
            date = "Visit Not performed";
        } else {
            date = format.format(visits.getVsvisitdate());
        }
        if (visits.getVsskipped() == 1) {
            skipped = "Visit not performed";
        } else if (visits.getVsskipped() == 0) {
            skipped = "Visit was performed";
        }
        final ObservableList<View> _data = FXCollections.observableArrayList(
                new View("Subject Number", Integer.toString(visits.getVssubjectnumber())),
                new View("Subject Name", DBConnect.getInstance().findSubjectsByNumber(visits.getVssubjectnumber()).getSname()),
                new View("Visit", visits.getVsvisit()),
                new View("Date", date),
                new View("User", DBConnect.getInstance().findUserByUserId(visits.getVsuserid()).getUfullname()),
                new View("Skipped", skipped),
                new View("Date entered", format.format(visits.getVsdatecreated()))
        );
        col[0].setCellValueFactory(new PropertyValueFactory<>("title"));
        col[1].setCellValueFactory(new PropertyValueFactory<>("details"));
        tableView.setItems(_data);
    }

    public boolean showAction(TableView selectionTable, TableView viewTable, TableColumn... col){
        if (selectionTable.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Error", "Please select a visit to view");
            return false;
        } else {
            visitsToShow(viewTable, (Visits) selectionTable.getSelectionModel().getSelectedItem(), col);
            return true;
        }
    }

    public boolean isValidated(CheckBox check, DatePicker picker, TextField text, ComboBox comb) {
        if (text.getText().isEmpty()) {
            md.note("Error", "Please enter the subject number to continue");
            return false;
        } else if (comb.getValue() == null) {
            md.note("Error", "Please the visit number is required");
            return false;
        } else if (picker.getValue() == null) {
            if (!check.isSelected()) {
                md.note("Error", "Please select the visit date");
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public boolean onSaveOrUpdate(CheckBox check, int vsid, DatePicker picker, TextField text, ComboBox<String> comb, Button but) {
       
        if (isValidated(check, picker, text, comb)) {
           String [] words = comb.getValue().split(" ");
           Integer visit = Integer.parseInt(words[1]);
           if(ScheduleValidations.validate(Integer.parseInt(text.getText()), visit).validDate()){
            if (isNumberValidated(Integer.parseInt(text.getText()))) {
                visits.setVsskipped(returnSkipped(check));
                visits.setVssubjectnumber(Integer.parseInt(text.getText()));
                visits.setVsuserid(md.getId());
                visits.setVsvisit(comb.getValue());
                if (check.isSelected()) {
                    if (md.AlertSelected(Alert.AlertType.INFORMATION, "Hi there", "Are you sure this visit was skiped", "Please okay if you are sure", "Ok")) {
                        visits.setVsvisitdate(null);
                    }
                } else {
                    visits.setVsvisitdate(md.convert(picker.getValue()));
                }
                if (but.getText().equals("Save")) {
                    if (DBConnect.getInstance().create(visits)) {
                        md.note("Successful", "Record added");
                        refreshField(check, picker, text, but);
                    } else {
                        md.note("Error", "Duplicate records are not allowed, verify and enter again");
                    }
                } else if (but.getText().equals("Update")) {
                    visits.setVsid(vsid);
                    if (DBConnect.getInstance().update(visits)) {
                        md.note("Successfull", "Record updated");
                        refreshField(check, picker, text, but);
                    } else {
                        md.note("Error", "Duplicate records are not allowed, verify and enter again");
                    }
                }
            }
        }
    }
        return true;
    }

    public int returnSkipped(CheckBox check) {
        if (check.isSelected()) {
            return 1;
        }
        return 0;
    }

    public boolean isNumberValidated(int number) {
        Subjects subjects = DBConnect.getInstance().findSubjectsByNumber(number);
        if (subjects.getSnumber() == null) {
            md.note("Error", "Please the subject number is not recognise");
            return false;
        } else if (subjects.getSnumber() != number) {
            md.note("Error", "Please the subject number is either not recognise or a failure");
            return false;
        }
        return true;
    }

    public void refreshField(CheckBox check, DatePicker picker, TextField text, Button but) {
        check.setSelected(false);
        text.setText("");
        but.setText("Save");
        text.requestFocus();
    }

    public boolean deleteClicked(TableView table) {
        if (table.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Sorry", "Please select the visit to delete");
            return false;
        } else {
            if (md.AlertSelected(Alert.AlertType.WARNING, "Delete", "Are you sure you want to delete?", "This action can not be undo", "Delete")) {
                if (DBConnect.getInstance().delete((Visits) table.getSelectionModel().getSelectedItem())) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean onUpdateClick(TableView tableView, Visits visit, CheckBox check, TextField txt, ComboBox com, DatePicker date, Button but) {
        if (tableView.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Error", "Please select the visit to update");
            return false;
        } else {
            updateLabels(visit, check, txt, com, date, but);
        }
        return false;
    }

    public void updateLabels(Visits visit, CheckBox check, TextField txt, ComboBox com, DatePicker date, Button but) {
        skippedChecked(visit.getVsskipped(), check);
        txt.setText(visit.getVssubjectnumber().toString());
        if (visit.getVsvisitdate() != null) {
            date.setValue(visit.getVsvisitdate().toLocalDate());
        } else {
            date.setValue(null);
        }
        com.setValue(visit.getVsvisit());
        but.setText("Update");
    }

    public void skippedChecked(int check, CheckBox box) {
        if (check == 1) {
            box.setSelected(true);
        } else {
            box.setSelected(false);
        }
    }

    public boolean validateExport(ComboBox comb, DatePicker date) {
        if (comb.getValue() == null || date.getValue() == null) {
            md.note("Error", "Sorry you can not export without date");
            return false;
        }
        return true;
    }

    public boolean exportAction(ComboBox<String> com, DatePicker date) {
        if (validateExport(com, date)) {
            Stage pm = new Stage();
            String path = md.myPath(pm, "Choose directory");
            if (!path.isEmpty()) {
                if (DBConnect.getInstance().exportForDateAndVisit(com.getValue(), md.convert(date.getValue()), path)) {
                    if (md.AlertSelected(Alert.AlertType.INFORMATION, "Success", "Exported successfully", "Click Ok to open your root directory to get the excel file", "Ok")) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            if (desktop.isSupported(Desktop.Action.OPEN)) {
//                            desktop.open(new File("C:/Users/Nana Badu Tamea/Desktop/Visit Reports/"));
//                                desktop.open(new File("C:/Users/yaw/Desktop/Visit Reports/"));
                                desktop.open(new File(path));//                            desktop.open(new File("/Users/kwakuadjei/Desktop/Exports/"));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

//       else{
//        md.note("Error", "Please make sure the date is correct");
//       }
        }
        return true;
    }
}
