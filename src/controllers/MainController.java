/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import bloc.DailyOutpatientBloc;
import bloc.InpatientBloc;
import bloc.ItemBloc;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Daily;
import model.Inpatients;
import model.Item;
import model.View;
import utils.ComboBoxAutoCompletes;
import utils.mediator;

/**
 * FXML Controller class
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class MainController implements Initializable {

    mediator md = mediator.md();

    ComboBoxAutoCompletes combo = new ComboBoxAutoCompletes();
    //DAILY OUTPATIENTS
    DailyOutpatientBloc dBloc = DailyOutpatientBloc.bBloc();
    //ITEM PRODUCT
    ItemBloc iBloc = ItemBloc.iBloc();
    //INPATIENT BLOC
    InpatientBloc inpBloc = InpatientBloc.inpBloc();
    
    @FXML
    private GridPane dailyGridPane;

    @FXML
    private GridPane viewGridPane;

    @FXML
    private TableView<View> dbViewTable;

    @FXML
    private TableColumn<View, String> dbViewTitleCol;

    @FXML
    private TableColumn<View, String> dbViewDetailsCol;

    @FXML
    private ComboBox<String> dbUserCombo;

    @FXML
    private Label dbSubjectNameLabel;

    @FXML
    private Label dbSubjectDOBLabel;

    @FXML
    private Label userLogin;

    @FXML
    private Label dbDateNow;

    @FXML
    private Label dbSubjectComLabel;

    @FXML
    private ComboBox<String> dbSubjectNumberCombo;

    @FXML
    private TextField dbWeight;

    @FXML
    private TextField dbTemp;

    @FXML
    private Button dbSaveOrUpdateButton;

    @FXML
    private CheckBox dbNewCaseCheck;

    @FXML
    private CheckBox dbReviewCheck;

    @FXML
    private TextField dbSearchTextfield;

    @FXML
    private TableView<Daily> dbTableView;

    @FXML
    private TableColumn<Daily, Integer> dbPidCol;

    @FXML
    private TableColumn<Daily, Date> dbDateCol;

    @FXML
    private TableColumn<Daily, String> dbCaseCol;

    @FXML
    private TableColumn<Daily, Double> dbTempCol;

    @FXML
    private TableColumn<Daily, Double> dbWeightCol;

    @FXML
    private TableColumn<Daily, Integer> dbUserCol;

    //ITEM
    @FXML
    private TextField inameTextField;
    @FXML
    private Button iAddOrUpdateButton;
    @FXML
    private TextField iSearchTextField;
    @FXML
    private TableView<Item> iTableView;
    @FXML
    private TableColumn<Item, Integer> iidCol;
    @FXML
    private TableColumn<Item, String> inameCol;
    @FXML
    private TableColumn<Item, Date> idateCol;
    @FXML
    private TableColumn<Item, String> iAddedByCol;
    @FXML
    private Label ioiAddedBy;
    @FXML
    private Label ioiDate;
    @FXML
    private Label ioiName;
    @FXML
    private GridPane iSupplyView;
    @FXML
    private GridPane iEmergencyView;

    @FXML
    private Label epiName;
    @FXML
    private Label epiAddedBy;
    @FXML
    private Label epiDate;
    @FXML
    private Label epiCuurentDate;
    @FXML
    private BorderPane ioTopView;
    @FXML
    private TextField riNReceive;
    @FXML
    private TextField riStockLeft;
    @FXML
    private TextField riDescription;
    @FXML
    private TextField riSupplyName;
    @FXML
    private TextField riInvoiceNumber;
    @FXML
    private DatePicker riExpiryDate;
    @FXML
    private BorderPane ioDownView;
    @FXML
    private BorderPane ioTopView1;
    @FXML
    private BorderPane ioDownView1;
    @FXML
    private GridPane viewEvent;
    @FXML
    private TableView<View> veTableView;
    @FXML
    private TableColumn<View, String> veTitleCol;
    @FXML
    private TableColumn<View, String> veDetailCol;
    @FXML
    private ComboBox<String> inpuserCombo;
    @FXML
    private Label inpSubjectNameLabel;
    @FXML
    private Label inpSubjectDOBLabel;
    @FXML
    private Label inpDateNow;
    @FXML
    private Label inpSubjectComLabel;
    @FXML
    private ComboBox<String> inpSubjectNumberCombo;
    @FXML
    private Button inpSaveOrUpdateButton;
    @FXML
    private DatePicker inpAdmissionDate;
    @FXML
    private DatePicker inpDischargeDate;
    @FXML
    private TextField inpSearchTextfield;
    @FXML
    private TableView<Inpatients> inpTableView;
    @FXML
    private TableColumn<Inpatients, Integer> inpPidCol;
    @FXML
    private TableColumn<Inpatients, Date> inpDateCol;
    @FXML
    private TableColumn<Inpatients, Date> inpAdmissionCol;
    @FXML
    private TableColumn<Inpatients, Date> inpDischargeCol;
    @FXML
    private TableColumn<Inpatients, Integer> inpUserCol;
    @FXML
    private TableView<Inpatients> inpPendingTable;
    @FXML
    private TableColumn<Inpatients, Integer> inpPendingPidCol;
    @FXML
    private TableColumn<Inpatients, Date> inpPendingAdmissionDateCol;
    @FXML
    private GridPane inpGridPane;
    @FXML
    private GridPane inpViewGridPane;
    @FXML
    private TableView<View> inpViewTable;
    @FXML
    private TableColumn<View, String> inpViewTitleCol;
    @FXML
    private TableColumn<View, String> inpViewDetailsCol;
    @FXML
    private Button riReceiveInOrUpdateButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableColumn<?, ?> riItemCol;
    @FXML
    private TableColumn<?, ?> riSupplierCol;
    @FXML
    private TableColumn<?, ?> riNoReceiveCol;
    @FXML
    private TableColumn<?, ?> riDateReceiveCol;
    @FXML
    private TableColumn<?, ?> riExpiryCol;
    @FXML
    private TableColumn<?, ?> riDescriptionCol;
    @FXML
    private TableColumn<?, ?> riInvoiceCol;
    @FXML
    private TableColumn<?, ?> riUserCol;

    @FXML
    void dbClearDetails(ActionEvent event) {
        refreshDailyBloc();
        dbSaveOrUpdateButton.setText("Save");
    }

    @FXML
    void dbDeleteButton(ActionEvent event) {
            if (dBloc.deleteAction(dbTableView)) {
                refreshDailyBloc();
        }
    }

    @FXML
    void dbSaveOrUpdate(ActionEvent event) {
        int pid;
        if (dbTableView.getSelectionModel().getSelectedIndex() < 0) {
            pid = 0;
        } else {
            pid = dbTableView.getSelectionModel().getSelectedItem().getDid();
        }
        if (dBloc.saveOrUpdate(dbSaveOrUpdateButton, event, dbWeight, dbTemp, dbNewCaseCheck, dbReviewCheck, pid, dbUserCombo, dbSubjectNumberCombo)) {
            refreshDailyBloc();
            dBlocInitializes();
        }
    }

    @FXML
    void dbUpdateButton(ActionEvent event) {
        refreshDailyBloc();
        dbSaveOrUpdateButton.setText("Update");
        dBloc.onUpdateClick(dbTableView, dbTableView.getSelectionModel().getSelectedItem(), dbUserCombo, dbSubjectNumberCombo, dbTemp, dbWeight, dbSubjectNameLabel, dbSubjectDOBLabel, dbSubjectComLabel);
        dBloc.checkStatus(dbTableView.getSelectionModel().getSelectedItem().getDcasetype(), dbNewCaseCheck, dbReviewCheck);

    }

    @FXML
    void dbViewButton(ActionEvent event) {
        if (dBloc.showAction(dbTableView, dbViewTable, dbViewTitleCol, dbViewDetailsCol)) {
            dBloc.showView(viewGridPane, dailyGridPane);
        }
    }

    @FXML
    void closeView(ActionEvent event) {
        dBloc.showView(viewGridPane, dailyGridPane);
    }

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dBlocInitializes();
        userLoggedIn();
        initializeItemBloc();
        initializeInpatient();
    }

    public void dBlocInitializes() {
        dBloc.populateCombox(dbUserCombo, dbSubjectNumberCombo);
        combo.new ComboBoxAutoComplete<String>(dbUserCombo);
        combo.new ComboBoxAutoComplete<String>(dbSubjectNumberCombo);
        dBloc.nowDate(dbDateNow);
        dBloc.onSelectSubject(dbSubjectNameLabel, dbSubjectDOBLabel, dbSubjectComLabel, dbUserCombo, dbSubjectNumberCombo);
        dBloc.populateTable(dbTableView, dbWeightCol, dbTempCol, dbPidCol, dbCaseCol, dbUserCol, dbDateCol);
        dBloc.autoSearch(dbSearchTextfield, dbTableView);
    }

    public void refreshDailyBloc() {
        dBloc.refreshLabel(dbSubjectNameLabel, dbSubjectDOBLabel, dbSubjectComLabel);
        dBloc.refreshFields(dbWeight, dbTemp, dbNewCaseCheck, dbReviewCheck, dbUserCombo, dbSubjectNumberCombo);
        dBloc.populateTable(dbTableView, dbWeightCol, dbTempCol, dbPidCol, dbCaseCol, dbUserCol, dbDateCol);
    }

    public void userLoggedIn() {
        userLogin.setText(md.getName());
    }

    //ITEM ACTION
    public void initializeItemBloc() {
        iBloc.populateTable(iTableView, iidCol, inameCol, idateCol, iAddedByCol);
        iBloc.autoSearch(iSearchTextField, iTableView);
    }
    
    public void initializeInpatient(){
        dBloc.onSelectSubject(inpSubjectNameLabel, inpSubjectDOBLabel, inpSubjectComLabel, inpuserCombo,inpSubjectNumberCombo);
        dBloc.populateCombox(inpuserCombo,inpSubjectNumberCombo);
        dBloc.nowDate(inpDateNow);
        combo.new ComboBoxAutoComplete<String>(inpuserCombo);
        combo.new ComboBoxAutoComplete<String>(inpSubjectNumberCombo);
        inpBloc.populateTable(inpTableView, inpPidCol,inpAdmissionCol, inpDischargeCol,inpUserCol,inpDateCol);
        inpBloc.populatePendingDischarge(inpPendingTable, inpPendingPidCol,inpPendingAdmissionDateCol);
        inpBloc.autoSearch(inpSearchTextfield, inpTableView);
    }
    

    @FXML
    void iAddOrUpdate(ActionEvent event) {
          int iid;
        if (iTableView.getSelectionModel().getSelectedIndex() < 0) {
            iid = 0;
        } else {
            iid = iTableView.getSelectionModel().getSelectedItem().getIid();
        }
            if (iBloc.addOrUpdateProduct(event, inameTextField, iAddOrUpdateButton, iid)) {
                initializeItemBloc();
            } 
    }

    @FXML
    void iUpdateAction(ActionEvent event) {
        iBloc.onUpdateClick(iTableView, iTableView.getSelectionModel().getSelectedItem(), inameTextField, iAddOrUpdateButton);
    }

    @FXML
    void iDeleteAction(ActionEvent event) {
       if(iBloc.onDeleteClick(iTableView, iAddOrUpdateButton, inameTextField)){
        initializeItemBloc();
       }
    }

    @FXML
    void iSupplyAction(ActionEvent event){
        iBloc.onSupplySelected(iTableView, iSupplyView, iEmergencyView, viewEvent , iTableView.getSelectionModel().getSelectedItem(),ioiName, ioiAddedBy, ioiDate);
    }

    @FXML
    void iEmergencyPurchase(ActionEvent event){
        iBloc.onEmergencyPuchaseClicked(iTableView, iTableView.getSelectionModel().getSelectedItem(), iSupplyView, iEmergencyView, viewEvent, epiName, epiAddedBy, epiDate);
    }


    @FXML
    private void onCloseViewEvent(ActionEvent event){
        iBloc.onCloseViewEvent( iSupplyView, iEmergencyView, viewEvent);
    }

    @FXML
    private void inpSaveOrUpdate(ActionEvent event) {
        int inpid;
        if (inpTableView.getSelectionModel().getSelectedIndex() < 0) {
            inpid = 0;
        } else {
            inpid = inpTableView.getSelectionModel().getSelectedItem().getInpid();
        }
       if(inpBloc.saveOrUpdateInpatient(inpAdmissionDate, inpDischargeDate, inpSaveOrUpdateButton, inpid, inpuserCombo, inpSubjectNumberCombo,inpSubjectNameLabel, inpSubjectDOBLabel, inpSubjectComLabel)){
           initializeInpatient();                   
        }
    }

    @FXML
    private void inpClearDetails(ActionEvent event) {
        dBloc.refreshLabel(inpSubjectNameLabel, inpSubjectDOBLabel, inpSubjectComLabel);
        inpBloc.refreshFields(inpAdmissionDate, inpDischargeDate, inpuserCombo,inpSubjectNumberCombo);
        
    }

    @FXML
    private void inpViewButton(ActionEvent event){
        if (inpBloc.showInpatientsAction(inpTableView, inpViewTable, inpViewTitleCol,inpViewDetailsCol)){
            dBloc.showView(inpViewGridPane, inpGridPane);
        }
    }

    @FXML
    private void inpUpdateButton(ActionEvent event){
        inpBloc.updateSelected(inpTableView, inpSaveOrUpdateButton, inpTableView.getSelectionModel().getSelectedItem(), inpAdmissionDate, inpDischargeDate, inpuserCombo,inpSubjectNumberCombo,inpSubjectNameLabel, inpSubjectDOBLabel, inpSubjectComLabel);
    }

    @FXML
    private void inpDeleteButton(ActionEvent event){
       if (inpBloc.deleteSelected(inpTableView)){
        dBloc.refreshLabel(inpSubjectNameLabel, inpSubjectDOBLabel, inpSubjectComLabel);
        inpBloc.refreshFields(inpAdmissionDate, inpDischargeDate, inpuserCombo,inpSubjectNumberCombo);
         initializeInpatient();                   
       }
    }

    @FXML
    private void inpCloseView(ActionEvent event) {
      dBloc.showView(inpViewGridPane, inpGridPane);
    }

    @FXML
    private void riOnRecieveOrUpdateReceive(ActionEvent event) {
    }

    @FXML
    private void riOnClearAll(ActionEvent event) {
    }

    @FXML
    private void riOnUpdate(ActionEvent event) {
    }

    @FXML
    private void riOnDelete(ActionEvent event) {
    }


}
