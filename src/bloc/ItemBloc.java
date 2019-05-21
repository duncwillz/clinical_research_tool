/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloc;

import dao.DBConnect;
import java.sql.Array;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Daily;
import model.Item;
import model.User;
import utils.mediator;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ItemBloc {

    private final static String E = "emergency";
    private final static String S = "supply";
    private final static String V = "view";
    private String showAs;
    mediator md = mediator.md();
    Item item = new Item();
    private static final ItemBloc ITEM_BLOC = new ItemBloc();
    ObservableList<Item> data;
    private final SimpleDateFormat format = new SimpleDateFormat("E, dd-MMM-yyyy");

    private ItemBloc() {
        this.data = FXCollections.observableArrayList();

    }

    public static ItemBloc iBloc() {
        return ITEM_BLOC;
    }

    public boolean isValidTextField(TextField textField) {
        if (textField.getText().isEmpty()) {
            md.note("Error", "Please product field cannot be empty");
            return false;
        }
        return true;
    }

    public Boolean addOrUpdateProduct(ActionEvent event, TextField textField, Button but, int iid) {
        if (isValidTextField(textField)) {
            item.setIname(textField.getText());
            item.setIuser(md.getId());
            if (but.getText().equals("Add Product")) {
                if (DBConnect.getInstance().create(item)) {
                    md.note("Successful", "Product added");
                }
            } else if (but.getText().equals("Update Product")) {
                item.setIid(iid);
                if (DBConnect.getInstance().update(item)) {
                    md.note("Successfule", "Product updated");
                    but.setText("Add Product");
                }
            }
            refresh(textField);
            return true;
        }
        return false;
    }

    public void onUpdateClick(TableView table, Item item, TextField text, Button but) {
        if (validateSelection(table)) {
            but.setText("Update Product");
            text.setText(item.getIname());
        }
    }

    public boolean onDeleteClick(TableView table, Button but, TextField text) {
        if(md.AlertSelected(Alert.AlertType.WARNING, "Delete","Are you sure you want to delete this item","This action cannot be undone, do you want to continue?","Yes")){
        if (validateSelection(table)) {
            if (DBConnect.getInstance().delete((Item) table.getSelectionModel().getSelectedItem())) {
                refresh(text);
                return true;
            }          
        } 
        }
        
        return false;
    }

    public boolean validateSelection(TableView table) {
        if (table.getSelectionModel().getSelectedIndex() < 0) {
            md.note("Error", "Please select a product to update");
            return false;
        }
        return true;
    }

    private void refresh(TextField text) {
        text.setText("");
    }

    public void populateTable(TableView table, TableColumn... col) {
        col[0].setCellValueFactory(new PropertyValueFactory<>("iid"));
        col[1].setCellValueFactory(new PropertyValueFactory<>("iname"));
        col[2].setCellValueFactory(new PropertyValueFactory<>("idate"));
        col[2].setCellFactory(column -> {
            TableCell<Item, Date> cell = new TableCell<Item, Date>() {
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
        col[3].setCellValueFactory(new PropertyValueFactory<>("iuser"));
//        col[3].setCellFactory(column -> {
//            TableCell<Item, Integer> cell = new TableCell<Item, Integer>() {
//                @Override
//                protected void updateItem(Integer item, boolean empty) {
//                    try {
//                        super.updateItem(item, empty);
//                        User user = DBConnect.getInstance().findUserByUserId(item);
//                        if (empty) {
//                            setText(null);
//                        } else {
//                            setText(user.getUfullname());
//                        }
//                    } catch (Exception e) {
//                    }
//
//                }
//            };
//            return cell;
//        });
        data = FXCollections.observableArrayList();
        data.addAll(DBConnect.getInstance().findAllItems());
        table.setItems(data);
    }

    public void autoSearch(TextField searchText, TableView tableView) {
        FilteredList<Item> filteredData = new FilteredList<>(data, e -> true);
        searchText.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Item>) item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return item.getIname().toLowerCase().contains(newValue.toLowerCase());
            });
        });
        SortedList<Item> sortdata = new SortedList<>(filteredData);
        sortdata.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortdata);
    }

    public void onSupplySelected(TableView tab, GridPane sg, GridPane eg, GridPane vg, Item item, Label... l) {
        if (validateSelection(tab)) {
            showView(S, sg, eg, vg);
            md.setItem(item);
            l[0].setText("Name: "+item.getIname());
            l[1].setText("Added by: "+DBConnect.getInstance().findUserByUserId(item.getIuser()).getUfullname());
            l[2].setText("Date: "+format.format(item.getIdate()));
        }
    }

    public void onEmergencyPuchaseClicked(TableView tab, Item item, GridPane sg, GridPane eg, GridPane vg, Label... l) {
        if (validateSelection(tab)) {
            showView(E, sg, eg, vg);
            md.setItem(item);
            l[0].setText("Name: "+item.getIname());
            l[1].setText("Added by: "+DBConnect.getInstance().findUserByUserId(item.getIuser()).getUfullname());
            l[2].setText("Date: "+format.format(item.getIdate()));
        }
    }

    public void onCloseViewEvent(GridPane... grid) {
        switch (showAs) {
            case S:
                showView(showAs, grid);
                break;
            case E:
                showView(showAs, grid);
                break;
        }
    }

    public void showView(String showFrom, GridPane... grid) {
        switch (showFrom) {
            case S:
                grid[0].setVisible(true);
                grid[1].setVisible(false);
                grid[2].setVisible(false);
                md.Translate(grid[0]);
                showAs = S;
                break;
            case E:
                grid[0].setVisible(false);
                grid[1].setVisible(true);
                grid[2].setVisible(false);
                md.Translate(grid[1]);
                showAs = E;
                break;
            case V:
                grid[0].setVisible(false);
                grid[1].setVisible(false);
                grid[2].setVisible(true);
                md.Translate(grid[2]);
                break;
        }

    }
}
