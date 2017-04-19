package org.camunda.bpmn.workflow.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Date;

/**
 * Created by Chris on 19.04.17.
 */
public class ProcessOrder implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {

        String product;
        String name;
        Integer amount;
        String email;
        Date start = new Date();
        try {
            product = (String) execution.getVariable("product");
            name = (String) execution.getVariable("name");
            email = (String) execution.getVariable("mail");
            amount = (Integer) execution.getVariable("amount");
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error while processing the user input. Please check your input.");
        }

        if (amount == null || amount < 1 || amount > 1000) {
            throw new IllegalArgumentException("The amount has to be between 1 and 1000");
        }

        product = product.trim();
        email = email.trim();

        execution.setVariable("product", product);
        execution.setVariable("name", name);
        execution.setVariable("amount", amount);
        execution.setVariable("mail", email);
        execution.setVariable("start", start);
    }
}
