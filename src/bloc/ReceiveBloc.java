/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloc;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Receive;
import utils.mediator;

/**
 *
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class ReceiveBloc {

    private static final ReceiveBloc RECEIVE_BLOC = new ReceiveBloc();
    mediator md = mediator.md();
    Receive ri = new Receive();
    private ReceiveBloc() {

    }

    public static ReceiveBloc riBloc() {
        return RECEIVE_BLOC;
    }
    
    
    
    public Boolean isvalidatedFields(DatePicker  date, TextField ...textField){
        if(date.getValue()==null||textField[0].getText().isEmpty()||textField[1].getText().isEmpty()||textField[2].getText().isEmpty()||textField[3].getText().isEmpty()){
            md.note("Error", "All fields are required, description is optional");
            return false;
        }
        return true;
    }
    
    public void onSupplyAction(DatePicker  date, int rrid, Button but, TextField ...textField){
        if (isvalidatedFields(date, textField)){
            ri.setRisupplier(textField[0].getText());
            ri.setRiexpiry(md.convert(date.getValue()));
            ri.setRiinvoice(Integer.parseInt(textField[1].getText()));
            ri.setRinumb(Integer.parseInt(textField[2].getText()));
            ri.setRiitem(md.getItem().getIid());
            ri.setRiuser(md.getDbuser().getUid());
            if(!textField[3].getText().isEmpty()){
            ri.setRidescription(textField[3].getText());
            }else{
            ri.setRidescription(null);
            }
            if (but.getText().equals("Receive In")){
                
                md.note("Success", "Drug received");
            }else if(but.getText().equals("Update receive")){
                md.note("Updated", "Drug receive updated");
            }
        }
    }
    
    
    public void onClearAction(DatePicker date, Button bt, TextField ...fields){
       date.setValue(null);
       bt.setText("Receive in");
        for (TextField field : fields) {
            field.setText(""); 
        }
    }
    
    
    public void populateTables(){
    
    
    
    }
    
    
}
