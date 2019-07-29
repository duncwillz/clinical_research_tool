/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloc;

import dao.DBConnect;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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
import model.Daily;
import model.Inpatients;
import model.Messaging;
import model.Subjects;
import model.User;
import model.View;
import utils.SendEmail;
import utils.SendSMS;
import utils.mediator;

/**
 *
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class InpatientBloc {

    mediator md = mediator.md();
    DailyOutpatientBloc dbloc = DailyOutpatientBloc.bBloc();
    Inpatients inp = new Inpatients();
    SendEmail sendMail = SendEmail.emailAPI();
    SendSMS sendSMS = SendSMS.smsAPI();
    private static final InpatientBloc INPATIENT_BLOC = new InpatientBloc();
    ObservableList<Inpatients> data;
    ObservableList<Inpatients> data_;

    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private InpatientBloc() {
        this.data = FXCollections.observableArrayList();
        this.data_ = FXCollections.observableArrayList();
    }

    public static InpatientBloc inpBloc() {
        return INPATIENT_BLOC;
    }

    public String todayDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public boolean saveOrUpdateInpatient(Button but, int inpid, ComboBox combA, ComboBox combB, ComboBox ad, ComboBox am, ComboBox ay, ComboBox dd, ComboBox dm, ComboBox dy, CheckBox aCheckDate, CheckBox dCheckBox, Label... l) {
        if (isValidDateField(aCheckDate, ad, am, ay)) {
//            if (adate.getValue() == null) {
//                inp.setInpadmissionDate(null);
//            } else {
//                inp.setInpadmissionDate(md.convert(adate.getValue()));
//            }
//            if (ddate.getValue() == null) {
//                inp.setInpdischargeDate(null);
//            } else {
//                inp.setInpdischargeDate(md.convert(ddate.getValue()));
//            }
            inp.setInpadmissionDate(getAdmissionDate(aCheckDate, ad, am, ay));
            if (dd.getValue() == null && !dCheckBox.isSelected()) {
                inp.setInpdischargeDate(null);
            } else {
                inp.setInpdischargeDate(getAdmissionDate(dCheckBox, dd, dm, dy));
            }
            System.out.println("This is the discharge ddate >>>>" + inp.getInpdischargeDate());
            inp.setInpuserid(md.getDbuser().getUid());
            inp.setInpsubject(md.getDbSubject().getSnumber());
            if (but.getText().equals("Save")) {
                if (DBConnect.getInstance().create(inp)) {
                    md.note("Successful", "Inpatients recorded");
                    sendMessagetoEmails(inp.getInpsubject().toString());
                }
            } else if (but.getText().equals("Update")) {
                inp.setInpid(inpid);
                if (DBConnect.getInstance().update(inp)) {
                    md.note("Successful", "Inpatient records updated succesfully");
                }
            }
            refreshFields(but, aCheckDate, dCheckBox, combA, combB, ad, am, ay, dd, dm, dy);
            dbloc.refreshLabel(l[0], l[1], l[2]);
            return true;
        }
        return false;
    }

    public Date getAdmissionDate(CheckBox acheck, ComboBox... combox) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        java.util.Date date = new java.util.Date();
        java.sql.Date sd = new java.sql.Date(date.getTime());

        if (acheck.isSelected()) {
            return sd;

        } else if (combox[0].getValue() == null || combox[2].getValue() == null || combox[1].getValue() == null && !acheck.getText().equals("Check if same as discharge today")) {
            md.note("Error", "Please enter Date");
            return null;
        } else {
            try {
                String dateSelected;
                Integer dayValue;
                if (combox[0].getValue() instanceof Integer) {
                    dayValue = (Integer) combox[0].getValue();
                } else {
                    dayValue = Integer.parseInt((String) combox[0].getValue());
                }
                if (dayValue > 9) {
                    dateSelected = combox[0].getValue() + "-" + combox[1].getValue() + "-" + combox[2].getValue();
                } else {
                    dateSelected = "0" + combox[0].getValue() + "-" + combox[1].getValue() + "-" + combox[2].getValue();
                }
                String dateInString = dateSelected;
                date = formatter.parse(dateInString);
                sd = new java.sql.Date(date.getTime());
                System.out.println(sd);
                return sd;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return sd;
    }

    public Boolean isValidDateField(CheckBox ac, ComboBox... combo) {
        if (combo[0].getValue() == null && combo[1].getValue() == null && combo[2].getValue() == null && !ac.isSelected()) {
            md.note("Error", "Please enter admission date");
            return false;
        }
        return true;
    }
//       public Boolean isValidatedCombo(ComboBox... combos) {
//        if (combos[0].getValue() == null) {
//            md.note("Error", "Please select User");
//            return false;
//        } else if (combos[1].getValue() == null) {
//            md.note("Error", "Please select Subject number");
//            return false;
//        } else if (combos[2].getValue() == null) {
//            md.note("Error", "Please select Day of  date");
//            return false;
//        } else if (combos[3].getValue() == null) {
//            md.note("Error", "Please select Month of  date");
//            return false;
//        } else if (combos[4].getValue() == null) {
//            md.note("Error", "Please select Year of  date");
//            return false;
//        }
//        return true;
//    }

    public void refreshFields(Button but, CheckBox adate, CheckBox ddate, ComboBox... combos) {
        adate.setSelected(false);
        ddate.setSelected(false);
        but.setText("Save");
//        combos[0].setValue(null);
        combos[1].setValue(null);
        combos[2].setValue(null);
        combos[3].setValue(null);
        combos[4].setValue(null);
        combos[5].setValue(null);
        combos[6].setValue(null);
        combos[7].setValue(null);

    }

    public void updateSelected(TableView table, Button but, Inpatients inp, CheckBox acheck, CheckBox dcheck, ComboBox c1, ComboBox c2, ComboBox ad, ComboBox am, ComboBox ay, ComboBox dd, ComboBox dm, ComboBox dy, Label... lab) {
        if (table.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Error", "Please select the OPD record to update");
        } else {
            refreshFields(but, acheck, dcheck, c1, c2, ad, am, ay, dd, dm, dy);
            but.setText("Update");
            updateSetFields(inp, c1, c2, ad, am, ay, dd, dm, dy, lab);
        }
    }

    public void updateSetFields(Inpatients inp, ComboBox c1, ComboBox c2, ComboBox ad, ComboBox am, ComboBox ay, ComboBox dd, ComboBox dm, ComboBox dy, Label... l) {
        Subjects subject = DBConnect.getInstance().findSubjectsByNumber(inp.getInpsubject());
        User user = DBConnect.getInstance().findUserByUserId(inp.getInpuserid());
        c1.setValue(user.getUfullname());
        c2.setValue(subject.getSnumber()+ " " + subject.getSname());
        l[0].setText(subject.getSname());
        l[1].setText(subject.getSdob().toString());
        l[2].setText(subject.getScommunity());
        SimpleDateFormat sdf = new SimpleDateFormat("d");
        ad.setValue(sdf.format(inp.getInpadmissionDate()));
        sdf = new SimpleDateFormat("MMM");
        am.setValue(sdf.format(inp.getInpadmissionDate()));
        sdf = new SimpleDateFormat("yyyy");
        ay.setValue(sdf.format(inp.getInpadmissionDate()));
        sdf = new SimpleDateFormat("d");
        dd.setValue(sdf.format(inp.getInpdischargeDate()));
        sdf = new SimpleDateFormat("MMM");
        dm.setValue(sdf.format(inp.getInpdischargeDate()));
        sdf = new SimpleDateFormat("yyyy");
        dy.setValue(sdf.format(inp.getInpdischargeDate()));
    }

    public boolean deleteSelected(TableView table) {
        if (md.AlertSelected(Alert.AlertType.WARNING, "Delete?", "Warning, this action cannot be undo", "Are you sure you want to delete", "Yes")) {
            if (table.getSelectionModel().getSelectedIndex() < 0) {
                md.note("Sorry", "Please select the OPD record to delect");
                return false;
            } else {
                if (DBConnect.getInstance().delete((Inpatients) table.getSelectionModel().getSelectedItem())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void populateTable(TableView tableView, TableColumn... col) {
        try {
            col[0].setCellValueFactory(new PropertyValueFactory<>("inpsubject"));
            col[1].setCellValueFactory(new PropertyValueFactory<>("inpadmissionDate"));
            col[1].setCellFactory(column -> {
                return returnCell("dd-MMM-yyyy");
            });
            col[2].setCellValueFactory(new PropertyValueFactory<>("inpdischargeDate"));
            col[2].setCellFactory(column -> {
                return returnCell("dd-MMM-yyyy");
            });
            col[3].setCellValueFactory(new PropertyValueFactory<>("inpuserid"));
            col[4].setCellValueFactory(new PropertyValueFactory<>("inpdateCreated"));
            col[4].setCellFactory(column -> {
                return returnCell("E, dd-MMM-yyyy");
            });
            data = FXCollections.observableArrayList();
            data.addAll(DBConnect.getInstance().findAllInpatients());
            tableView.setItems(data);
        } catch (Exception ex) {
        }
    }

    public TableCell returnCell(String pattern) {
        TableCell<Inpatients, Date> cell = new TableCell<Inpatients, Date>() {

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

    public void autoSearch(TextField searchText, TableView tableView) {
        FilteredList<Inpatients> filteredData = new FilteredList<>(data, e -> true);
        searchText.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Inpatients>) inpat -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return Integer.toString(inpat.getInpsubject()).toLowerCase().contains(newValue.toLowerCase());
            });
        });
        SortedList<Inpatients> sortdata = new SortedList<>(filteredData);
        sortdata.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortdata);
    }

    public void inpatientToShow(TableView tableView, Inpatients inpat, TableColumn... col) {
        String adate, ddate, date;
        if (inpat.getInpadmissionDate() == null) {
            adate = "Pending";
        } else {
            adate = format.format(inpat.getInpadmissionDate());
        }
        if (inpat.getInpdischargeDate() == null) {
            ddate = "Pending";
        } else {
            ddate = format.format(inpat.getInpdischargeDate());
        }
        if (inpat.getInpdateCreated() == null) {
            date = "Pending";
        } else {
            date = format.format(inpat.getInpdateCreated());

        }
        final ObservableList<View> _data = FXCollections.observableArrayList(new View("Subject Number", Integer.toString(inpat.getInpsubject())),
                new View("Admission date", adate),
                new View("Discharge date", ddate),
                new View("User", DBConnect.getInstance().findUserByUserId(inpat.getInpuserid()).getUfullname()),
                new View("Date", date));

        col[0].setCellValueFactory(new PropertyValueFactory<>("title"));
        col[1].setCellValueFactory(new PropertyValueFactory<>("details"));
        tableView.setItems(_data);
    }

    public boolean showInpatientsAction(TableView selectionTable, TableView viewTable, TableColumn... col) {
        if (selectionTable.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Error", "Please select an Inpatients record");
            return false;
        } else {
            inpatientToShow(viewTable, (Inpatients) selectionTable.getSelectionModel().getSelectedItem(), col);
            return true;
        }
    }

    public void populatePendingDischarge(TableView pendingtable, TableColumn... col) {
        col[0].setCellValueFactory(new PropertyValueFactory<>("inpsubject"));
        col[1].setCellValueFactory(new PropertyValueFactory<>("inpadmissionDate"));
        col[1].setCellFactory(column -> {
            return returnCell("dd-MMM-yyyy");
        });
        data_ = FXCollections.observableArrayList();
        data_.addAll(DBConnect.getInstance().findAllByDischarge());
        pendingtable.setItems(data_);
    }

    public void sendMessagetoEmails(String subjectNumber) {
        List<Messaging> mRecipients = DBConnect.getInstance().findAllMessageRecipients();
        for (int i = 0; i < mRecipients.size(); i++) {
            sendMail.sendMessage(mRecipients.get(i).getMname(), subjectNumber, todayDate(), mRecipients.get(i).getMemail());
        }

    }

    public void sendMessagetoNumbers() {
        // sendSMS.sendSMS(inp.getInpsubject().toString(),todayDate(),"+233247035610");
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
            } else if (i == 2 || i == 5) {
                daysInMonth.remove(0);
                combos[2].getItems().clear();
                combos[2].getItems().addAll(daysInMonth);
                md.new ComboBoxAutoComplete<Integer>(combos[2]);
                combos[5].getItems().clear();
                combos[5].getItems().addAll(daysInMonth);
                md.new ComboBoxAutoComplete<Integer>(combos[5]);
            } else if (i == 3) {
                combos[3].getItems().clear();
                combos[3].getItems().addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
                md.new ComboBoxAutoComplete<Integer>(combos[3]);
                combos[6].getItems().clear();
                combos[6].getItems().addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
                md.new ComboBoxAutoComplete<Integer>(combos[6]);
            } else if (i == 4) {
                combos[4].getItems().clear();
                combos[4].getItems().addAll(2017, 2018, 2019, 2020, 2021, 2022, 2023);
                md.new ComboBoxAutoComplete<Integer>(combos[4]);
                combos[7].getItems().clear();
                combos[7].getItems().addAll(2017, 2018, 2019, 2020, 2021, 2022, 2023);
                md.new ComboBoxAutoComplete<Integer>(combos[7]);
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

}
