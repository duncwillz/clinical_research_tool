/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bloc;
import model.Supplier;
import utils.mediator;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class SupplyBloc {
    Supplier suppliers = new Supplier();
    private static final SupplyBloc SUPPLY_BLOC = new SupplyBloc();
    mediator md = mediator.md();
    private SupplyBloc() {

    }

    public static SupplyBloc suBloc() {
        return SUPPLY_BLOC;
    }
    
    
}
