package com.bee32.icsf.principal;

import java.util.Collections;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("G1")
public class SimpleGroup
        extends Group {

    private static final long serialVersionUID = 1L;

    public SimpleGroup() {
        super();
    }

    public SimpleGroup(String name, SimpleUser owner, SimpleUser... memberUsers) {
        super(name, owner, memberUsers);
    }

    public SimpleGroup(String name) {
        super(name);
    }

    @Override
    public SimpleGroup getInheritedGroup() {
        return (SimpleGroup) super.getInheritedGroup();
    }

    @Override
    public Set<SimpleGroup> getDerivedGroups() {
        return Collections.emptySet();
    }

    @Override
    public IUserPrincipal getOwner() {
        return null;
    }

    @Override
    public void setOwner(IUserPrincipal owner) {
    }

    @Override
    public void setPrimaryRole(IRolePrincipal role) {
    }

}
