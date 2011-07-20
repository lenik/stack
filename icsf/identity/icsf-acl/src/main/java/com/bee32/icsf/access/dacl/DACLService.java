package com.bee32.icsf.access.dacl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.EnterpriseService;
import com.bee32.plover.criteria.hibernate.CriteriaElement;

public class DACLService
        extends EnterpriseService {

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

        CriteriaElement criterion = DACLCriteria.impliesDACE(principal, permission);

        List<DACE> daceList = asFor(DACE.class).list(criterion);

        Set<Integer> set = new HashSet<Integer>();
        set.add(0); // 0 is always shown.

        for (DACE dace : daceList) {
            DACL dacl = dace.getDacl();
            set.add(dacl.getId());
        }

        Integer[] array = set.toArray(new Integer[0]);
        return array;
    }

}
