package com.bee32.icsf.access.acl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
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
@SequenceGenerator(name = "idgen", sequenceName = "acl_seq", allocationSize = 1)
public class ACL
        extends AbstractACL<ACL> {

    private static final long serialVersionUID = 1L;

    Map<Principal, Long> _entryMap;
    transient Map<Principal, Permission> entryMap;

    public ACL() {
        this(null, new LinkedHashMap<Principal, Long>());
    }

    public ACL(ACL parent) {
        this(parent, new LinkedHashMap<Principal, Long>());
    }

    public ACL(ACL parent, Map<Principal, Long> _entries) {
        super();
        setEntryMap(_entries);
    }

    @Override
    protected ACL create(Map<Principal, Permission> entryMap) {
        Map<Principal, Long> _entryMap = new LinkedHashMap<Principal, Long>();
        for (Entry<Principal, Permission> entry : entryMap.entrySet()) {
            Principal principal = entry.getKey();
            Permission permission = entry.getValue();
            long permissionLong = permission.toLong();
            _entryMap.put(principal, permissionLong);
        }
        ACL acl = new ACL(null, _entryMap);
        return acl;
    }

    @Override
    protected void createTransients() {
        super.createTransients();
        setEntryMap(_entryMap);
    }

    @ManyToOne
    @Override
    public ACL getInheritedACL() {
        return getParent();
    }

    @ManyToMany
    @JoinTable(name = "ACE")
    public Map<Principal, Long> get_entryMap() {
        return _entryMap;
    }

    @Transient
    public Map<Principal, Permission> getEntryMap() {
        return entryMap;
    }

    public void setEntryMap(Map<Principal, Long> entryMap) {
        if (entryMap == null)
            throw new NullPointerException("aceMap");
        this._entryMap = entryMap;
        this.entryMap = TransformedMap.decorate(entryMap, NOPTransformer.INSTANCE, PermissionTransformer.INSTANCE);
    }

    @Transient
    @Override
    public Set<Principal> getDeclaredPrincipals() {
        return _entryMap.keySet();
    }

    @Override
    public Permission getDeclaredPermission(Principal principal) {
        return entryMap.get(principal);
    }

    @Override
    public int size() {
        return _entryMap.size();
    }

    @Transient
    @Override
    public Collection<Entry<Principal, Permission>> getEntries() {
        return entryMap.entrySet();
    }

    @Override
    public void add(Entry<? extends Principal, Permission> entry) {
        Principal principal = entry.getKey();
        Permission permission = entry.getValue();
        add(principal, permission);
    }

    @Override
    public Permission add(Principal principal, Permission permission) {
        Permission existing = entryMap.get(principal);
        if (existing != null) {
            Permission merged = new Permission(0);
            existing.merge(existing);
            existing.merge(permission);
            permission = merged;
        }
        entryMap.put(principal, permission);
        return permission;
    }

    @Override
    public boolean remove(Principal principal) {
        Long existing = _entryMap.remove(principal);
        return existing != null;
    }

}
