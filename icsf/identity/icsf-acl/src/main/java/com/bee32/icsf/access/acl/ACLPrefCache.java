package com.bee32.icsf.access.acl;

import java.util.HashMap;
import java.util.Map;

import javax.free.Nullables;

import com.bee32.plover.orm.cache.DataCacheBean;
import com.bee32.plover.site.scope.PerSite;

@PerSite
public class ACLPrefCache
        extends DataCacheBean {

    Map<Class<?>, Integer> entityAclMap;

    public Map<Class<?>, Integer> getMap() {
        if (entityAclMap == null) {
            synchronized (this) {
                if (entityAclMap == null) {
                    entityAclMap = _load();
                }
            }
        }
        return entityAclMap;
    }

    private Map<Class<?>, Integer> _load() {
        Map<Class<?>, Integer> entityAclMap = new HashMap<Class<?>, Integer>();
        for (ACLPref pref : ctx.data.access(ACLPref.class).list()) {
            Class<?> entityType = pref.getType();
            ACL preferredACL = pref.getPreferredACL();
            entityAclMap.put(entityType, preferredACL.getId());
        }
        return entityAclMap;
    }

    public void invalidate() {
        entityAclMap = null;
    }

    public Integer getPreferredAclId(Class<?> entityType) {
        Map<Class<?>, Integer> map = getMap();
        while (entityType != null) {
            Integer acl = map.get(entityType);
            if (acl != null)
                return acl;
            entityType = entityType.getSuperclass();
        }
        return null;
    }

    public void setPreferredAclId(Class<?> entityType, Integer preferredAclId) {
        getMap().put(entityType, preferredAclId);
    }

    public void updatePreferredAclId(Class<?> entityType, Integer preferredAclId) {
        Integer old = getPreferredAclId(entityType);
        if (Nullables.equals(old, preferredAclId))
            return;

        ACL preferredACL = null;
        if (preferredAclId != null)
            preferredACL = ctx.data.access(ACL.class).lazyLoad(preferredAclId);

        ACLPref newPref = new ACLPref();
        newPref.setType(entityType);
        newPref.setPreferredACL(preferredACL);

        ACLPref existing = ctx.data.access(ACLPref.class).get(newPref.getId());
        if (existing != null) {
            existing.setPreferredACL(preferredACL);
            newPref = existing;
        }
        getMap().put(entityType, preferredAclId);
    }

}
