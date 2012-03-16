package com.bee32.icsf.access.web;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLPref;
import com.bee32.icsf.access.acl.ACLPrefCache;
import com.bee32.icsf.access.acl.ACLPrefDto;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

public class ACLPrefBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ACLPrefBean() {
        super(ACLPref.class, ACLPrefDto.class, 0);
    }

    public void setDescriptorToApply(EntityTypeDescriptor descriptor) {
        ACLPrefDto pref = getOpenedObject();
        pref.setType(descriptor.getEntityType());
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        ACLPrefCache cache = ctx.bean.getBean(ACLPrefCache.class);
        for (ACLPref pref : uMap.<ACLPref> entitySet()) {
            Class<?> entityType = pref.getType();
            ACL preferredACL = pref.getPreferredACL();
            cache.setPreferredAclId(entityType, preferredACL == null ? null : preferredACL.getId());
        }
    }

    @Override
    protected void postDelete(UnmarshalMap uMap)
            throws Exception {
        ACLPrefCache cache = ctx.bean.getBean(ACLPrefCache.class);
        for (ACLPref pref : uMap.<ACLPref> entitySet()) {
            Class<?> entityType = pref.getType();
            cache.setPreferredAclId(entityType, null);
        }
    }

}
