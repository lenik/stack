package com.bee32.icsf.access.acl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 3)
@SequenceGenerator(name = "idgen", sequenceName = "acl_seq", allocationSize = 1)
public class ACL
        extends AbstractACL<ACL> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 10;

    String name;
    List<ACLEntry> entries = new ArrayList<ACLEntry>();

    public ACL() {
        this(null, new ArrayList<ACLEntry>());
    }

    public ACL(ACL parent) {
        this(parent, new ArrayList<ACLEntry>());
    }

    public ACL(ACL parent, List<ACLEntry> entries) {
        super();
        setEntries(entries);
    }

    @NaturalId
    @Column(length = NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected ACL create(Map<Principal, Permission> entryMap) {
        List<ACLEntry> entries = new ArrayList<ACLEntry>();
        for (Entry<Principal, Permission> entry : entryMap.entrySet()) {
            Principal principal = entry.getKey();
            Permission permission = entry.getValue();
            ACLEntry aclEntry = new ACLEntry(null, principal, permission);
            entries.add(aclEntry);
        }
        ACL acl = new ACL(null, entries);
        return acl;
    }

    @Transient
    @Override
    public ACL getInheritedACL() {
        return getParent();
    }

    public void setInheritedACL(ACL inheritedACL) {
        setParent(inheritedACL);
    }

    @OneToMany(mappedBy = "ACL", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<ACLEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ACLEntry> entries) {
        if (entries == null)
            throw new NullPointerException("entries");
        this.entries = entries;
    }

    @Transient
    @Override
    public Set<Principal> getDeclaredPrincipals() {
        Set<Principal> declaredPrincipals = new HashSet<Principal>();
        for (ACLEntry entry : entries)
            declaredPrincipals.add(entry.getPrincipal());
        return declaredPrincipals;
    }

    /**
     * @return The permission reference, you can modify it directly.
     */
    @Override
    public Permission getDeclaredPermission(Principal principal) {
        if (principal == null)
            throw new NullPointerException("principal");
        for (ACLEntry entry : entries) {
            Principal declaredPrincipal = entry.getPrincipal();
            if (declaredPrincipal.equals(principal))
                return entry.getPermission();
        }
        return null;
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Transient
    @Override
    public Map<Principal, Permission> getDeclaredEntries() {
        return compactPermission();
    }

    public Map<Principal, Permission> compactPermission() {
        Map<Principal, Permission> unique = new HashMap<Principal, Permission>();
        Iterator<ACLEntry> iterator = entries.iterator();
        while (iterator.hasNext()) {
            ACLEntry entry = iterator.next();
            Principal principal = entry.getPrincipal();
            Permission permission = unique.get(principal);
            if (permission == null)
                unique.put(principal, permission = entry.getPermission());
            else {
                permission.merge(entry.getPermission());
                iterator.remove();
            }
        }
        return unique;
    }

    public void add(Principal principal, int allowBits) {
        add(principal, new Permission(allowBits));
    }

    @Override
    public void add(Entry<? extends Principal, Permission> entry) {
        Principal principal = entry.getKey();
        Permission permission = entry.getValue();
        add(principal, permission);
    }

    @Override
    public Permission add(Principal principal, Permission permission) {
        if (principal == null)
            throw new NullPointerException("principal");
        if (permission == null)
            throw new NullPointerException("permission");
        Permission result = getDeclaredPermission(principal);
        if (result == null) {
            result = new Permission(0);
            ACLEntry entry = new ACLEntry(this, principal, result);
            entries.add(entry);
        }
        result.merge(permission);
        return result;
    }

    @Override
    public boolean remove(Principal principal) {
        if (principal == null)
            throw new NullPointerException("principal");
        Iterator<ACLEntry> iterator = entries.iterator();
        while (iterator.hasNext()) {
            ACLEntry entry = iterator.next();
            Principal declaredPrincipal = entry.getPrincipal();
            if (declaredPrincipal.equals(principal)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    protected Serializable naturalId() {
        if (name == null)
            return new DummyId(this);
        return name;
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }

}
