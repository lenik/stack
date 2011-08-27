package com.bee32.icsf.access.alt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.ResourcePermission;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourceRegistry;
import com.bee32.plover.orm.dao.GenericDao;
import com.bee32.plover.ox1.principal.IPrincipal;
import com.bee32.plover.ox1.principal.Principal;

public class R_ACLDao
        extends GenericDao {

    // @Inject
    // R_ACEDao aceDao;

    public R_ACL loadACL(Resource resource) {
        if (resource == null)
            throw new NullPointerException("resource");

        String qualifiedName = ResourceRegistry.qualify(resource);

        R_ACL acl = new R_ACL(null, resource);

        List<R_ACE> list = getHibernateTemplate().findByNamedParam(//
                "from R_ACE where" //
                        + "   ( :q like qualifiedName || '%' )", //
                "q", qualifiedName);

        for (R_ACE ace : list)
            acl.add(ace.getPrincipal(), ace.getPermission());

        return acl;
    }

    public void saveACL(R_ACL acl) {
        if (acl == null)
            throw new NullPointerException("acl");

        HibernateTemplate template = getHibernateTemplate();

        Resource resource = acl.getResource();
        String qualifiedName = ResourceRegistry.qualify(resource);

        List<R_ACE> oldList = template.findByNamedParam(//
                "from R_ACE where" //
                        + "   ( :q like qualifiedName || '%' )", //
                "q", qualifiedName);

        Map<Principal, R_ACE> existingMap = new HashMap<Principal, R_ACE>();
        for (R_ACE ace : oldList)
            existingMap.put(ace.getPrincipal(), ace);

        List<R_ACE> newList = new ArrayList<R_ACE>();
        for (Entry<? extends IPrincipal, Permission> entry : acl.getEntries()) {
            Principal principal = (Principal) entry.getKey();
            R_ACE existed = existingMap.remove(principal);
            if (existed != null) {
                newList.add(existed);
            } else {
                Permission newPermission = entry.getValue();
                R_ACE aceNew = new R_ACE(resource, principal, newPermission);
                newList.add(aceNew);
            }
        }

        template.deleteAll(oldList);
        template.saveOrUpdateAll(newList);
    }

    public List<ResourcePermission> getResourcePermissions(IPrincipal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        List<R_ACE> list = getHibernateTemplate().findByNamedParam(// ,
                "from R_ACE where principal.id = :principalId", //
                "principalId", principal.getId());

        List<ResourcePermission> resources = new ArrayList<ResourcePermission>(list.size());

        for (R_ACE ace : list) {
            Resource resource = ace.getResource();
            Permission permission = ace.getPermission();
            ResourcePermission rp = new ResourcePermission(resource, permission);
            resources.add(rp);
        }

        return resources;
    }

    /**
     * @return <code>null</code> if undefined.
     */
    public Permission getPermission(Resource resource, IPrincipal principal) {
        if (resource == null)
            throw new NullPointerException("resource");
        if (principal == null)
            throw new NullPointerException("principal");

        String qualifiedName = ResourceRegistry.qualify(resource);

        List<R_ACE> list = getHibernateTemplate().findByNamedParam(// ,
                "from R_ACE where" //
                        + "   ( :q like qualifiedName || '%' )" //
                        + "   and principal.id = :principalId", //
                new String[] { "q", "principalId" }, //
                new Object[] { qualifiedName, principal.getId() });

        // Only the first matching entry is returned.
        for (R_ACE ace : list)
            return ace.getPermission();

        // No matching entry.
        return null;
    }

}
