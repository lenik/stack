package com.bee32.icsf.access.acl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class ACLService
        extends DataService
        implements IACLService {

    /**
     * 查找蕴涵给定 principal -> permission 的 ACL 列表。
     *
     * @param principal
     *            有效主体。返回的 ACL 中相关 ACE 必须蕴涵该主体。
     * @param permission
     *            所要求的权限位。返回的 ACL 中相关的 ACE 必须蕴涵该 permission。
     * @return 返回有效 ACL 的 id 列表。
     */
    @Override
    public Set<Integer> getACLs(Principal principal, Permission permission) {
        ICriteriaElement criteria = ACLCriteria.implies(principal, permission);
        List<ACL> acls = ctx.data.access(ACL.class).list(criteria);

        Set<Integer> aclIds = new HashSet<Integer>();
        aclIds.add(0); // 0 is always shown.

        for (ACL acl : acls) {
            aclIds.add(acl.getId());
        }

        return aclIds;
    }

}
