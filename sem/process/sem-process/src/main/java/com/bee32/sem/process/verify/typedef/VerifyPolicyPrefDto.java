package com.bee32.sem.process.verify.typedef;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.orm.ext.typepref.TypePrefDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.builtin.web.VerifyPolicyDto;

public class VerifyPolicyPrefDto
        extends TypePrefDto<VerifyPolicyPref> {

    private static final long serialVersionUID = 1L;

    String description;
    VerifyPolicyDto preferredPolicy;

    @Override
    protected void _marshal(VerifyPolicyPref source) {
        description = source.getDescription();
        preferredPolicy = new VerifyPolicyDto().marshal(source.getPreferredPolicy());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, VerifyPolicyPref target) {
        target.setDescription(description);
        target.setPreferredPolicy(unmarshal(preferredPolicy));
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        description = map.getString("description");

        preferredPolicy = new VerifyPolicyDto();
        preferredPolicy.setReferenceOnly(true);
        preferredPolicy.setId(map.getInt("preferredPolicyId"));
    }

}
