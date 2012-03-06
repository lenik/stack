package com.bee32.icsf.principal.realm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;

@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "realm_seq", allocationSize = 1)
public class Realm
        extends UIEntityAuto<Integer>
        implements IRealm {

    private static final long serialVersionUID = 1L;

    protected Set<Principal> principals = new HashSet<Principal>();
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
    public void populate(Object source) {
        if (source instanceof Realm)
            _populate((Realm) source);
        else
            super.populate(source);
    }

    protected void _populate(Realm o) {
        super._populate(o);
        principals = new HashSet<Principal>(o.principals);
        users = null;
        groups = null;
        roles = null;
    }

    @NaturalId
    @Column(length = 30, unique = true)
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");

        if (this.name != null && !this.name.equals(name))
            throw new IllegalStateException("You can't change Realm.name");

        this.name = name;
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
        return principals;
    }

    public void setPrincipals(Set<Principal> principals) {
        if (principals == null)
            throw new NullPointerException("principals");
        this.principals = principals;
    }

    @Transient
    @Override
    public Set<User> getUsers() {
        if (users == null) {
            synchronized (this) {
                if (users == null) {
                    users = new HashSet<User>();

                    for (Principal principal : principals)
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

                    for (Principal principal : principals)
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

                    for (Principal principal : principals)
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

}
