package com.bee32.icsf.access.alt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.authority.Authority;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourceRegistry;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.HibernateTemplate;

@Lazy
@Service
public class R_Authority
        extends Authority {

    @Inject
    CommonDataManager dataManager;

    @Inject
    R_ACEDao aceDao;

    @Inject
    HibernateTemplate hibernateTemplate;

    @Transactional(readOnly = true)
    @Override
    public R_ACL getACL(Resource resource) {
        if (resource == null)
            throw new NullPointerException("resource");

        String resourceName = ResourceRegistry.toName(resource);

        R_ACL acl = new R_ACL(null, resource);

        List<R_ACE> list = hibernateTemplate.findByNamedParam(//
                "from ace where resourceName = :resourceName", //
                "resourceName", resourceName);

        for (R_ACE ace : list)
            acl.add(ace.getPrincipal(), ace.getPermission());

        return acl;
    }

    @Transactional(readOnly = true)
    @Override
    public Permission getPermission(Resource resource, IPrincipal principal) {
        if (resource == null)
            throw new NullPointerException("resource");
        if (principal == null)
            throw new NullPointerException("principal");

        String resourceName = ResourceRegistry.toName(resource);

        Long principalId = principal.getId();
        List<R_ACE> list = hibernateTemplate.findByNamedParam(// ,
                "from ace where resourceName = :resourceName and principal = :principalId", //
                new String[] { "resourceName", "principalId" }, //
                new Object[] { resourceName, principalId });

        // Only the first matching entry is returned.
        for (R_ACE ace : list)
            return ace.getPermission();

        // No matching entry.
        return null;
    }

    @Transactional
    public void saveACL(R_ACL acl) {
        if (acl == null)
            throw new NullPointerException("acl");

        Resource resource = acl.getResource();
        String resourceName = ResourceRegistry.toName(resource);

        List<R_ACE> existing = hibernateTemplate.findByNamedParam(//
                "from ace where resourceName = :resourceName", //
                "resourceName", resourceName);

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

        hibernateTemplate.deleteAll(existing);
        hibernateTemplate.saveOrUpdateAll(reorg);
    }

}
