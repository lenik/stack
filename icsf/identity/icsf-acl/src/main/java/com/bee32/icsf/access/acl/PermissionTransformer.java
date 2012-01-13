package com.bee32.icsf.access.acl;

import org.apache.commons.collections15.Transformer;

import com.bee32.icsf.access.Permission;

public class PermissionTransformer
        implements Transformer<Long, Permission> {

    @Override
    public Permission transform(Long input) {
        if (input == null)
            return null;
        return Permission.fromLong(input);
    }

    public static final PermissionTransformer INSTANCE = new PermissionTransformer();

}
