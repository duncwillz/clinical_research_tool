/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import bloc.DailyVisitsBloc;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.View;
import model.Visits;
import utils.ComboBoxAutoCompletes;
import utils.mediator;

/**
 * FXML Controller class
 *
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class FieldController implements Initializable {

    ComboBoxAutoCompletes combo = new ComboBoxAutoCompletes();
    mediator md = mediator.md();
    DailyVisitsBloc dvBloc = DailyVisitsBloc.dvBloc();

    @FXML
    private ComboBox<String> vsSelectVisitCombo;
    @FXML
    private DatePicker vsVisitDatePicker;
    @FXML
    private TextField vsSubjectNumber;
    @FXML
    private Button vsSaveButton;
    @FXML
    private TextField vsSearchTextfield;
    @FXML
    private TableColumn<Visits, Integer> vsPIDCol;
    @FXML
    private TableColumn<Visits, String> vsVisitsCol;
    @FXML
    private TableColumn<Visits, Date> vsVisitDateCol;
    @FXML
    private TableColumn<Visits, Integer> vsUserCol;
    @FXML
    private ComboBox<String> vsSearchVisitCombo;
    @FXML
    private Label userLogin;
    @FXML
    private TableView<Visits> vsTableView;
    @FXML
    private TableColumn<View, String> vsViewTitleCol;
    @FXML
    private TableColumn<View, String> vsViewDetailsCol;
    @FXML
    private GridPane vsGridPane;
    @FXML
    private GridPane vsViewGridPane;
    @FXML
    private TableView<View> vsViewTable;
    @FXML
    private Label vsSelectedDateLabel;
    @FXML
    private Label vsSelectedVisitLabel;
    @FXML
    private TableColumn<?, ?> vsSkippedCol;
    @FXML
    private CheckBox vsSkippedCheckbox;
    @FXML
    private Label vsTitle;
    @FXML
    private GridPane vsGridPane1;
    @FXML
    private GridPane vsViewGridPane1;
    @FXML
    private TableView<?> vsViewTable1;
    @FXML
    private TableColumn<?, ?> vsViewTitleCol1;
    @FXML
    private TableColumn<?, ?> vsViewDetailsCol1;
    @FXML
    private TextField vsSubjectNumber11;
    @FXML
    private Button vsSaveButton11;
    @FXML
    private CheckBox vsSkippedCheckbox11;
    @FXML
    private GridPane vsGridPane11;
    @FXML
    private TextField vsSearchTextfield11;
    @FXML
    private TableView<?> vsTableView11;
    @FXML
    private TableColumn<?, ?> vsPIDCol11;
    @FXML
    private TableColumn<?, ?> vsVisitsCol11;
    @FXML
    private TableColumn<?, ?> vsVisitDateCol11;
    @FXML
    private TableColumn<?, ?> vsSkippedCol11;
    @FXML
    private TableColumn<?, ?> vsUserCol11;
    @FXML
    private ComboBox<?> vsSearchVisitCombo11;
    @FXML
    private GridPane vsViewGridPane11;
    @FXML
    private TableView<?> vsViewTable11;
    @FXML
    private TableColumn<?, ?> vsViewTitleCol11;
    @FXML
    private TableColumn<?, ?> vsViewDetailsCol11;
    @FXML
    private Label wdSubjectNameLabel;
    @FXML
    private Label wdSubjectDOBLabel;
    @FXML
    private Label wdDateNow;
    @FXML
    private Label wdSubjectFWLabel;
    @FXML
    private ComboBox<?> wdSubjectSelectCombo;
    @FXML
    private Label wdSubjectCOMLabel;
    @FXML
    private TextField wdReasonTextfield;
    @FXML
    private Button wdSaveButton;
    @FXML
    private CheckBox wdDecisionInvestigator;
    @FXML
    private CheckBox wdMother;
    @FXML
    private DatePicker wdDateCompleted;
    @FXML
    private DatePicker wdLastContactDate;
    @FXML
    private CheckBox wdDecisionCoordinator;
    @FXML
    private TextField wdSearchTextfield;
    @FXML
    private TableView<?> wdTableView;
    @FXML
    private TableColumn<?, ?> wdPIDCol;
    @FXML
    private TableColumn<?, ?> wdWithdrawalCol;
    @FXML
    private TableColumn<?, ?> wdReasonCol;
    @FXML
    private TableColumn<?, ?> wdDecisionnByCol;
    @FXML
    private TableColumn<?, ?> wdUserCol;
    @FXML
    private Label hvSubjectNameLabel;
    @FXML
    private Label hvSubjectDOBLabel;
    @FXML
    private Label hvDateNow;
    @FXML
    private Label hvSubjectFWLabel;
    @FXML
    private ComboBox<?> hvSubjectSelectCombo;
    @FXML
    private Label hvSubjectCOMLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dvBloc.populateCombox(vsSelectVisitCombo, vsSearchVisitCombo);

        initializeVisitBloc();

        userLoggedIn();
    }

    @FXML
    private void vsOnSave(ActionEvent event) {
        int vsid;
        if (vsTableView.getSelectionModel().getSelectedIndex() < 0) {
            vsid = 0;
        } else {
            vsid = vsTableView.getSelectionModel().getSelectedItem().getVsid();
        }
        if (dvBloc.onSaveOrUpdate(vsSkippedCheckbox, vsid, vsVisitDatePicker, vsSubjectNumber, vsSelectVisitCombo, vsSaveButton)) {
            initializeVisitBloc();
        }
    }

    @FXML
    private void vsOnClear(ActionEvent event) {
        dvBloc.refreshField(vsSkippedCheckbox, vsVisitDatePicker, vsSubjectNumber, vsSaveButton);
    }

    @FXML
    private void vsViewButton(ActionEvent event) {
        if (dvBloc.showAction(vsTableView, vsViewTable, vsViewTitleCol, vsViewDetailsCol)) {
            dvBloc.showView(vsGridPane, vsViewGridPane);
        }
    }

    @FXML
    private void vsUpdateButton(ActionEvent event) {
        if (dvBloc.onUpdateClick(vsTableView, vsTableView.getSelectionModel().getSelectedItem(), vsSkippedCheckbox, vsSubjectNumber, vsSelectVisitCombo, vsVisitDatePicker, vsSaveButton)) {
            initializeVisitBloc();
        }
    }

    @FXML
    private void vsDeleteButton(ActionEvent event) {
        if (dvBloc.deleteClicked(vsTableView)) {
            initializeVisitBloc();
        }
    }

    @FXML
    private void vsCloseView(ActionEvent event) {
        dvBloc.showView(vsGridPane, vsViewGridPane);
    }

    public void userLoggedIn() {
        userLogin.setText(md.getName());
    }

    public void initializeVisitBloc() {
        dvBloc.onSelectDate(vsSelectedDateLabel, vsVisitDatePicker);
        dvBloc.onSelectVisit(vsSelectedVisitLabel, vsSelectVisitCombo);
        dvBloc.populateTable(vsTableView, vsSearchVisitCombo, vsPIDCol, vsVisitsCol, vsVisitDateCol, vsSkippedCol, vsUserCol);
        dvBloc.onSelectedSearchVisit(vsSearchVisitCombo, vsTableView, vsSearchVisitCombo.getValue(), vsSearchTextfield, vsPIDCol, vsVisitsCol, vsVisitDateCol, vsSkippedCol, vsUserCol);
        dvBloc.autoSearch(vsSearchTextfield, vsTableView);
        combo.new ComboBoxAutoComplete<String>(vsSelectVisitCombo);
        combo.new ComboBoxAutoComplete<String>(vsSearchVisitCombo);
    }

    @FXML
    private void fieldConnectLogout(ActionEvent event) {
        md.callback(event, "/views/login.fxml", false);
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void onQuitApplication(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void vsExport(ActionEvent event) {
        dvBloc.exportAction(vsSelectVisitCombo, vsVisitDatePicker);
    }

    @FXML
    private void wdOnSave(ActionEvent event) {
    }

    @FXML
    private void wdOnClear(ActionEvent event) {
    }

    @FXML
    private void wdViewButton(ActionEvent event) {
    }

    @FXML
    private void wdUpdateButton(ActionEvent event) {
    }

    @FXML
    private void wdDeleteButton(ActionEvent event) {
    }

    @FXML
    private void wdExportAll(ActionEvent event) {
    }


}
