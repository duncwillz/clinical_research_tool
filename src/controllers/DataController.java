/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import bloc.DataDashboardBloc;
import bloc.DemoReportBloc;
import bloc.WithdrawalReportsBloc;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.VisitsCount;

/**
 * FXML Controller class
 *
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class DataController implements Initializable {
    
    DataDashboardBloc dboardBloc = DataDashboardBloc.dataDashboardBloc();
    DemoReportBloc drBloc = DemoReportBloc.demoReportBloc();
    WithdrawalReportsBloc wrBloc = WithdrawalReportsBloc.withdrawalReportBloc();

    @FXML
    private Label userLogin;
    @FXML
    private Label dbSuccessfulLabel;
    @FXML
    private PieChart dbGenderDistributionPie;
    @FXML
    private PieChart dbAEnSAEPie;
    @FXML
    private TableView<VisitsCount> dbVisitsCounts;
    @FXML
    private TableColumn<?, ?> dbVisitsNameCol;
    @FXML
    private TableColumn<?, ?> dbTotalVisitsCol;
    @FXML
    private Label dbActiveParticipants;
    @FXML
    private Label dbWithdrawalsLabel;
    @FXML
    private Label dbLostToFollowUpLabel;
    @FXML
    private PieChart dbVisitsPerformsAndSkippedPie;
    @FXML
    private TextField drSearchTextfield;
    @FXML
    private TableView<?> drTableView;
    @FXML
    private TableColumn<?, ?> drSidCol;
    @FXML
    private TableColumn<?, ?> drNameCol;
    @FXML
    private Label drSidLabel;
    @FXML
    private Label drDOBLabel;
    @FXML
    private Label drCommunityLabel;
    @FXML
    private Label drFieldWorkerLabel;
    @FXML
    private Label drStudyGroupLabel;
    @FXML
    private Label drContactLabel;
    @FXML
    private Label drAgeLabel;
    @FXML
    private Label drNameLabel;
    @FXML
    private Label drGenderLabel;
    @FXML
    private Label drStatusLabel;
    @FXML
    private ComboBox<?> vrSelectVisitForExportCombo;
    @FXML
    private ComboBox<?> vrSelectSubjectNumbeCombo;
    @FXML
    private Label vrNextVisitLabel;
    @FXML
    private Label vrStartDateLabel;
    @FXML
    private Label vrEndDateLabel;
    @FXML
    private Label vrDaysLeft;
    @FXML
    private TableColumn<?, ?> vrVisitCol;
    @FXML
    private TableColumn<?, ?> vrStartDateCol;
    @FXML
    private TableColumn<?, ?> vrEndDateCol;
    @FXML
    private TableColumn<?, ?> vrActualDateCol;
    @FXML
    private TableColumn<?, ?> vrWindowPeriodCol;
    @FXML
    private Label vrNameLabel;
    @FXML
    private Label vrGroupLabel;
    @FXML
    private Label vrDateOfBirthLabel;
    @FXML
    private Label vrCommunityLabel;
    @FXML
    private TextField wrSearchTextfield;
    @FXML
    private TableColumn<?, ?> wrSIDCol;
    @FXML
    private TableColumn<?, ?> drNameCol1;
    @FXML
    private TableColumn<?, ?> wrDateWithdrawalCol;
    @FXML
    private TableColumn<?, ?> wrReasonWithdrawalCol;
    @FXML
    private TableColumn<?, ?> wrDecisionCol;
    @FXML
    private Label wrSIDLabel;
    @FXML
    private Label wrDateOfWithdrawalLabel;
    @FXML
    private Label wrDecisionLabel;
    @FXML
    private Label wrUserLabel;
    @FXML
    private Label wrNameLabel;
    @FXML
    private Label wrReasonLabel;
    @FXML
    private Label wrTotalLabel;
    @FXML
    private TableColumn<?, ?> psSIDCol;
    @FXML
    private TableColumn<?, ?> psPreviousDateCol;
    @FXML
    private TableColumn<?, ?> psNextVisitCol;
    @FXML
    private TableColumn<?, ?> psStartDateCol;
    @FXML
    private TableColumn<?, ?> psEndDateCol;
    @FXML
    private TableColumn<?, ?> psDaysLeftCol;
    @FXML
    private TextField psSearchTextField;
    @FXML
    private ComboBox<?> psSelectVisitCombo;
    @FXML
    private DatePicker vrStartDateFrom;
    @FXML
    private DatePicker vrEndDateTo;
    @FXML
    private ComboBox<?> srSelectSubjectCombo;
    @FXML
    private Label srNameLabel;
    @FXML
    private Label srGroupLabel;
    @FXML
    private Label srDOBLabel;
    @FXML
    private TextField srDiagnosis;
    @FXML
    private DatePicker srAdmissionDate;
    @FXML
    private DatePicker srDischargeDate;
    @FXML
    private Label vrDateOfBirthLabel11;
    @FXML
    private TableColumn<?, ?> srSIDCol;
    @FXML
    private TableColumn<?, ?> srAdmissionDateCol;
    @FXML
    private TableColumn<?, ?> srDischargeDateCol;
    @FXML
    private TableColumn<?, ?> srDiagnosisCol;
    @FXML
    private TableColumn<?, ?> srUserCol;
    @FXML
    private TextField srSearchTextBox;
    @FXML
    private Label srTotalSAEsLabel;
    @FXML
    private DatePicker srAdmissionDateSearch;
    @FXML
    private DatePicker srDischargeDateSearch;
    @FXML
    private TableView<?> vrSummayTableView;
    @FXML
    private TableView<?> vrTableViewSubjectProject;
    @FXML
    private TableView<?> wrTableView;
    @FXML
    private TableView<?> psTableView;
    @FXML
    private ComboBox<?> arSelectSubjectCombo;
    @FXML
    private Label arNameLabel;
    @FXML
    private Label arGroupLabel;
    @FXML
    private Label arDOBLabel;
    @FXML
    private DatePicker arSearchByDate;
    @FXML
    private Label arDateNow;
    @FXML
    private TableView<?> arOPDTable;
    @FXML
    private TableColumn<?, ?> arSIDCol;
    @FXML
    private TableColumn<?, ?> arVisitDateCol;
    @FXML
    private TableColumn<?, ?> arTimeCol;
    @FXML
    private TableColumn<?, ?> arTempCol;
    @FXML
    private TableColumn<?, ?> srSIDLabCol;
    @FXML
    private TableColumn<?, ?> arVisitDateLabCol;
    @FXML
    private TableColumn<?, ?> arFTALabCol;
    @FXML
    private TableColumn<?, ?> arRequisitionLabCol;
    @FXML
    private TableColumn<?, ?> arUserLabCol;
    @FXML
    private TextField arLabSearchTextfield;
    @FXML
    private DatePicker arSearchLabByDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initDashboard();
        initDemoReport();
        initWithdrawalReport();
    }    

    @FXML
    private void onQuitApplication(ActionEvent event) {
    }


    @FXML
    private void fieldConnectLogout(ActionEvent event) {
    }
    
    
    public void initDashboard(){
    dboardBloc.populatePires(dbGenderDistributionPie,dbVisitsPerformsAndSkippedPie,dbAEnSAEPie);
    
    }
    
    public void initDemoReport(){
    drBloc.populateTable(drTableView, drSidCol, drNameCol);
    drBloc.autoSearch(drSearchTextfield, drTableView);
    drBloc.onChangeKey(drTableView, drSidLabel, drNameLabel, drDOBLabel, drGenderLabel,drCommunityLabel,drFieldWorkerLabel,drStudyGroupLabel,drContactLabel, drAgeLabel, drStatusLabel);
    }

    public void initWithdrawalReport(){
    wrBloc.populateTable(wrTableView, wrSIDCol, wrDateWithdrawalCol, wrDecisionCol);
    wrBloc.autoSearch(wrSearchTextfield, wrTableView);
    wrBloc.getTotal(wrTotalLabel);
    wrBloc.onChangeKey(wrTableView, wrSIDLabel, wrNameLabel,wrDateOfWithdrawalLabel, wrReasonLabel, wrDecisionLabel,wrUserLabel);
    }
    
    @FXML
    private void drOnExportDemorgraphicData(ActionEvent event) {
        drBloc.exportAction();
    }

    @FXML
    private void drOnView(ActionEvent event) {
        drBloc.onViewSelected(drTableView, drSidLabel, drNameLabel, drDOBLabel, drGenderLabel,drCommunityLabel,drFieldWorkerLabel,drStudyGroupLabel,drContactLabel, drAgeLabel, drStatusLabel);
    }

    @FXML
    private void vrExportVisit(ActionEvent event) {
    }

    @FXML
    private void vrExportSkippedVisit(ActionEvent event) {
    }

    @FXML
    private void vrExportOutWindow(ActionEvent event) {
    }

    @FXML
    private void vrSummaryExportVisits(ActionEvent event) {
    }

    @FXML
    private void vrExportSummarySkipped(ActionEvent event) {
    }

    @FXML
    private void vrExportSummaryOutWindow(ActionEvent event) {
    }

    @FXML
    private void wrExportAll(ActionEvent event) {
      wrBloc.exportAction();
    }

    @FXML
    private void wrView(ActionEvent event) {
      wrBloc.onViewSelected(wrTableView, wrSIDLabel, wrNameLabel,wrDateOfWithdrawalLabel, wrReasonLabel, wrDecisionLabel,wrUserLabel);
    }

    @FXML
    private void psExportedSchedule(ActionEvent event) {
    }

    @FXML
    private void srSaveSAE(ActionEvent event) {
    }

    @FXML
    private void srClear(ActionEvent event) {
    }

    @FXML
    private void srUpdateSAE(ActionEvent event) {
    }

    @FXML
    private void srDeleteSAE(ActionEvent event) {
    }

    @FXML
    private void srExportAllorSelectedSAE(ActionEvent event) {
    }

    @FXML
    private void arExportStores(ActionEvent event) {
    }

    @FXML
    private void arExportLabs(ActionEvent event) {
    }
    
}
