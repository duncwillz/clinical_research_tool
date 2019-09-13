/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bloc_factory;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public interface BlocInterface {
    public void populateCombox(ComboBox ...box);
    public void populateTable(TableView tableView, ComboBox<String> visitType, TableColumn... col);
    public void populateSecondTable(TableView tableView, ComboBox<String> visitType, TableColumn... col);
    public void autoSearch(TextField searchText, TableView tableView);
}
