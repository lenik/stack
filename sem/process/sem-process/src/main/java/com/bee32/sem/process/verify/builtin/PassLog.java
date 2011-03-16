package com.bee32.sem.process.verify.builtin;

import java.util.ArrayList;
import java.util.List;

import com.bee32.icsf.principal.User;
import com.bee32.sem.process.verify.VerifyState;

public class PassLog
        extends VerifyState {

    private static final long serialVersionUID = 1L;

    private List<AllowState> stepStates;

    public PassLog() {
        this.stepStates = new ArrayList<AllowState>();
    }

    public PassLog(List<AllowState> stepStates) {
        if (stepStates == null)
            throw new NullPointerException("stepStates");
        this.stepStates = stepStates;
    }

    public void passBy(User user) {
        if (user == null)
            throw new NullPointerException("user");
        stepStates.add(new AllowState(true, user));
    }

    public void rejectBy(User user) {
        if (user == null)
            throw new NullPointerException("user");
        stepStates.add(new AllowState(false, user));
    }

    public int size() {
        return stepStates.size();
    }

    public void clear() {
        stepStates.clear();
    }

    public AllowState get(int index) {
        return stepStates.get(index);
    }

    @Override
    public String getStateMessage() {
        StringBuilder sb = new StringBuilder();

        int index = 0;
        for (AllowState stepState : stepStates) {
            if (index++ != 0)
                sb.append(", ");

            sb.append("审核" + index + " ");
            sb.append(stepState);
            sb.append("\n");
        }

        return sb.toString();
    }

}
