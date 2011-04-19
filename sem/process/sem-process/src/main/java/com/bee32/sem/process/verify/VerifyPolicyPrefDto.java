package com.bee32.sem.process.verify;

import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.builtin.web.VerifyPolicyDto;

public class VerifyPolicyPrefDto
        extends EntityDto<VerifyPolicyPref, String> {

    private static final long serialVersionUID = 1L;

    VerifyPolicyDto policy;

    @Override
    protected void _marshal(VerifyPolicyPref source) {
        policy = new VerifyPolicyDto().marshal(source.getPolicy());
    }

    @Override
    protected void _unmarshalTo(VerifyPolicyPref target) {
        target.setPolicy(unmarshal(policy));
    }

}
