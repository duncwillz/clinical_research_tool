/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloc;

import dao.DBConnect;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
    public String todayDate (){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        LocalDateTime now = LocalDateTime.now();
       return dtf.format(now);
    }

    public boolean saveOrUpdateInpatient(DatePicker adate, DatePicker ddate, Button but, int inpid, ComboBox combA, ComboBox combB, Label... l) {
        if (dbloc.isValidatedCombo(combA, combB) && isValidDateField(adate, ddate)) {
            if (adate.getValue() == null) {
                inp.setInpadmissionDate(null);
            } else {
                inp.setInpadmissionDate(md.convert(adate.getValue()));
            }
            if (ddate.getValue() == null) {
                inp.setInpdischargeDate(null);
            } else {
                inp.setInpdischargeDate(md.convert(ddate.getValue()));
            }
            inp.setInpuserid(md.getDbuser().getUid());
            inp.setInpsubject(md.getDbSubject().getSid());
            if (but.getText().equals("Save")) {
                if (DBConnect.getInstance().create(inp)) {
                    md.note("Successful", "Inpatients recorded");
                      sendMessagetoEmails(inp.getInpsubject().toString());
                }
            } else if (but.getText().equals("Update")) {
                inp.setInpid(inpid);
                if (DBConnect.getInstance().update(inp)) {
                    md.note("Successful", "Inpatient records updated succesfully");
                    but.setText("Save");
                }
            }
            refreshFields(adate, ddate, combA, combB);
            dbloc.refreshLabel(l[0], l[1], l[2]);
            return true;
        }
        return false;
    }

    public Boolean isValidDateField(DatePicker... dateFields) {
        if (dateFields[0].getValue() == null) {
            md.note("Error", "Please enter admission date");
            return false;
        }
        return true;
    }

    public void refreshFields(DatePicker adate, DatePicker ddate, ComboBox<String>... combos) {
        adate.setValue(null);
        ddate.setValue(null);
        combos[0].setValue(null);
        combos[1].setValue(null);

    }

    public void updateSelected(TableView table, Button but, Inpatients inp, DatePicker adate, DatePicker ddate, ComboBox c1, ComboBox c2, Label... lab) {
        if (table.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Error", "Please select the OPD record to update");
        } else {
            but.setText("Update");
            updateSetFields(inp, adate, ddate, c1, c2, lab);
        }
    }

    public void updateSetFields(Inpatients inp, DatePicker adate, DatePicker ddate, ComboBox c1, ComboBox c2, Label... l) {
        Subjects subject = DBConnect.getInstance().findSubjectsByNumber(inp.getInpsubject());
        User user = DBConnect.getInstance().findUserByUserId(inp.getInpuserid());
        adate.setValue(inp.getInpadmissionDate().toLocalDate());
        if (inp.getInpdischargeDate() == null) {
            ddate.setValue(null);
        } else {
            ddate.setValue(inp.getInpdischargeDate().toLocalDate());
        }
        c1.setValue(user.getUfullname());
        c2.setValue(subject.getSid() + " " + subject.getSname());
        l[0].setText(subject.getSname());
        l[1].setText(subject.getSdob().toString());
        l[2].setText(subject.getScommunity());
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
        String adate, ddate,date;
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
        if(inpat.getInpdateCreated()==null){
            date = "Pending";
        }else{
                        date = format.format(inpat.getInpdateCreated());

        }
        final ObservableList<View> _data = FXCollections.observableArrayList(new View("Subject Number", Integer.toString(inpat.getInpsubject())),
                new View("Admission date", adate),
                new View("Discharge date",ddate),
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
                sendMail.sendMessage(mRecipients.get(i).getMname(),subjectNumber,todayDate(),mRecipients.get(i).getMemail());
           }
        
    }

    public void sendMessagetoNumbers() {
      // sendSMS.sendSMS(inp.getInpsubject().toString(),todayDate(),"+233247035610");
    }
}
