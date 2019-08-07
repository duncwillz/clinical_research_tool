/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloc;

import dao.DBConnect;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
import model.Receive;
import model.Visits;
import utils.mediator;

/**
 *
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class ReceiveBloc {
    
    private static final ReceiveBloc RECEIVE_BLOC = new ReceiveBloc();
    mediator md = mediator.md();
    Receive ri = new Receive();
    ObservableList<Receive> data;
    
    private ReceiveBloc() {
        this.data = FXCollections.observableArrayList();
    }
    
    public static ReceiveBloc riBloc() {
        return RECEIVE_BLOC;
    }
    
    public Boolean isvalidatedFields(DatePicker date, TextField... textField) {
        if (date.getValue() == null || textField[0].getText().isEmpty() || textField[1].getText().isEmpty() || textField[2].getText().isEmpty() || textField[3].getText().isEmpty()) {
            md.note("Error", "All fields are required, description is optional");
            return false;
        }
        return true;
    }
    
    public void onSupplyAction(DatePicker date, int rrid, Button but, TextField... textField) {
        if (isvalidatedFields(date, textField)) {
            ri.setRisupplier(textField[0].getText());
            ri.setRiexpiry(md.convert(date.getValue()));
            ri.setRiinvoice(Integer.parseInt(textField[1].getText()));
            ri.setRinumb(Integer.parseInt(textField[2].getText()));
            ri.setRiitem(md.getItem().getIid());
            ri.setRiuser(md.getDbuser().getUid());
            if (!textField[3].getText().isEmpty()) {
                ri.setRidescription(textField[3].getText());
            } else {
                ri.setRidescription(null);
            }
            if (but.getText().equals("Receive In")) {
                
                md.note("Success", "Drug received");
            } else if (but.getText().equals("Update receive")) {
                md.note("Updated", "Drug receive updated");
            }
        }
    }
    
    public void onClearAction(DatePicker date, Button bt, TextField... fields) {
        date.setValue(null);
        bt.setText("Receive in");
        for (TextField field : fields) {
            field.setText("");
        }
    }
    
    public void populateTable(TableView tableView, TableColumn... col) {
        col[0].setCellValueFactory(new PropertyValueFactory<>("riitem"));
        col[0].setCellFactory(column -> {
            TableCell<Visits, Integer> cell = new TableCell<Visits, Integer>() {
                
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    try {
                        String itemName = DBConnect.getInstance().findItemById(item).getIname();
                        super.updateItem(item, empty);
                        setText(itemName);
                    } catch (Exception es) {
                    }
                }
            };
            return cell;
        });
        col[1].setCellValueFactory(new PropertyValueFactory<>("risupplier"));
        col[2].setCellValueFactory(new PropertyValueFactory<>("rinumb"));
        col[3].setCellValueFactory(new PropertyValueFactory<>("ridate"));
        col[3].setCellFactory(column -> {
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
        col[4].setCellValueFactory(new PropertyValueFactory<>("riexpiry"));
        col[4].setCellFactory(column -> {
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
        col[5].setCellValueFactory(new PropertyValueFactory<>("ridescription"));
        col[6].setCellValueFactory(new PropertyValueFactory<>("riinvoice"));
        col[7].setCellValueFactory(new PropertyValueFactory<>("riuser"));
        data = FXCollections.observableArrayList();
        data.addAll(DBConnect.getInstance().findAllReceive());
        tableView.setItems(data);
    }
    
    public void autoSearch(TextField searchText, TableView tableView) {
        FilteredList<Receive> filteredData = new FilteredList<>(data, e -> true);
        searchText.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Receive>) receive -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return DBConnect.getInstance().findItemById(receive.getRiitem()).getIname().toLowerCase().contains(newValue.toLowerCase());
            });
        });
        SortedList<Receive> sortdata = new SortedList<>(filteredData);
        sortdata.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortdata);
    }
    
    public Boolean onReceiveOrUpdate(Button saveButton, ActionEvent event, int id, DatePicker date, Label lab, TextField... text) {
        if (isValidated(date, text)) {
            ri.setRiitem(DBConnect.getInstance().findItemByName(lab.getText()).getIid());
            ri.setRisupplier(text[0].getText());
            ri.setRiinvoice(Integer.parseInt(text[1].getText()));
            ri.setRinumb(Integer.parseInt(text[2].getText()));
            ri.setRiexpiry(md.convert(date.getValue()));
            ri.setRidescription(text[3].getText());
            ri.setRiuser(md.getDbuser().getUid());
            if (saveButton.getText().equals("Receive In")) {
                if (DBConnect.getInstance().create(ri)) {
                    md.note("Successful", "Records saved");
                    refreshFields(date, text);
                }
            } else if (saveButton.getText().equals("Update Receive")) {
                ri.setRiid(id);
                if (md.AlertSelected(Alert.AlertType.INFORMATION, "Update", "Are sure you want to update OPD Records", " ", "Yes")) {
                    if (DBConnect.getInstance().update(ri)) {
                        md.note("Successful", "Records updated");
                        saveButton.setText("Save");
                        refreshFields(date, text);
                    }
                } else {
                    
                }
            }
            return true;
        }
        return false;
    }
    
    public void onUpdate(TableView t, Receive re, DatePicker dp, Button b, TextField... text) {
        if (t.getSelectionModel().getFocusedIndex() < 0) {
            md.note("Error", "Please select a product to update");
        } else {
            text[0].setText(re.getRisupplier());
            text[1].setText(Integer.toString(re.getRiinvoice()));
            text[2].setText(Integer.toString(re.getRinumb()));
            dp.setValue(md.toLocalDate(re.getRiexpiry()));
            text[3].setText(re.getRidescription());
            b.setText("Update Receive");
        }
    }
    
    public boolean onDelete(TableView t, Receive re, DatePicker dp, Button b, TextField... text) {
        if (md.AlertSelected(Alert.AlertType.WARNING, "Delete", "Are you sure you want to delete this item", "This action cannot be undone, do you want to continue?", "Yes")) {
            if (t.getSelectionModel().getFocusedIndex() < 0) {
                md.note("Error", "Please select a product to update");
                return false;
            } else {
                if (DBConnect.getInstance().delete((Receive) t.getSelectionModel().getSelectedItem())) {
                    refreshFields(dp, text);
                    md.note("Successful", "You have successful delete the data");
                }
            }
        }
        
        return false;
    }
    
    public void refreshFields(DatePicker date, TextField... t) {
        date.setValue(null);
        for (TextField _t : t) {
            _t.setText("");
        }
    }
    
    public Boolean isValidated(DatePicker date, TextField... text) {
        for (TextField field : text) {
            if (field.getText().isEmpty()) {
                md.note("error", "All feilds are required");
                return false;
            }
        }
        if (date.getValue() == null) {
            md.note("error", "All feilds are required");
            return false;
        }
        return true;
    }
    
}
