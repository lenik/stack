package com.bee32.sem.process.verify.web;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierPolicyDto;

@ForEntity(SingleVerifierPolicy.class)
public class SingleVerifierPolicyBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    PrincipalDto selectedResponsible;

    public SingleVerifierPolicyBean() {
        super(SingleVerifierPolicy.class, SingleVerifierPolicyDto.class, 0);
    }

    public PrincipalDto getSelectedResponsible() {
        if (selectedResponsible == null)
            selectedResponsible = new PrincipalDto();
        return selectedResponsible;
    }

    public void setSelectedResponsible(PrincipalDto selectedResponsible) {
        this.selectedResponsible = selectedResponsible;
    }

    public void addResponsible() {
        SingleVerifierPolicyDto policy = getOpenedObject();
        policy.getResponsibles().add(selectedResponsible);
    }

    public void removeResponsible() {
        SingleVerifierPolicyDto policy = getOpenedObject();
        policy.getResponsibles().remove(selectedResponsible);
        selectedResponsible = null;
    }

}
