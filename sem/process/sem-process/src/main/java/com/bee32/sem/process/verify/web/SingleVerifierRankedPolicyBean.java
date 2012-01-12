package com.bee32.sem.process.verify.web;

import java.util.List;

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

    SingleVerifierLevelDto level;

    public SingleVerifierRankedPolicyBean() {
        super(SingleVerifierRankedPolicy.class, SingleVerifierRankedPolicyDto.class, 0);
    }

    public Object getChooseVerifyPolicyDialogListener() {
        return new ChooseVerifyPolicyDialogListener() {
            @Override
            protected void select(List<?> selection) {
                for (Object item : selection)
                    setVerifyPolicy((VerifyPolicyDto) item);
            }
        };
    }

    public void setVerifyPolicy(VerifyPolicyDto policy) {
        level.setTargetPolicy(policy);
    }

    public SingleVerifierLevelDto getLevel() {
        return level;
    }

    public void setLevel(SingleVerifierLevelDto level) {
        this.level = level;
    }

    public void addLevel() {
        SingleVerifierRankedPolicyDto policy = getActiveObject();
        level.setPolicy(policy);
        policy.getLevels().add(level);
    }

    public void removeLevel() {
        SingleVerifierRankedPolicyDto policy = getActiveObject();
        policy.getLevels().remove(level);
    }

    public void newLevel() {
        level = new SingleVerifierLevelDto().create();
    }

}
