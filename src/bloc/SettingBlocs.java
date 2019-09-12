/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bloc;

import dao.DBConnect;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.Visits;
import utils.mediator;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class SettingBlocs {

    mediator md = mediator.md();
    Visits item = new Visits();
    private static final SettingBlocs SETTING_BLOC = new SettingBlocs();
    private SettingBlocs() {

    }

    public static SettingBlocs iBloc() {
        return SETTING_BLOC;
    }

    
    
      public void exportAction() {
            Stage pm = new Stage();
            String path = md.myPath(pm, "Choose directory");
            if (!path.isEmpty()) {
                if (DBConnect.getInstance().exportAllVisits(path)) {
                    if (md.AlertSelected(Alert.AlertType.INFORMATION, "Success", "Exported successfully", "Click Ok to open your root directory to get the excel file", "Ok")) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            if (desktop.isSupported(Desktop.Action.OPEN)) {
                                desktop.open(new File(path));//                            desktop.open(new File("/Users/kwakuadjei/Desktop/Exports/"));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    }
}