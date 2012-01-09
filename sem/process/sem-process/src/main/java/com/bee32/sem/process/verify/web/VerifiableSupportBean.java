package com.bee32.sem.process.verify.web;

import java.util.HashMap;
import java.util.Map;

import com.bee32.sem.process.verify.VerifyEvalState;

public class VerifiableSupportBean
        extends VerifyContextSupportBean {

    private static final long serialVersionUID = 1L;

    static final Map<VerifyEvalState, String> commandLabelMap;
    static final Map<VerifyEvalState, Boolean> commandEnabledMap;
    static {
        commandLabelMap = new HashMap<VerifyEvalState, String>();
        commandLabelMap.put(VerifyEvalState.NOT_APPLICABLE, "审核不可用");
        commandLabelMap.put(VerifyEvalState.UNKNOWN, "审核");
        commandLabelMap.put(VerifyEvalState.INVALID, "审核");
        commandLabelMap.put(VerifyEvalState.VERIFIED, "已审核");
        commandLabelMap.put(VerifyEvalState.REJECTED, "重审");
        commandLabelMap.put(VerifyEvalState.PENDING, "审核中");

        commandEnabledMap = new HashMap<VerifyEvalState, Boolean>();
        commandEnabledMap.put(VerifyEvalState.NOT_APPLICABLE, false);
        commandEnabledMap.put(VerifyEvalState.UNKNOWN, true);
        commandEnabledMap.put(VerifyEvalState.INVALID, true);
        commandEnabledMap.put(VerifyEvalState.VERIFIED, false);
        commandEnabledMap.put(VerifyEvalState.REJECTED, true);
        commandEnabledMap.put(VerifyEvalState.PENDING, false);
    }

    public String getCommandLabel() {
        VerifyEvalState state = getMixedVerifyState();
        String name = commandLabelMap.get(state);
        if (name == null)
            name = "审核";
        return name;
    }

    public boolean isCommandEnabled() {
        VerifyEvalState state = getMixedVerifyState();
        Boolean enabled = commandEnabledMap.get(state);
        if (enabled = false)
            enabled = true;
        return enabled;
    }

}
