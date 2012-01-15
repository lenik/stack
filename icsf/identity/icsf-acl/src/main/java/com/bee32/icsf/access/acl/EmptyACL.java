package com.bee32.icsf.access.acl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.free.ReadOnlyException;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;

public class EmptyACL
        implements IACL {

    EmptyACL() {
    }

    @Override
    public IACL getInheritedACL() {
        return null;
    }

    @Override
    public IACL flatten() {
        return this;
    }

    @Override
    public Set<? extends Principal> getDeclaredPrincipals() {
        return Collections.emptySet();
    }

    @Override
    public Set<? extends Principal> getEffectivePrincipals() {
        return Collections.emptySet();
    }

    @Override
    public Collection<? extends Principal> findPrincipals(Permission requiredPermission) {
        return Collections.emptySet();
    }

    @Override
    public Collection<? extends Principal> findPrincipals(String requiredMode) {
        return Collections.emptySet();
    }

    @Override
    public Permission getDeclaredPermission(Principal principal) {
        return null;
    }

    @Override
    public Permission getEffectivePermission(Principal principal) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Map<? extends Principal, Permission> getDeclaredEntries() {
        return Collections.emptyMap();
    }

    @Override
    public Permission add(Principal principal, Permission permission) {
        throw new ReadOnlyException();
    }

    @Override
    public Permission add(Principal principal, String mode) {
        throw new ReadOnlyException();
    }

    @Override
    public void add(Entry<? extends Principal, Permission> entry) {
        throw new ReadOnlyException();
    }

    @Override
    public boolean remove(Entry<? extends Principal, Permission> entry) {
        throw new ReadOnlyException();
    }

    @Override
    public boolean remove(Principal principal) {
        throw new ReadOnlyException();
    }

    static final EmptyACL instance = new EmptyACL();

    public static EmptyACL getInstance() {
        return instance;
    }

}
