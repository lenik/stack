package com.bee32.icsf.access.acl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class ACLService
        extends DataService {

    /**
     * 查找蕴涵给定 principal -> permission 的 ACL 列表。
     *
     * @param principal
     *            有效主体。返回的 ACL 中相关 ACE 必须蕴涵该主体。
     * @param permission
     *            所要求的权限位。返回的 ACL 中相关的 ACE 必须蕴涵该 permission。
     * @return 返回有效 ACL 的 id 列表。
     */
    public Integer[] aclIndex(Principal principal, Permission permission) {

        ICriteriaElement criterion = ACLCriteria.impliesEntry(principal, permission);

        List<ACLEntry> entries = asFor(ACLEntry.class).list(criterion);

        Set<Integer> set = new HashSet<Integer>();
        set.add(0); // 0 is always shown.

        for (ACLEntry entry : entries) {
            ACL acl = entry.getACL();
            set.add(acl.getId());
        }

        Integer[] array = set.toArray(new Integer[0]);
        return array;
    }

}
