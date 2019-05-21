/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.DBConnect;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import utils.mediator;

/**
 * FXML Controller class
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class LoginController implements Initializable {

    mediator md = mediator.md();
    private User user;
    @FXML
    private TextField uname;
    @FXML
    private PasswordField upass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        user = new User();
    }

    @FXML
    void login(ActionEvent event) {
        if (isValidated()) {
            user.setUname(uname.getText().trim());
            user.setUpass(upass.getText().trim());
            List<User> users = DBConnect.getInstance().findUserByUsernameAndPassword(user);
            if (users.size() > 0) {
                md.setName(users.get(0).getUfullname());
                md.setId(users.get(0).getUid());
                switch (users.get(0).getUdepartment()) {
                    case "Field":
                        md.callback(event, "/views/field.fxml", true);
                        break;
                    case "Data":
                        md.callback(event, "/views/data.fxml", true);
                        break;
                    case "Lab":
                        md.callback(event, "/views/lab.fxml", true);
                        break;
                    case "Stores":
                        md.callback(event, "/views/stores.fxml", true);
                        break;
                }
            } else {
                md.note("Error", "Username or Password is incorrect");
            }
        } else {
            md.note("Error", "Please provide username or password");
        }
    }

    private Boolean isValidated() {
        return !(uname.getText().isEmpty() || upass.getText().isEmpty());
    }

    @FXML
    private void close(ActionEvent event) {
        md.close(event);
    }

}
