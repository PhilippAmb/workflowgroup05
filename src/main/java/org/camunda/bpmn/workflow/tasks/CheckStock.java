package wfApp.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Created by Chris on 19.04.17.
 */
public class CheckStock implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {

        Integer stock = 900 + (int)Math.round(100*Math.random());
        Integer requestedAmount = (Integer) execution.getVariable("amount");
        int outOfStock = 0;
        if(requestedAmount > stock) {
            outOfStock = 1;
        }

        execution.setVariable("outOfStock", outOfStock);

    }
}
