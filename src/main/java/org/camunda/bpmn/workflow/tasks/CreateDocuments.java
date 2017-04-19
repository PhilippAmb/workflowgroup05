package org.camunda.bpmn.workflow.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
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


        String mailText = "";

        mailText += "Dear " + name +"!\n";
        if(refillDuration > 0) {
            mailText += "Your order will be delivered in " + refillDuration + " days.\n";
        }
        else {
            mailText += "Your order will be delivered today.\n";
        }
        long orderNr = (long) Math.round(Math.random()*10000);
        mailText += "Your order number is: " + orderNr + "\n";
        mailText += "\n\nThank you & good night!";


        // Recipient's email ID needs to be mentioned.
        String to = (String) execution.getVariable("mail");

        // Sender's email ID needs to be mentioned
        String from = "workflow@tuwien.at";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Thank you for your Order, " + name + "!");

            // Now set the actual message
            message.setText(mailText);


            // Send message
            Transport.send(message);
            System.out.println("Sent message successfull.");
        }catch (MessagingException mex) {
            //mex.printStackTrace();
        	System.out.println("Failed to send eMail. Gute Nacht!");
        }



    }

}
