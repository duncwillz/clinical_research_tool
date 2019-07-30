/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dao.DBConnect;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import model.Item;
import model.Subjects;
import model.User;
import org.controlsfx.control.Notifications;
import storeconnect.StoreConnect;

/**
 *
 * @author Duncan Adjei
 */
public class mediator {
    
    private final Map<String, String> intervals = new HashMap<>();
    Map<String, String> visits = new HashMap<>();

    public Date[] calcVisitInterval(Date date, String visit) {
        Date[] dates = new Date [2];
        setIntervals();
            Set set=intervals.entrySet(); 
            Iterator itr=set.iterator(); 
            while(itr.hasNext()){  
                Map.Entry entry=(Map.Entry)itr.next();  
                System.out.println("Compare to visit seleted and get the interval"+entry.getKey());
                System.out.println("This is the visit given from dBFunction----->"+visit);
                 if (entry.getKey().toString().equals(visit)){
                     System.out.println("The key with interval----->"+entry.getValue().toString());
                     String[] intervals_in = entry.getValue().toString().split("-");
                     dates[0] = convert(toLocalDate(date).plusDays(Long.parseLong(intervals_in[0])));
                     dates[1] = convert(toLocalDate(date).plusDays(Long.parseLong(intervals_in[1])));
                }
         }   
        return dates;
    }

    public int calcDaysLeft(Date endDate) {
        System.out.println(endDate);
        LocalDate d1 = endDate.toLocalDate();
        LocalDate localDate = LocalDate.now();
        java.time.Duration diff = java.time.Duration.between(localDate.atStartOfDay(), d1.atStartOfDay());
        long diffDays = diff.toDays();
        int i = (int)diffDays;
        return i;
    }
    
    public void setIntervals(){
        intervals.put("Visit 24", "77-105");
        intervals.put("Visit 27", "77-105");
        intervals.put("Visit 30", "77-105");
        intervals.put("Visit 33", "77-105");
        intervals.put("Visit 35", "77-105");
        intervals.put("Visit 36", "77-105");
        intervals.put("Visit 37", "77-105");
    }
    
    public void setDependents(){
     visits.put("Visit 26", "Visit 24");
     visits.put("Visit 29", "Visit 27");
     visits.put("Visit 32", "Visit 30");
     visits.put("Visit 35", "Visit 33");
     visits.put("Visit 36", "Visit 35");
     visits.put("Visit 37", "Visit 36");
     visits.put("Visit 38", "Visit 37");
    }
    
    public String dependentVisits(String visit){
        String depend = "";
        setDependents();
            Set set=visits.entrySet(); 
            Iterator itr=set.iterator();  
            while(itr.hasNext()){  
                Map.Entry entry=(Map.Entry)itr.next(); 
                System.out.println("Find dependent and compare to---->"+entry.getKey().toString());
                if (entry.getKey().toString().equals(visit)){
                 depend = entry.getValue().toString();
                }
            }
            System.out.println("Depended visit to return ---->"+depend);
         return depend;
    }


    int id;
    String indexnumber;
    String name;
    User dbuser;
    Subjects dbSubject;
    Item item;
    int lastVisit;

    public int getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(int lastVisit) {
        this.lastVisit = lastVisit;
    }
    
    

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getDbuser() {
        return dbuser;
    }

    public void setDbuser(User dbuser) {
        this.dbuser = dbuser;
    }

    public Subjects getDbSubject() {
        return dbSubject;
    }

    public void setDbSubject(Subjects dbSubject) {
        this.dbSubject = dbSubject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return indexnumber;
    }

    public void setRole(String indexnumber) {
        this.indexnumber = indexnumber;
    }
    private static final mediator MD = new mediator();

    private mediator() {
    }

    public static mediator md() {
        return MD;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void Translate(Node pane) {
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(200));
        tt.setNode(pane);
        tt.setByY(50);
        tt.setCycleCount(2);
        tt.setAutoReverse(true);
        tt.play();
    }

