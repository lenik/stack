package com.bee32.icsf.access.acl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.NOPTransformer;
import org.apache.commons.collections15.map.TransformedMap;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Principal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 3)
@SequenceGenerator(name = "idgen", sequenceName = "dacl_seq", allocationSize = 1)
public class ACL
        extends AbstractACL<ACL> {

    private static final long serialVersionUID = 1L;

    Map<Principal, ACLEntry> entryMap;

    public ACL() {
        this(null, new LinkedHashMap<Principal, ACLEntry>());
    }

    public ACL(ACL parent) {
        this(parent, new LinkedHashMap<Principal, ACLEntry>());
    }

    public ACL(ACL parent, Map<Principal, ACLEntry> _entries) {
        super();
        setEntryMap(_entries);
    }

    @Override
    protected ACL create(Map<Principal, Permission> entryMap) {
        Map<Principal, ACLEntry> _entryMap = new LinkedHashMap<Principal, ACLEntry>();
        for (Entry<Principal, Permission> entry : entryMap.entrySet()) {
            Principal principal = entry.getKey();
            Permission permission = entry.getValue();
            ACLEntry aclEntry = new ACLEntry(null, principal, permission);
            _entryMap.put(principal, aclEntry);
        }
        ACL acl = new ACL(null, _entryMap);
        return acl;
    }

    @Override
    protected void createTransients() {
        super.createTransients();
        setEntryMap(entryMap);
    }

    @ManyToOne
    @Override
    public ACL getInheritedACL() {
        return getParent();
    }

    @ManyToMany
    public Map<Principal, ACLEntry> getEntryMap() {
        return entryMap;
    }

    public void setEntryMap(Map<Principal, ACLEntry> entryMap) {
        if (entryMap == null)
            throw new NullPointerException("aceMap");
        this.entryMap = entryMap;
        this.entryMap = TransformedMap.decorate(entryMap, NOPTransformer.INSTANCE, PermissionTransformer.INSTANCE);
    }

    @Transient
    @Override
    public Set<Principal> getDeclaredPrincipals() {
        return entryMap.keySet();
    }

    @Override
    public Permission getDeclaredPermission(Principal principal) {
        ACLEntry existing = entryMap.get(principal);
        if (existing == null)
            return null;
        else
            return existing.permission;
    }

    @Override
    public int size() {
        return entryMap.size();
    }

    @Override
    public Map<Principal, Permission> getDeclaredEntries() {
        return TransformedMap.decorate(entryMap, NOPTransformer.INSTANCE, EntryPermissionTransformer.INSTANCE);
    }

    @Override
    public void add(Entry<? extends Principal, Permission> entry) {
        Principal principal = entry.getKey();
        Permission permission = entry.getValue();
        add(principal, permission);
    }

    @Override
    public Permission add(Principal principal, final Permission permission) {
        ACLEntry existing = entryMap.get(principal);
        if (existing != null) {
            Permission merged = new Permission(0);
            merged.merge(existing.permission);
            merged.merge(permission);
            existing.permission = merged;
        } else {
            ACLEntry entry = new ACLEntry(this, principal, permission);
            entryMap.put(principal, entry);
        }
        return permission;
    }

    @Override
    public boolean remove(Principal principal) {
        ACLEntry existing = entryMap.remove(principal);
        return existing != null;
    }

}
