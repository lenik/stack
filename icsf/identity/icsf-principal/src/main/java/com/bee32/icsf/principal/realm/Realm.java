package com.bee32.icsf.principal.realm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.free.Nullables;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalBeanConfig;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class Realm
        extends EntityBean<Integer>
        implements IRealm {

    private static final long serialVersionUID = 1L;

    protected Set<Principal> principals;
    protected transient Set<User> users;
    protected transient Set<Group> groups;
    protected transient Set<Role> roles;

    public Realm() {
        super();
    }

    public Realm(String name) {
        super(name);
    }

    @Override
    public boolean contains(IPrincipal principal) {
        Collection<? extends IPrincipal> principals = getPrincipals();

        if (principals == null)
            return false;

        return principals.contains(principal);
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, //
    /*            */targetEntity = Principal.class)
    @JoinTable(name = "RealmPrincipal", //
    /*            */joinColumns = @JoinColumn(name = "principal"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "realm"))
    @Override
    public Set<Principal> getPrincipals() {
        if (principals == null) {
            synchronized (this) {
                if (principals == null) {
                    principals = new HashSet<Principal>();
                }
            }
        }
        return principals;
    }

    public void setPrincipals(Collection<? extends Principal> principals) {
        this.principals = new HashSet<Principal>(principals);
    }

    @Transient
    @Override
    public Set<User> getUsers() {
        if (users == null) {
            synchronized (this) {
                if (users == null) {
                    users = new HashSet<User>();

                    for (Principal principal : getPrincipals())
                        if (principal instanceof User)
                            users.add((User) principal);
                }
            }
        }
        return users;
    }

    @Transient
    @Override
    public Set<Group> getGroups() {
        if (groups == null) {
            synchronized (this) {
                if (groups == null) {
                    groups = new HashSet<Group>();

                    for (Principal principal : getPrincipals())
                        if (principal instanceof Group)
                            groups.add((Group) principal);
                }
            }
        }
        return groups;
    }

    @Transient
    @Override
    public Set<Role> getRoles() {
        if (roles == null) {
            synchronized (this) {
                if (roles == null) {
                    roles = new HashSet<Role>();

                    for (Principal principal : getPrincipals())
                        if (principal instanceof Role)
                            roles.add((Role) principal);
                }
            }
        }
        return roles;
    }

    @Override
    public void addPrincipal(IPrincipal principal) {
        getPrincipals().add((Principal) principal);
    }

    @Override
    public void removePrincipal(IPrincipal principal) {
        getPrincipals().remove(principal);
    }

    @Override
    public void addUser(IUserPrincipal user) {
        addPrincipal(user);
        getUsers().add((User) user);
    }

    @Override
    public void removeUser(IUserPrincipal user) {
        removePrincipal(user);
        getUsers().remove(user);
    }

    @Override
    public void addGroup(IGroupPrincipal group) {
        addPrincipal(group);
        getGroups().add((Group) group);
    }

    @Override
    public void removeGroup(IGroupPrincipal group) {
        removePrincipal(group);
        getGroups().remove(group);
    }

    @Override
    public void addRole(IRolePrincipal role) {
        addPrincipal(role);
        getRoles().add((Role) role);
    }

    @Override
    public void removeRole(IRolePrincipal role) {
        removePrincipal(role);
        getRoles().remove(role);
    }

    @Override
    protected int hashCodeEntity() {
        if (!PrincipalBeanConfig.fullEquality)
            return super.hashCodeEntity();

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((principals == null) ? 0 : principals.hashCode());
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Integer> otherEntity) {
        if (!PrincipalBeanConfig.fullEquality)
            return false;

        Realm other = (Realm) otherEntity;
        if (!Nullables.equals(principals, other.principals))
            return false;

        return true;
    }

}
