/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bloc;

import utils.mediator;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class WithdrawalBloc {
  WithdrawalBloc withdrawal = new WithdrawalBloc();
    private static final WithdrawalBloc EXPOSE_BLOC = new WithdrawalBloc();
    mediator md = mediator.md();
    private WithdrawalBloc() {

    }

    public static WithdrawalBloc wdBloc() {
        return EXPOSE_BLOC;
    }
    
}
