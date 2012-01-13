package com.bee32.sem.process.verify.preference;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.typePref.TypePrefDto;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class VerifyPolicyPrefDto
        extends TypePrefDto<VerifyPolicyPref> {

    private static final long serialVersionUID = 1L;

    String description;
    VerifyPolicyDto preferredPolicy;
    VerifyPolicyDto lastPreferredPolicy;

    @Override
    protected void _marshal(VerifyPolicyPref source) {
        description = source.getDescription();

        VerifyPolicy _preferredPolicy = source.getPreferredPolicy();
        preferredPolicy = mref(VerifyPolicyDto.class, _preferredPolicy);

        lastPreferredPolicy = new VerifyPolicyDto().ref(preferredPolicy);
    }

    @Override
    protected void _unmarshalTo(VerifyPolicyPref target) {
        target.setDescription(description);
        merge(target, "preferredPolicy", preferredPolicy);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        description = map.getString("description");

        String _policyId = map.getString("preferredPolicyId");
        int policyId = Integer.parseInt(_policyId);
        preferredPolicy = new VerifyPolicyDto().ref(policyId);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VerifyPolicyDto getPreferredPolicy() {
        return preferredPolicy;
    }

    public void setPreferredPolicy(VerifyPolicyDto preferredPolicy) {
        this.preferredPolicy = preferredPolicy;
    }

    public boolean isPreferredPolicyChanged() {
        Object id = preferredPolicy.getId();
        Object lastId = lastPreferredPolicy.getId();
        return !id.equals(lastId);
    }

}
