/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ComboBoxAutoCompletes {
 public class ComboBoxAutoComplete<T> {

        private ComboBox<T> cmb;
        String filter = "";
        private ObservableList<T> originalItems;

        public ComboBoxAutoComplete(ComboBox<T> cmb) {
            this.cmb = cmb;
            originalItems = FXCollections.observableArrayList(cmb.getItems());
            cmb.setTooltip(new Tooltip());
            cmb.setOnKeyPressed(this::handleOnKeyPressed);
            cmb.setOnHidden(this::handleOnHiding);
        }

        public void handleOnKeyPressed(KeyEvent e) {
            ObservableList<T> filteredList = FXCollections.observableArrayList();
            KeyCode code = e.getCode();

            if (code.isLetterKey()) {
                filter += e.getText();
            }
            if(code.isDigitKey()){
                filter += e.getText();
            }
            if(code.isKeypadKey()){
                filter += e.getText();
            }
            if (code == KeyCode.BACK_SPACE && filter.length() > 0) {
                filter = filter.substring(0, filter.length() - 1);
                cmb.getItems().setAll(originalItems);
            }
            if (code == KeyCode.ESCAPE) {
                filter = "";
            }
            if (filter.length() == 0) {
                filteredList = originalItems;
                cmb.getTooltip().hide();
            } else {
                Stream<T> itens = cmb.getItems().stream();
                String txtUsr = filter.toString().toLowerCase();
                itens.filter(el -> el.toString().toLowerCase().contains(txtUsr)).forEach(filteredList::add);
                cmb.getTooltip().setText(txtUsr);
                Window stage = cmb.getScene().getWindow();
                double posX = stage.getX() + cmb.getBoundsInParent().getMinX();
                double posY = stage.getY() + cmb.getBoundsInParent().getMinY();
                cmb.getTooltip().show(stage, posX, posY);
                cmb.show();
            }
            cmb.getItems().setAll(filteredList);
        }

        public void handleOnHiding(Event e) {
            filter = "";
            cmb.getTooltip().hide();
            T s = cmb.getSelectionModel().getSelectedItem();
            cmb.getItems().setAll(originalItems);
            cmb.getSelectionModel().select(s);
        }
    }

    public class ComboBoxAutoCompleteNumber<T> {

        private ComboBox<T> cmb;
        int filter = 0;
        private ObservableList<T> originalItems;

        public ComboBoxAutoCompleteNumber(ComboBox<T> cmb) {
            this.cmb = cmb;
            originalItems = FXCollections.observableArrayList(cmb.getItems());
            cmb.setTooltip(new Tooltip());
            cmb.setOnKeyPressed(this::handleOnKeyPressed);
            cmb.setOnHidden(this::handleOnHiding);
        }

        public void handleOnKeyPressed(KeyEvent e) {
            ObservableList<T> filteredList = FXCollections.observableArrayList();
            KeyCode code = e.getCode();

            if (code.isDigitKey()) {
                filter = Integer.parseInt(e.getText());
            }
            if (code == KeyCode.BACK_SPACE && filter > 0) {
                filter = 0;
                cmb.getItems().setAll(originalItems);
            }
            if (code == KeyCode.ESCAPE) {
                filter = 0;
            }
            if (filter == 0) {
                filteredList = originalItems;
                cmb.getTooltip().hide();
            } else {
                Stream<T> itens = cmb.getItems().stream();
                String txtUsr = Integer.toString(filter).toString().toLowerCase();
                itens.filter(el -> el.toString().toLowerCase().contains(txtUsr)).forEach(filteredList::add);
                cmb.getTooltip().setText(txtUsr);
                Window stage = cmb.getScene().getWindow();
                double posX = stage.getX() + cmb.getBoundsInParent().getMinX();
                double posY = stage.getY() + cmb.getBoundsInParent().getMinY();
                cmb.getTooltip().show(stage, posX, posY);
                cmb.show();
            }
            cmb.getItems().setAll(filteredList);
        }

        public void handleOnHiding(Event e) {
            filter = 0;
            cmb.getTooltip().hide();
            T s = cmb.getSelectionModel().getSelectedItem();
            cmb.getItems().setAll(originalItems);
            cmb.getSelectionModel().select(s);
        }
    }
   

}
