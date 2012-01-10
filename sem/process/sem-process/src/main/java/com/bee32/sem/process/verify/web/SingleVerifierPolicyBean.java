package com.bee32.sem.process.verify.web;

import java.util.List;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierPolicyDto;

@ForEntity(SingleVerifierPolicy.class)
public class SingleVerifierPolicyBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    SingleVerifierPolicyDto policy = new SingleVerifierPolicyDto().create();

    String responsiblePattern;
    List<PrincipalDto> responsibles;
    PrincipalDto selectedResponsible;

    public SingleVerifierPolicyBean() {
        super(SingleVerifierPolicy.class, SingleVerifierPolicyDto.class, 0);
    }

    public SingleVerifierPolicyDto getPolicy() {
        return policy;
    }

    public void setPolicy(SingleVerifierPolicyDto policy) {
        this.policy = policy;
    }


    public void newPolicy() {
        policy = new SingleVerifierPolicyDto().create();
    }

    public void reloadPolicy() {
        policy = reload(policy);
    }

    public void savePolicy() {
        try {
            SingleVerifierPolicy _policy = (SingleVerifierPolicy) policy.unmarshal();
            serviceFor(SingleVerifierPolicy.class).saveOrUpdate(_policy);

            uiLogger.info("保存成功.");
        } catch (Exception e) {
            uiLogger.error("保存错误!", e);
        }
    }

    public void deletePolicy() {
        try {
            serviceFor(SingleVerifierPolicy.class).deleteById(policy.getId());

            uiLogger.info("删除成功.");
        } catch (Exception e) {
            uiLogger.error("删除错误!", e);
        }
    }


    public List<PrincipalDto> getAllowList() {
        if (policy != null && policy.getId() != null) {
            policy = reload(policy, SingleVerifierPolicyDto.RESPONSIBLES);
            return policy.getResponsibles();
        }

        return null;
    }


    public String getResponsiblePattern() {
        return responsiblePattern;
    }

    public void setResponsiblePattern(String responsiblePattern) {
        this.responsiblePattern = responsiblePattern;
    }

    public PrincipalDto getSelectedResponsible() {
        return selectedResponsible;
    }

    public void setSelectedResponsible(PrincipalDto selectedResponsible) {
        this.selectedResponsible = selectedResponsible;
    }

    public void setResponsibles(List<PrincipalDto> responsibles) {
        this.responsibles = responsibles;
    }

    public List<PrincipalDto> getResponsibles() {
        return responsibles;
    }

    public void findResponsible() {
        if (responsiblePattern != null && !responsiblePattern.isEmpty()) {

            List<Principal> _principals = serviceFor(Principal.class).list(
                    new Like("name", "%" + responsiblePattern + "%"));

            responsibles = DTOs.mrefList(PrincipalDto.class, _principals);
        }
    }

    public void addResponsible() {
        try {
            SingleVerifierPolicy _policy = (SingleVerifierPolicy) policy.unmarshal();
            Principal _p = selectedResponsible.unmarshal();
            _policy.addResponsible(_p);
            serviceFor(SingleVerifierPolicy.class).saveOrUpdate(_policy);

            uiLogger.info("保存成功.");
        } catch (Exception e) {
            uiLogger.error("保存错误!", e);
        }
    }

    public void deleteResponsible() {
        try {
            SingleVerifierPolicy _policy = (SingleVerifierPolicy) policy.unmarshal();
            Principal _p = selectedResponsible.unmarshal();
            _policy.removeResponsible(_p);
            serviceFor(SingleVerifierPolicy.class).saveOrUpdate(_policy);

            uiLogger.info("删除成功.");
        } catch (Exception e) {
            uiLogger.error("删除错误!", e);
        }
    }
}
