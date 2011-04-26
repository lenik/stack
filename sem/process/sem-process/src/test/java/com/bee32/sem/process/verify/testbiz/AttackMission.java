package com.bee32.sem.process.verify.testbiz;

import javax.persistence.Entity;

import com.bee32.sem.process.verify.IAllowedByContext;
import com.bee32.sem.process.verify.util.AllowedBySupport;

@Entity
public class AttackMission
        extends AllowedBySupport<Integer, IAllowedByContext> {

    private static final long serialVersionUID = 1L;

    private String target;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
