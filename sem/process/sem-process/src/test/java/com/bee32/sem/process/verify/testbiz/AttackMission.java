package com.bee32.sem.process.verify.testbiz;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.ext.color.Pink;
import com.bee32.sem.process.verify.IAllowedByContext;
import com.bee32.sem.process.verify.util.AllowedBySupport;

@Entity
@Pink
@SequenceGenerator(name = "idgen", sequenceName = "attack_mission_seq", allocationSize = 1)
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
