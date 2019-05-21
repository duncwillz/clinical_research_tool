/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import infobip.api.client.SendSingleTextualSms;
import infobip.api.config.BasicAuthConfiguration;
import infobip.api.model.sms.mt.send.SMSResponse;
import infobip.api.model.sms.mt.send.SMSResponseDetails;
import infobip.api.model.sms.mt.send.textual.SMSTextualRequest;
import java.util.Arrays;

/**
 *
 * @author kwakuadjei <duncanadjei@gmail.com>
 */
public class SendSMSInfobip {

    private static final String USERNAME = "kwakuadjei";
    private static final String PASSWORD = "idunnomrhacker";
    private static final SendSMSInfobip MESSAGE_RESOLVER = new SendSMSInfobip();

    private SendSMSInfobip() {
    }

    public static SendSMSInfobip smsAPI() {
        return MESSAGE_RESOLVER;
    }

    public void sendSMS(String... content) {

        SendSingleTextualSms client = new SendSingleTextualSms(new BasicAuthConfiguration(USERNAME, PASSWORD));

        SMSTextualRequest requestBody = new SMSTextualRequest();
        requestBody.setFrom("MRC Stores");
        requestBody.setTo(Arrays.asList(content));
        requestBody.setText(content[0]);

        SMSResponse response = client.execute(requestBody);

        for (SMSResponseDetails sentMessageInfo : response.getMessages()) {
            System.out.println("Message ID: " + sentMessageInfo.getMessageId());
            System.out.println("Receiver: " + sentMessageInfo.getTo());
            System.out.println("Message status: " + sentMessageInfo.getStatus().getName());
            System.out.println("------------------------------------------------");
        }
    }

}
