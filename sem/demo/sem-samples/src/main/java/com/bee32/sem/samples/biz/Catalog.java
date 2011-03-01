package com.bee32.sem.samples.biz;

import java.util.Collection;

import com.bee32.icsf.access.IAccessControlInfo;
import com.bee32.icsf.access.IManagedOperation;
import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.PermissionException;
import com.bee32.icsf.access.annotation.AnnotatedAccessControlInfo;
import com.bee32.icsf.access.annotation.ManagedOperation;

public class Catalog
        implements IAccessControlInfo {

    Permission p;

    private static Permission addEntryPermission = null;

    /**
     * @throws PermissionException
     */
    @ManagedOperation
    public void addEntry(String entry) {
        // AccessControlContext
        addEntryPermission.getAuthority().getACL().checkPermission(addEntryPermission);
    }

    static AnnotatedAccessControlInfo acimpl = new AnnotatedAccessControlInfo(Catalog.class);

    @Override
    public Collection<? extends IManagedOperation> getManagedOperations() {
        return acimpl.getManagedOperations();
    }

    @Override
    public Permission getRequiredPermission(String operationName) {
        return acimpl.getRequiredPermission(operationName);
    }

}
