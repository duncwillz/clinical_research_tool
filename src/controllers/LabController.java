/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class LabController implements Initializable {

    @FXML
    private VBox addLogVBOX1;
    @FXML
    private TextField loggingNumber1;
    @FXML
    private CheckBox loggingParasiteStatus1;
    @FXML
    private TextField loggingTrackingNumber1;
    @FXML
    private TextField loggingTimeHour1;
    @FXML
    private TextField loggingTimeMinute1;
    @FXML
    private Button loggingSave1;
    @FXML
    private Button loggingClose1;
    @FXML
    private ComboBox<?> loggingDateDay1;
    @FXML
    private ComboBox<?> loggingDateMonth1;
    @FXML
    private ComboBox<?> loggingDateYear1;
    @FXML
    private ComboBox<?> dbSubjectNumberCombo;
    @FXML
    private Label dbSubjectNameLabel;
    @FXML
    private Label dbSubjectNameLabel1;
    @FXML
    private CheckBox loggingParasiteStatus11;
    @FXML
    private CheckBox loggingParasiteStatus12;
    @FXML
    private CheckBox loggingParasiteStatus121;
    @FXML
    private CheckBox loggingParasiteStatus122;
    @FXML
    private CheckBox loggingParasiteStatus1221;
    @FXML
    private TextField loggingTimeHour11;
    @FXML
    private TextField loggingTimeMinute11;
    @FXML
    private TextField searchSubject1;
    @FXML
    private VBox viewUpdateLog11;
    @FXML
    private Button viewAddLog1;
    @FXML
    private Button viewDeleteLog1;
    @FXML
    private Button viewClose1;
    @FXML
    private TableView<?> viewLogsTable1;
    @FXML
    private TableColumn<?, ?> viewLogs1;
    @FXML
    private TableColumn<?, ?> viewDate1;
    @FXML
    private TableColumn<?, ?> viewRequisition1;
    @FXML
    private TableColumn<?, ?> viewTrackingNumber1;
    @FXML
    private TableColumn<?, ?> viewTime1;
    @FXML
    private TableColumn<?, ?> viewParasite1;
    @FXML
    private TableColumn<?, ?> viewParasite11;
    @FXML
    private TableColumn<?, ?> viewParasite111;
    @FXML
    private Label viewSubjectNumber1;
    @FXML
    private VBox subjectVBOX;
    @FXML
    private TextField searchSubject;
    @FXML
    private TableView<?> subjectTable;
    @FXML
    private TableColumn<?, ?> colSubjectNumber;
    @FXML
    private TableColumn<?, ?> colSubjectDOB;
    @FXML
    private TableColumn<?, ?> colSubjectGroup;
    @FXML
    private TableColumn<?, ?> colSubjectGender;
    @FXML
    private Button subjectView;
    @FXML
    private Button subjectEnroll;
    @FXML
    private Button subjectUpdate;
    @FXML
    private Button subjectArchive;
    @FXML
    private Button subjectClose;
    @FXML
    private VBox addLogVBOX;
    @FXML
    private TextField loggingNumber;
    @FXML
    private TextField loggingRequsitionNumber;
    @FXML
    private CheckBox loggingParasiteStatus;
    @FXML
    private TextField loggingTrackingNumber;
    @FXML
    private TextField loggingTimeHour;
    @FXML
    private TextField loggingTimeMinute;
    @FXML
    private Button loggingSave;
    @FXML
    private Button loggingClose;
    @FXML
    private ComboBox<?> loggingDateDay;
    @FXML
    private ComboBox<?> loggingDateMonth;
    @FXML
    private ComboBox<?> loggingDateYear;
    @FXML
    private TextField loggingNumber2;
    @FXML
    private VBox viewUpdateLog1;
    @FXML
    private Button viewAddLog;
    @FXML
    private Button viewDeleteLog;
    @FXML
    private Button viewClose;
    @FXML
    private TableView<?> viewLogsTable;
    @FXML
    private TableColumn<?, ?> viewLogs;
    @FXML
    private TableColumn<?, ?> viewDate;
    @FXML
    private TableColumn<?, ?> viewRequisition;
    @FXML
    private TableColumn<?, ?> viewTrackingNumber;
    @FXML
    private TableColumn<?, ?> viewTime;
    @FXML
    private TableColumn<?, ?> viewParasite;
    @FXML
    private Label viewSubjectNumber;
    @FXML
    private Label userLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onQuitApplication(ActionEvent event) {
    }

    @FXML
    private void saveLogging(ActionEvent event) {
    }

    @FXML
    private void closeAddLog(ActionEvent event) {
    }

    @FXML
    private void addLog(ActionEvent event) {
    }

    @FXML
    private void updateLog(ActionEvent event) {
    }

    @FXML
    private void deleteLog(ActionEvent event) {
    }

    @FXML
    private void close(ActionEvent event) {
    }

    @FXML
    private void viewSubject(ActionEvent event) {
    }

    @FXML
    private void enrollSubject(ActionEvent event) {
    }

    @FXML
    private void updateSubject(ActionEvent event) {
    }

    @FXML
    private void archiveSubject(ActionEvent event) {
    }

    @FXML
    private void closeSubject(ActionEvent event) {
    }

    @FXML
    private void fieldConnectLogout(ActionEvent event) {
    }
    
}
