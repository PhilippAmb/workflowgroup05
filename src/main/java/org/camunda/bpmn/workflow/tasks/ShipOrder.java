package org.camunda.bpmn.workflow.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Date;

/**
 * Created by Chris on 19.04.17.
 */
public class ShipOrder implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {

        Date end = new Date();

        long diff = end.getTime() - ((Date)execution.getVariable("start")).getTime();
        System.out.println("Order duration: " + diff);
        System.out.println("Thank you for your Order, " + execution.getVariable("name"));



    }
}
