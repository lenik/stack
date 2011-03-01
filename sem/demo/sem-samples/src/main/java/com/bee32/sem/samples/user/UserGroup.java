package com.bee32.sem.samples.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.bee32.icsf.principal.AbstractGroup;
import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;

public class UserGroup
        extends AbstractGroup {

    private static final long serialVersionUID = 1L;

    private String name;
    private List<User> employees;

    public UserGroup(String name, User... employees) {
        if (name == null)
            throw new NullPointerException("name");
        if (employees == null)
            throw new NullPointerException("employees");
        this.name = name;
        this.employees = Arrays.asList(employees);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IGroupPrincipal getInheritedGroup() {
        return null;
    }

    @Override
    public Collection<? extends IRolePrincipal> getAssignedRoles() {
        return Collections.emptyList();
    }

    @Override
    public List<User> getMemberUsers() {
        return employees;
    }

    @Override
    public String toString() {
        return "<公司 " + name + ">";
    }

}
