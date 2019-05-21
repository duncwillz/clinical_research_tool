/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storeconnect;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.mediator;

/**
 *
 * @author kwakuadjei
 */
public class StoreConnect extends Application {
    mediator md  = mediator.md();
    String loginscreen="/views/login.fxml";
    
    @Override
    public void start(Stage primaryStage) {
       //TODO open the login screen 
       md.callBack(loginscreen,"STORE CONNECT SYSTEM", false,primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
