package com.bee32.icsf.access.acl;

import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;

public interface IACLService {

    Set<Integer> getACLs(Principal principal, Permission permission);

}
