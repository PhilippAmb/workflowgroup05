package org.camunda.bpmn.workflow.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Chris on 19.04.17.
 */
public class CreateDocuments implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {

        String name = (String) execution.getVariable("name");
        int outOfStock = (Integer) execution.getVariable("outOfStock");
        int refillDuration = 0;
        if(outOfStock > 0) {
            refillDuration = (Integer) execution.getVariable("refillDuration");
        }

        String body = "";

        body += "Dear " + name +"!\n";
        if(refillDuration > 0) {
            body += "Your order will be delivered in " + refillDuration + " days.\n";
        }
        
        else {
            body += "Your order will be delivered within the next hours.\n";
        }
        
        long orderNr_p1 = (long) Math.round(Math.random()*10000);
        long orderNr_p2 = (long) Math.round(Math.random()*1000000);
        
        String orderNr = orderNr_p1 + "-" + orderNr_p2;
        
        String subject = "Your Order: " + orderNr;
        
        body += "Your order number is: " + orderNr  + "\n";
        body += "\n\nThank you & good night!";

        // Recipient's email ID needs to be mentioned.
        String to = (String) execution.getVariable("mail");

        // Sender's email ID needs to be mentioned
        String from = "worflow.gr5";
        
        String theword = "***********";

        CreateDocuments.sendFromGMail(from, theword, to, subject, body);
    }

    private static void sendFromGMail(String from, String pass, String to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress();

            // To get the array of addresses

            toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (Exception me) {
            me.printStackTrace();
        }
    }
}
