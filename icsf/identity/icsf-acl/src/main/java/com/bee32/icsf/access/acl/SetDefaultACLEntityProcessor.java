package com.bee32.icsf.access.acl;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;

import com.bee32.plover.orm.access.AbstractEntityProcessor;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.c.CEntityAccessor;

public class SetDefaultACLEntityProcessor
        extends AbstractEntityProcessor
        implements SaveOrUpdateEventListener {

    private static final long serialVersionUID = 1L;

    @Override
    public Collection<String> getInterestingEvents() {
        return listOf("save-update");
    }

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event)
            throws HibernateException {
        Object obj = event.getObject();
        if (obj instanceof CEntity<?>) {
            CEntity<?> c = (CEntity<?>) obj;
            injectACL(c);
        }
    }

    void injectACL(CEntity<?> entity) {
        Integer aclId = entity.getAclId();
        if (aclId == null) {
            ACLPrefCache cache = ctx.bean.getBean(ACLPrefCache.class);
            Integer preferredAclId = cache.getPreferredAclId(entity.getClass());
            if (preferredAclId != null)
                CEntityAccessor.setAclId(entity, preferredAclId);
        }
    }

}
