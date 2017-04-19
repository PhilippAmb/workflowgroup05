package wfApp.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Created by Chris on 19.04.17.
 */
public class RefillStock implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {

        int refillDuration = (int)Math.round(Math.random()*2) +1;
        execution.setVariable("refillDuration", refillDuration);
    }
}
