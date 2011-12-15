package com.bee32.sem.process.verify.testbiz;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.Pink;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.process.verify.ISingleVerifier;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.util.SingleVerifierSupport;

@Entity
@Pink
@SequenceGenerator(name = "idgen", sequenceName = "attack_mission_seq", allocationSize = 1)
public class AttackMission
        extends UIEntityAuto<Integer>
        implements IVerifiable<ISingleVerifier> {

    private static final long serialVersionUID = 1L;

    private String target;
    private SingleVerifierSupport<ISingleVerifier> singleVerifierSupport;

    public AttackMission() {
        singleVerifierSupport = new SingleVerifierSupport<ISingleVerifier>(this);
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public ISingleVerifier getVerifyContext() {
        return singleVerifierSupport;
    }

}
