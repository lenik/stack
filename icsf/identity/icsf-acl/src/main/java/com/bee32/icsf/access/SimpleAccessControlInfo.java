package com.bee32.icsf.access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SimpleAccessControlInfo
        implements IAccessControlInfo {

    private List<IManagedOperation> operations;

    public SimpleAccessControlInfo() {
        this.operations = new ArrayList<IManagedOperation>();
    }

    public SimpleAccessControlInfo(IManagedOperation... operations) {
        if (operations == null)
            throw new NullPointerException("operations");
        this.operations = new ArrayList<IManagedOperation>(Arrays.asList(operations));
    }

    @Override
    public Collection<? extends IManagedOperation> getManagedOperations() {
        return operations;
    }

    @Override
    public Permission getRequiredPermission(String operationName) {
        for (IManagedOperation o : getManagedOperations()) {
            if (operationName.equals(o.getName()))
                return o.getRequiredPermission();
        }
        return null;
    }

}
