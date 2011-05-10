package com.bee32.sem.process.verify.typedef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.typePref.TypePrefDto;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.dto.AbstractVerifyPolicyDto;

public class VerifyPolicyPrefDto
        extends TypePrefDto<VerifyPolicyPref> {

    private static final long serialVersionUID = 1L;

    String description;
    AbstractVerifyPolicyDto preferredPolicy;
    List<PrincipalDto> responsibles;

    @Override
    protected void _marshal(VerifyPolicyPref source) {
        description = source.getDescription();

        VerifyPolicy<?> _preferredPolicy = source.getPreferredPolicy();
        preferredPolicy = new AbstractVerifyPolicyDto().marshal(_preferredPolicy);

        if (_preferredPolicy != null) {
            Collection<? extends Principal> _responsibles = _preferredPolicy.getDeclaredResponsibles(null);
            responsibles = marshalList(PrincipalDto.class, _responsibles);
        } else {
            responsibles = new ArrayList<PrincipalDto>();
        }
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
        preferredPolicy = new AbstractVerifyPolicyDto().ref(policyId);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AbstractVerifyPolicyDto getPreferredPolicy() {
        return preferredPolicy;
    }

    public void setPreferredPolicy(AbstractVerifyPolicyDto preferredPolicy) {
        this.preferredPolicy = preferredPolicy;
    }

    public List<PrincipalDto> getResponsibles() {
        return responsibles;
    }

}
