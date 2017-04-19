package wfApp.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by Chris on 19.04.17.
 */
public class ShipOrder implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {

        Date end = new Date();

        int refillNecessary = (Integer) execution.getVariable("outOfStock");

        long diff = end.getTime() - ((Date)execution.getVariable("start")).getTime();
        int amount = (Integer) execution.getVariable("amount");
        int price = (Integer) execution.getVariable("price");
        System.out.println("Order duration: " + diff + " ms");
        System.out.println("Thank you for your Order, " + execution.getVariable("name"));


        File f = new File("log.csv");
        PrintWriter pw = null;
        if(f.exists() && !f.isDirectory()) {
            pw = new PrintWriter(new FileWriter(f.getAbsoluteFile(), true));
        }
        else {
            pw = new PrintWriter(new File("log.csv"));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(diff);
        sb.append(',');
        sb.append(refillNecessary);
        sb.append(',');
        sb.append(amount);
        sb.append(',');
        sb.append(price);
        sb.append('\n');

        pw.write(sb.toString());
        pw.close();



    }
}
