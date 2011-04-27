package com.bee32.sem.process.verify.typedef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.plover.arch.util.ParameterMap;
import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.ext.typepref.TypePrefDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.web.VerifyPolicyDto;

public class VerifyPolicyPrefDto
        extends TypePrefDto<VerifyPolicyPref> {

    private static final long serialVersionUID = 1L;

    String description;
    VerifyPolicyDto preferredPolicy;
    List<PrincipalDto> responsibles;

    @Override
    protected void _marshal(VerifyPolicyPref source) {
        description = source.getDescription();

        VerifyPolicy<?> _preferredPolicy = source.getPreferredPolicy();
        preferredPolicy = new VerifyPolicyDto().marshal(_preferredPolicy);

        if (_preferredPolicy != null) {
            Collection<? extends Principal> _responsibles = _preferredPolicy.getDeclaredResponsibles(null);
            responsibles = marshalList(PrincipalDto.class, _responsibles);
        } else {
            responsibles = new ArrayList<PrincipalDto>();
        }
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, VerifyPolicyPref target) {

        target.setDescription(description);

        with(context, target) //
                .unmarshal(preferredPolicyProperty, preferredPolicy);
    }

    @Override
    public void _parse(ParameterMap map)
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

    public List<PrincipalDto> getResponsibles() {
        return responsibles;
    }

    static final PropertyAccessor<VerifyPolicyPref, VerifyPolicy<?>> preferredPolicyProperty;
    static {
        preferredPolicyProperty = new PropertyAccessor<VerifyPolicyPref, VerifyPolicy<?>>(VerifyPolicy.class) {

            @Override
            public VerifyPolicy<?> get(VerifyPolicyPref entity) {
                return entity.getPreferredPolicy();
            }

            @Override
            public void set(VerifyPolicyPref entity, VerifyPolicy<?> preferredPolicy) {
                entity.setPreferredPolicy(preferredPolicy);
            }

        };
    }

}
