package com.bee32.sem.process.verify.web;

import java.util.List;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierPolicyDto;

@ForEntity(SingleVerifierPolicy.class)
public class SingleVerifierPolicyBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    SingleVerifierPolicyDto policy = new SingleVerifierPolicyDto().create();

    public SingleVerifierPolicyBean() {
        super(SingleVerifierPolicy.class, SingleVerifierPolicyDto.class, 0);
    }

    public SingleVerifierPolicyDto getPolicy() {
        return policy;
    }

    public void setPolicy(SingleVerifierPolicyDto policy) {
        this.policy = policy;
    }

    public List<PrincipalDto> getResponsibles() {
        if (policy != null && policy.getId() != null) {
            return policy.getResponsibles();
        }

        return null;
    }

    public void newPolicy() {
        policy = new SingleVerifierPolicyDto().create();
    }

    public void savePolicy() {

    }

}