    public void note(String title, String text) {
        Notifications showNotice = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(4))
                .position(Pos.BASELINE_RIGHT);
        showNotice.darkStyle();
        Platform.runLater(() -> {
            showNotice.show();
        });
    }

    public java.sql.Timestamp dateToTimeStamp(java.sql.Date date) {
        java.sql.Timestamp newTimeStamp = new java.sql.Timestamp(date.getTime());
        return newTimeStamp;
    }

    public boolean AlertSelected(Alert.AlertType type, String... details) {
        Alert alert = new Alert(type);
        alert.setTitle(details[0]);
        alert.setHeaderText(details[1]);
        alert.setContentText(details[2]);
        ButtonType typeone = new ButtonType(details[3]);
        ButtonType typetwo = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(typeone, typetwo);
        Optional<ButtonType> results = alert.showAndWait();
        if (results.get() == typeone) {
            return true;
        } else if (results.get() == typetwo) {
            return false;
        }
        return false;
    }

    public String myPath(Stage pm, String title) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(title);
//        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(pm);
        return selectedDirectory.getPath();
    }

    public void close(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void callBack(String source, String title, boolean state, Stage primaryStage) {
        try {
            Parent myload = FXMLLoader.load(getClass().getResource(source));
            Scene scene = new Scene(myload);
            String style = getClass().getResource("/assets/css/Application.css").toExternalForm();
            scene.getStylesheets().addAll(style);
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image("/assets/images/schoollogo.png"));
            primaryStage.setMaximized(state);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(StoreConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void callback(ActionEvent event, String source, boolean state) {
        Stage primaryStage = new Stage();
        Pane root;
        try {
            root = FXMLLoader.load(getClass().getResource(source));
            Scene scene = new Scene(root);
            String style = getClass().getResource("/assets/css/Application.css").toExternalForm();
            scene.getStylesheets().addAll(style);
            primaryStage.setTitle("Store Connect");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(state);
            primaryStage.getIcons().add(new Image("/assets/images/schoollogo.png"));
            ((Node) event.getSource()).getScene().getWindow().hide();
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(storeconnect.StoreConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Fade(Node pane) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), pane);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    public void regulateView(boolean v1, boolean v2, Node... notes) {
        notes[0].setVisible(v1);
        notes[1].setVisible(v2);
        Translate(notes[2]);
    }

    public Date convert(LocalDate value) {
        Date date = Date.valueOf(value);
        return date;
    }

    public String toDateFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("d - MMM - yyyy");
        String finaldate = dateFormat.format(date);
        return finaldate;
    }

    public String toDateFormatFromString(java.sql.Timestamp date) {
        SimpleDateFormat format = new SimpleDateFormat("E, d - MMM - yyyy");
        return format.format(date);
    }

    public String toTimeFormatFromString(Time time){
    SimpleDateFormat format = new SimpleDateFormat("H:m");
        return format.format(time);
    }
    public LocalDate toLocalDate(Date date) {
        return date.toLocalDate();
    }

    public String toStringInt(int number) {
        return Integer.toString(number);
    }

    public String toStringDouble(Double number) {
        return Double.toString(number);
    }

    public void search(String oldVal, String newVal, ObservableList<String> clanss, ListView aryes) {
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            aryes.setItems(clanss);
        }
        String value = newVal.toUpperCase();
        ObservableList<String> data = FXCollections.observableArrayList();
        for (Object entry : aryes.getItems()) {
            boolean match = true;
            String entryText = (String) entry;
            if (!entryText.toUpperCase().contains(value)) {
                match = false;
                break;
            }
            if (match) {
                data.add(entryText);
            }
        }
        aryes.setItems(data);
    }

    public String getCurrentAgeFromDOB(Date dob) {
        LocalDate birthdate = dob.toLocalDate();
        LocalDate today = LocalDate.now();                          //Today's date
        Period p = Period.between(birthdate, today);
        return p.getYears() + " year(s) " + p.getMonths() + " month(s)";
    }
    
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
            if (code.isDigitKey()) {
                filter += e.getText();
            }
            if (code.isKeypadKey()) {
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

}
