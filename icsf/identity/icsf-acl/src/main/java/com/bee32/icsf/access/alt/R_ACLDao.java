package com.bee32.icsf.access.alt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.ResourcePermission;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourceRegistry;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.dao.GenericDao;

@Lazy
public class R_ACLDao
        extends GenericDao {

    // @Inject
    // R_ACEDao aceDao;

    public R_ACL loadACL(Resource resource) {
        if (resource == null)
            throw new NullPointerException("resource");

        String resourceName = ResourceRegistry.qualify(resource);

        R_ACL acl = new R_ACL(null, resource);

        List<R_ACE> list = getHibernateTemplate().findByNamedParam(//
                "from R_ACE where" //
                        + "   (:q = resourceName or :q like resourceName || '.%')", //
                "q", resourceName);

        for (R_ACE ace : list)
            acl.add(ace.getPrincipal(), ace.getPermission());

        return acl;
    }

    public void saveACL(R_ACL acl) {
        if (acl == null)
            throw new NullPointerException("acl");

        HibernateTemplate template = getHibernateTemplate();

        Resource resource = acl.getResource();
        String resourceName = ResourceRegistry.qualify(resource);

        List<R_ACE> existing = template.findByNamedParam(//
                "from R_ACE where" //
                        + "   (:q = resourceName or :q like resourceName || '.%')", //
                "q", resourceName);

        Map<IPrincipal, R_ACE> existingMap = new HashMap<IPrincipal, R_ACE>();
        for (R_ACE ace : existing)
            existingMap.put(ace.getPrincipal(), ace);

        List<R_ACE> reorg = new ArrayList<R_ACE>();
        for (Entry<? extends IPrincipal, Permission> entry : acl.getEntries()) {
            Principal principal = (Principal) entry.getKey();
            R_ACE existed = existingMap.remove(principal);
            if (existed != null) {
                reorg.add(existed);
            } else {
                Permission newPermission = entry.getValue();
                R_ACE newAce = new R_ACE(resource, principal, newPermission);
                reorg.add(newAce);
            }
        }

        template.deleteAll(existing);
        template.saveOrUpdateAll(reorg);
    }

    public List<ResourcePermission> getResourcePermissions(IPrincipal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        Long principalId = principal.getId();
        List<R_ACE> list = getHibernateTemplate().findByNamedParam(// ,
                "from R_ACE where principal = :principalId", //
                "principalId", principalId);

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

        String resourceName = ResourceRegistry.qualify(resource);

        Long principalId = principal.getId();
        List<R_ACE> list = getHibernateTemplate().findByNamedParam(// ,
                "from R_ACE where" //
                        + "   (:q = resourceName or :q like resourceName || '.%')" //
                        + "   and principal = :principalId", //
                new String[] { "q", "principalId" }, //
                new Object[] { resourceName, principalId });

        // Only the first matching entry is returned.
        for (R_ACE ace : list)
            return ace.getPermission();

        // No matching entry.
        return null;
    }

}
