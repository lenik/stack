package com.bee32.sem.process.verify.web;

import java.util.List;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.web.ChoosePrincipalDialogListener;
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

    public void addResponsible(PrincipalDto principal) {
        SingleVerifierPolicyDto policy = getActiveObject();
        policy.getResponsibles().add(principal);
    }

    public void removeResponsible() {
        SingleVerifierPolicyDto policy = getActiveObject();
        policy.getResponsibles().remove(selectedResponsible);
    }

    public PrincipalDto getSelectedResponsible() {
        return selectedResponsible;
    }

    public void setSelectedResponsible(PrincipalDto selectedResponsible) {
        this.selectedResponsible = selectedResponsible;
    }

    public Object getAddResponsibleAdapter() {
        return new ChoosePrincipalDialogListener() {
            @Override
            protected void selected(List<?> selection) {
                for (Object item : selection)
                    addResponsible((PrincipalDto) item);
            }
        };
    }

}
