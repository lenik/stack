package com.bee32.icsf.access.acl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Set;

import javax.free.ReadOnlyException;

import com.bee32.icsf.access.Permission;
import com.bee32.plover.ox1.principal.IPrincipal;

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
    public Set<? extends IPrincipal> getDeclaredPrincipals() {
        return Collections.emptySet();
    }

    @Override
    public Set<? extends IPrincipal> getPrincipals() {
        return Collections.emptySet();
    }

    @Override
    public Collection<? extends IPrincipal> findPrincipals(Permission requiredPermission) {
        return Collections.emptySet();
    }

    @Override
    public Collection<? extends IPrincipal> findPrincipals(String requiredMode) {
        return Collections.emptySet();
    }

    @Override
    public Permission getDeclaredPermission(IPrincipal principal) {
        return null;
    }

    @Override
    public Permission getPermission(IPrincipal principal) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Collection<? extends Entry<? extends IPrincipal, Permission>> getEntries() {
        return Collections.emptyList();
    }

    @Override
    public Permission add(IPrincipal principal, Permission permission) {
        throw new ReadOnlyException();
    }

    @Override
    public Permission add(IPrincipal principal, String mode) {
        throw new ReadOnlyException();
    }

    @Override
    public void add(Entry<? extends IPrincipal, Permission> entry) {
        throw new ReadOnlyException();
    }

    @Override
    public boolean remove(Entry<? extends IPrincipal, Permission> entry) {
        throw new ReadOnlyException();
    }

    @Override
    public boolean remove(IPrincipal principal) {
        throw new ReadOnlyException();
    }

    static final EmptyACL instance = new EmptyACL();

    public static EmptyACL getInstance() {
        return instance;
    }

}
