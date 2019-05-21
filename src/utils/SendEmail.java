/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.sql.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author kwakuadjei <duncanadjei@gmail.com>
 */

public class SendEmail {
     private static final SendEmail MESSAGE_RESOLVER = new SendEmail();
     mediator md =mediator.md();
    private SendEmail() {
    }

    public static SendEmail emailAPI() {
        return MESSAGE_RESOLVER;
    }
    
    public void sendMessage(String ...content){
     try{
            String host ="smtp.gmail.com" ;
            String user = "agogomrc@gmail.com";
            String pass = "MRC2019Agogo";
            String to = content[3];
            String from = "agogomrc@gmail.com";
            String messageSubject = "SAE Alert  - Mal 094 PID-"+content[1];
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(messageSubject); 
            msg.setText("Hello "+content[0]+",\n\nSubject number "+content[1] + " has new SAE on "+content[2]+". Please follow - up to execute the needed action. \n\nThank you, Duncan");

           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
    
}