/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloc;

import com.sun.javafx.util.Utils;
import dao.DBConnect;
import java.sql.Array;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import model.Daily;
import model.Subjects;
import model.User;
import model.View;
import utils.mediator;

/**
 *
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class DailyOutpatientBloc {

    mediator md = mediator.md();
    Daily daily = new Daily();
    View view = new View();
    private static final DailyOutpatientBloc DAILY_BLOC = new DailyOutpatientBloc();
    ObservableList<Daily> data;

    private DailyOutpatientBloc() {
        this.data = FXCollections.observableArrayList();

    }

    public static DailyOutpatientBloc bBloc() {
        return DAILY_BLOC;
    }

    public void populateCombox(ComboBox... combos) {
        List<Integer> daysInMonth = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            daysInMonth.add(i);
        }
        for (int i = 0; i < combos.length; i++) {
            combos[i].getItems().clear();
            if (i == 0) {
                combos[i].getItems().addAll(namesOfUsers());
                md.new ComboBoxAutoComplete<String>(combos[0]);
            } else if (i == 1) {
                combos[i].getItems().addAll(subjectInfo());
                md.new ComboBoxAutoComplete<String>(combos[1]);
            } else if (i == 2) {
                daysInMonth.remove(0);
                combos[2].getItems().clear();
                combos[2].getItems().addAll(daysInMonth);
                md.new ComboBoxAutoComplete<Integer>(combos[2]);
            } else if (i == 3) {
                combos[3].getItems().clear();
                combos[3].getItems().addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
                md.new ComboBoxAutoComplete<Integer>(combos[3]);
            } else if (i == 4) {
                combos[4].getItems().clear();
                combos[4].getItems().addAll(2017, 2018, 2019, 2020, 2021, 2022, 2023);
                md.new ComboBoxAutoComplete<Integer>(combos[4]);
            }
        }

    }

    public List<String> namesOfUsers() {
        List<String> names = new ArrayList<>();
        DBConnect.getInstance().findAllUser().forEach((user) -> {
            names.add(user.getUfullname());
        });
        return names;
    }

    public List<String> subjectInfo() {
        List<String> subjects = new ArrayList<>();
        DBConnect.getInstance().findAllSubjects().forEach((subject) -> {
            subjects.add(subject.getSnumber() + " " + subject.getSname());
        });
        return subjects;
    }

    public void nowDate(Label date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        LocalDateTime now = LocalDateTime.now();
        date.setText("Date: " + dtf.format(now));
    }

    public void onSelectSubject(Label l1, Label l2, Label l3, ComboBox<String>... combos) {
        try {
            combos[0].valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    md.setDbuser(DBConnect.getInstance().findUserByName(t1));
                }
            });
            combos[1].valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    try {
                        String[] subject = Utils.split(t1, " ");
                        md.setDbSubject(DBConnect.getInstance().findSubjectsByNumber(Integer.parseInt(subject[0])));
                        showSubjectDetails(l1, l2, l3);
                    } catch (NumberFormatException e) {
                    }
                }
            });
        } catch (Exception ex) {
        }
    }

    public void showSubjectDetails(Label... label) {
        label[0].setText("NAME: " + md.getDbSubject().getSname());
        label[1].setText("DOB: " + md.toDateFormat(md.getDbSubject().getSdob()));
        label[2].setText("COM: " + md.getDbSubject().getScommunity());
    }

    public Boolean isValidatedCombo(ComboBox... combos) {
        if (combos[0].getValue() == null) {
            md.note("Error", "Please select User");
            return false;
        } else if (combos[1].getValue() == null) {
            md.note("Error", "Please select Subject number");
            return false;
        } else if (combos[2].getValue() == null) {
            md.note("Error", "Please select Day of  date");
            return false;
        } else if (combos[3].getValue() == null) {
            md.note("Error", "Please select Month of  date");
            return false;
        } else if (combos[4].getValue() == null) {
            md.note("Error", "Please select Year of  date");
            return false;
        }
        return true;
    }

    public Boolean isValidTextField(TextField... textFields) {
        if (textFields[0].getText().isEmpty()) {
            md.note("Error", "Please enter Temperature");
            return false;
        } else if (textFields[1].getText().isEmpty()) {
            md.note("Error", "Please enter Weight");
            return false;
        } else if (textFields[2].getText().isEmpty()) {
            md.note("Error", "Please enter Hour of Time");
            return false;
        } else if (textFields[3].getText().isEmpty()) {
            md.note("Error", "Please enter Minute of Time");
            return false;
        }
        return true;
    }

    public Boolean isValidCaseType(CheckBox... check) {
        if (!check[0].isSelected() && !check[1].isSelected()) {
            md.note("Error", "At least one case type should be checked");
            return false;
        }
        return true;
    }

    public String caseType(CheckBox... check) {
        if (check[0].isSelected() && check[1].isSelected()) {
            return "New case and Review";
        } else if (check[0].isSelected()) {
            return "New case only";
        } else if (check[1].isSelected()) {
            return "Review only";
        }
        return null;
    }

    public String feverState(TextField temp) {
        if (Double.parseDouble(temp.getText()) < 37.5) {
            return "Not Fever";
        } else if (Double.parseDouble(temp.getText()) > 37.5) {
            return "Fever";
        }
        return null;
    }

    public Boolean saveOrUpdate(Button saveButton, ActionEvent event, TextField weight, TextField temp, TextField h, TextField m, CheckBox dbNewCaseCheck, CheckBox dbReviewCheck,CheckBox dbDateCheck, int opdId, ComboBox... combos) {
        if (isValidCaseType(dbNewCaseCheck, dbReviewCheck) && isValidTextField(temp, weight, h, m) && isValidatedCombo(combos)) {
            daily.setDcasetype(caseType(dbNewCaseCheck, dbReviewCheck));
            daily.setDfeverstate(feverState(temp));
            daily.setDtemp(Double.parseDouble(temp.getText()));
            daily.setDuser(md.getDbuser().getUid());
            daily.setDweight(Double.parseDouble(weight.getText()));
            daily.setDsubjectnumber(md.getDbSubject().getSnumber());
            daily.setDtime(getTimeJoint(h, m));
            daily.setDdate(getDDate(dbDateCheck,combos));
            if (saveButton.getText().equals("Save")) {
                if (DBConnect.getInstance().create(daily)) {
                    md.note("Successful", "Records saved");
                    refreshFields(weight, temp, h, m, dbNewCaseCheck, dbReviewCheck, combos);
                }
            } else if (saveButton.getText().equals("Update")) {
                daily.setDid(opdId);
                if (md.AlertSelected(Alert.AlertType.INFORMATION, "Update", "Are sure you want to update OPD Records", " ", "Yes")) {
                    if (DBConnect.getInstance().update(daily)) {
                        md.note("Successful", "Records updated");
                        saveButton.setText("Save");
                        refreshFields(weight, temp, h, m, dbNewCaseCheck, dbReviewCheck, combos);
                    }
                } else {

                }
            }
            return true;
        }
        return false;
    }

    private Time getTimeJoint(TextField h, TextField m) {
        DateFormat sdf = new SimpleDateFormat("H:m");
        if (h.getText().isEmpty() || m.getText().isEmpty()) {
            md.note("Error", "Please indicate time of collection");
            return null;
        } else {
            String timeCollection = h.getText() + ":" + m.getText();
            try {
                LocalTime localTime = LocalTime.parse(timeCollection);
                return java.sql.Time.valueOf(localTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Date getDDate(CheckBox check, ComboBox... combox) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        java.util.Date date = new java.util.Date();
        java.sql.Date sd = new java.sql.Date(date.getTime());

        if (check.isSelected()) {
            return sd;

        } else if (combox[2].getValue() == null || combox[3].getValue() == null || combox[4].getValue() == null) {
            md.note("Error", "Please enter Date");
            return null;
        } else {
            try {
                String dateSelected;
                if ((Integer) combox[2].getValue() > 9) {
                    dateSelected = combox[2].getValue() + "-" + combox[3].getValue() + "-" + combox[4].getValue();
                } else {
                    dateSelected = "0" + combox[2].getValue() + "-" + combox[3].getValue() + "-" + combox[4].getValue();
                }
                String dateInString = dateSelected;
                date = formatter.parse(dateInString);
                sd = new java.sql.Date(date.getTime());
                return sd;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return sd;
    }

    public void refreshFields(TextField weight, TextField temp, TextField h, TextField m, CheckBox dbNewCaseCheck, CheckBox dbReviewCheck, ComboBox... combos) {
        weight.setText("");
        temp.setText("");
        h.setText("");
        m.setText("");
        dbNewCaseCheck.setSelected(false);
        dbReviewCheck.setSelected(false);
        combos[0].setValue(null);
        combos[1].setValue(null);
        combos[2].setValue(null);
        combos[3].setValue(null);
        combos[4].setValue(null);

    }

    public void refreshLabel(Label... label) {
        label[0].setText("NAME: Not Selected");
        label[1].setText("DOB: Not Selected");
        label[2].setText("COM: Not Selected");
    }

    public void populateTable(TableView tableView, TableColumn... col) {
        col[0].setCellValueFactory(new PropertyValueFactory<>("dweight"));
        col[1].setCellValueFactory(new PropertyValueFactory<>("dtemp"));
        col[2].setCellValueFactory(new PropertyValueFactory<>("dsubjectnumber"));
        col[3].setCellValueFactory(new PropertyValueFactory<>("dcasetype"));
        col[4].setCellValueFactory(new PropertyValueFactory<>("duser"));
        col[5].setCellValueFactory(new PropertyValueFactory<>("ddatecreated"));
        col[5].setCellFactory(column -> {
            TableCell<Daily, java.sql.Timestamp> cell = new TableCell<Daily, java.sql.Timestamp>() {
                private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

                @Override
                protected void updateItem(java.sql.Timestamp item, boolean empty) {
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
        col[6].setCellValueFactory(new PropertyValueFactory<>("dtime"));
        col[6].setCellFactory(column -> {
            TableCell<Daily, Time> cell = new TableCell<Daily, Time>() {
                private final SimpleDateFormat format = new SimpleDateFormat("H:m");

                @Override
                protected void updateItem(Time item, boolean empty) {
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
        col[7].setCellValueFactory(new PropertyValueFactory<>("ddate"));
        col[7].setCellFactory(column -> {
            TableCell<Daily, Date> cell = new TableCell<Daily, Date>() {
                private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
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
        data = FXCollections.observableArrayList();
        data.addAll(DBConnect.getInstance().findAllDailyRecords());
        tableView.setItems(data);
    }

    public void autoSearch(TextField searchText, TableView tableView) {
        FilteredList<Daily> filteredData = new FilteredList<>(data, e -> true);
        searchText.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Daily>) daily -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return Integer.toString(daily.getDsubjectnumber()).toLowerCase().contains(newValue.toLowerCase());
            });
        });
        SortedList<Daily> sortdata = new SortedList<>(filteredData);
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

    public void dailyToShow(TableView tableView, Daily daily, TableColumn... col) {
        final ObservableList<View> _data = FXCollections.observableArrayList(
                new View("Subject Number", Integer.toString(daily.getDsubjectnumber())),
                new View("Weight", Double.toString(daily.getDweight())),
                new View("Temperature", Double.toString(daily.getDtemp())),
                new View("Case", daily.getDcasetype()),
                new View("Fever state", daily.getDfeverstate()),
                new View("User", DBConnect.getInstance().findUserByUserId(daily.getDuser()).getUfullname()),
                new View("Time", md.toTimeFormatFromString(daily.getDtime())),
                new View("OPD Date", md.toDateFormat(daily.getDdate())),
                new View("Date Created", md.toDateFormatFromString(daily.getDdatecreated()))
        );
        col[0].setCellValueFactory(new PropertyValueFactory<>("title"));
        col[1].setCellValueFactory(new PropertyValueFactory<>("details"));
        tableView.setItems(_data);
    }

    public boolean showAction(TableView selectionTable, TableView viewTable, TableColumn... col) {
        if (selectionTable.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Error", "Please select an OPD record");
            return false;
        } else {
            dailyToShow(viewTable, (Daily) selectionTable.getSelectionModel().getSelectedItem(), col);
            return true;
        }
    }

    public boolean deleteAction(TableView table) {
        if (table.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Sorry", "Please select the OPD record to delect");
            return false;
        } else {
            if (md.AlertSelected(Alert.AlertType.WARNING, "Delete", "Are you sure you want to delete?", "This action can not be undo", "Delete")) {
                if (DBConnect.getInstance().delete((Daily) table.getSelectionModel().getSelectedItem())) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean onUpdateClick(TableView tableView, Daily daily, ComboBox c1, ComboBox c2, ComboBox d, ComboBox mm, ComboBox y, TextField t1, TextField t2, TextField h, TextField m, Label... l) {
        if (tableView.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Error", "Please select the OPD record to update");
            return false;
        } else {
            if (updateLabelFields(daily, c1, c2, l)) {
                updateTextFields(daily, d, mm, y, t1, t2, h, m);
            }
        }
        return false;
    }

    public boolean updateLabelFields(Daily daily, ComboBox c1, ComboBox c2, Label... l) {
        Subjects subject = DBConnect.getInstance().findSubjectsByNumber(daily.getDsubjectnumber());
        User user = DBConnect.getInstance().findUserByUserId(daily.getDuser());
        l[0].setText(subject.getSname());
        l[1].setText(subject.getSdob().toString());
        l[2].setText(subject.getScommunity());
        c1.setValue(user.getUfullname());
        c2.setValue(subject.getSid() + " " + subject.getSname());
        return true;
    }

    public void updateTextFields(Daily daily, ComboBox d, ComboBox m, ComboBox y, TextField... text) {
        text[0].setText(Double.toString(daily.getDtemp()));
        text[1].setText(Double.toString(daily.getDweight()));
        SimpleDateFormat sdf = new SimpleDateFormat("H");
        text[2].setText(sdf.format(daily.getDtime()));
        sdf = new SimpleDateFormat("m");
        text[3].setText(sdf.format(daily.getDtime()));
        sdf = new SimpleDateFormat("d");
        d.setValue(Integer.parseInt(sdf.format(daily.getDdate())));
        sdf = new SimpleDateFormat("MMM");
        m.setValue(sdf.format(daily.getDdate()));
        sdf = new SimpleDateFormat("yyyy");
        y.setValue(Integer.parseInt(sdf.format(daily.getDdate())));
    }

//    public int dayFromPreviousVisit(Integer subjectNumber){
//    Daily daily = DBConnect.getInstance().findDailyRecordBySubject(subjectNumber);
//    
//    }
    public void checkStatus(String state, CheckBox... check) {
        switch (state) {
            case "New case only":
                check[0].setSelected(true);
                break;
            case "Review only":
                check[1].setSelected(true);
                break;
            case "New case and Review":
                check[0].setSelected(true);
                check[1].setSelected(true);
                break;
        }
    }

}
