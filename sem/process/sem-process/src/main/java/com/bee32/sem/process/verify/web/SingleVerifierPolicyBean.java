package com.bee32.sem.process.verify.web;

import java.util.List;

import com.bee32.icsf.principal.Principal;
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
