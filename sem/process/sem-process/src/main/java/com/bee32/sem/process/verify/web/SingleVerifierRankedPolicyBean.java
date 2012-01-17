package com.bee32.sem.process.verify.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierRankedPolicy;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierLevelDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierRankedPolicyDto;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

@ForEntity(SingleVerifierPolicy.class)
public class SingleVerifierRankedPolicyBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public SingleVerifierRankedPolicyBean() {
        super(SingleVerifierRankedPolicy.class, SingleVerifierRankedPolicyDto.class, 0);
    }

    // For editDialog:form

    SingleVerifierLevelDto selectedLevel;

    public SingleVerifierLevelDto getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(SingleVerifierLevelDto selectedLevel) {
        this.selectedLevel = selectedLevel;
    }

    public void removeLevel() {
        SingleVerifierRankedPolicyDto policy = getActiveObject();
        policy.getLevels().remove(selectedLevel);
    }

    public void newLevel() {
        level = new SingleVerifierLevelDto();
    }

    // For editLevelForm

    SingleVerifierLevelDto level;
    VerifyPolicyDto selectedTargetPolicy;

    public SingleVerifierLevelDto getLevel() {
        return level;
    }

    public VerifyPolicyDto getSelectedTargetPolicy() {
        return selectedTargetPolicy;
    }

    public void setSelectedTargetPolicy(VerifyPolicyDto selectedTargetPolicy) {
        this.selectedTargetPolicy = selectedTargetPolicy;
    }

    public void confirmTargetPolicy() {
        level.setTargetPolicy(selectedTargetPolicy);
    }

    public void addLevel() {
        SingleVerifierRankedPolicyDto policy = getActiveObject();
        level.setPolicy(policy);
        policy.getLevels().add(level);
    }

}
