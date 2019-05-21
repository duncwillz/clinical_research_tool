/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class SendSMS {
    
    private static final SendSMS MESSAGE_RESOLVER = new SendSMS();

    private SendSMS() {
    }

    public static SendSMS smsAPI() {
        return MESSAGE_RESOLVER;
    }  
  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "AC1e494c33a2bea07fb1fa179311da1a31";
  public static final String AUTH_TOKEN = "59e7a56f21e1d555f01c8429dcb63e28";

  public void sendSMS(String ...content){
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    Message message = Message.creator(new PhoneNumber(content[3]),
        new PhoneNumber("+15005550006"), 
        "Hello "+content[0]+",\nNEW SAE: Subject number "+content[1] + " has new SAE on "+content[2]+". Please follow - up to execute the needed action. \nThank you, Duncan").create();
    System.out.println(message.getSid());
  }
  
}
