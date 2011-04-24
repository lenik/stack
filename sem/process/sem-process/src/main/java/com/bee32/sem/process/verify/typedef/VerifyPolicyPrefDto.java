package com.bee32.sem.process.verify.typedef;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.builtin.web.VerifyPolicyDto;
import com.bee32.sem.process.verify.util.AllowedBySupportDto;

public class VerifyPolicyPrefDto
        extends AllowedBySupportDto<VerifyPolicyPref, String> {

    private static final long serialVersionUID = 1L;

    String entityType;
    String displayName;
    String description;
    VerifyPolicyDto preferredPolicy;

    @Override
    protected void _marshal(VerifyPolicyPref source) {
        entityType = source.getEntityType();
        displayName = source.getDisplayName();
        description = source.getDescription();
        preferredPolicy = new VerifyPolicyDto().marshal(source.getPreferredPolicy());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, VerifyPolicyPref target) {
        target.setEntityType(entityType);
        target.setDisplayName(displayName);
        target.setDescription(description);
        target.setPreferredPolicy(unmarshal(preferredPolicy));
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        entityType = map.getString("entityType");
        displayName = map.getString("displayName");
        description = map.getString("description");

        // XXX
    }

}
