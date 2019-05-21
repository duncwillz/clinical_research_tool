/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bloc;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Suppliers;
import utils.mediator;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class SupplyBloc {
    Suppliers suppliers = new Suppliers();
    private static final SupplyBloc SUPPLY_BLOC = new SupplyBloc();
    mediator md = mediator.md();
    private SupplyBloc() {

    }

    public static SupplyBloc suBloc() {
        return SUPPLY_BLOC;
    }
    
    
}
