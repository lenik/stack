package com.bee32.sem.process.verify.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierRankedPolicy;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierLevelDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierRankedPolicyDto;

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
        SingleVerifierRankedPolicyDto policy = getOpenedObject();
        policy.getLevels().remove(selectedLevel);
    }

    public void newLevel() {
        level = new SingleVerifierLevelDto();
    }

    // For editLevelForm

    SingleVerifierLevelDto level;

    public SingleVerifierLevelDto getLevel() {
        return level;
    }

    public void addLevel() {
        SingleVerifierRankedPolicyDto policy = getOpenedObject();
        level.setPolicy(policy);
        policy.getLevels().add(level);
    }

}
