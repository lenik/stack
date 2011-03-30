package com.bee32.icsf.access.alt;

import java.util.HashSet;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class R_ACL
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    private R_ACL parent;

    private String objType;
    private long objId;

    private Set<R_ACE> entries;

    public R_ACL() {
        super("acl");
    }

    public R_ACL(R_ACL parent) {
        super("acl");

    }

    @ManyToOne(optional = true)
    public R_ACL getParent() {
        return parent;
    }

    public void setParent(R_ACL parent) {
        this.parent = parent;
    }

    @NaturalId
    @Column(length = 50)
    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    @NaturalId
    public long getObjId() {
        return objId;
    }

    public void setObjId(long objId) {
        this.objId = objId;
    }

    @OneToMany(mappedBy = "acl")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<R_ACE> getEntries() {
        if (entries == null) {
            synchronized (this) {
                if (entries == null) {
                    entries = new HashSet<R_ACE>();
                }
            }
        }
        return entries;
    }

    public void setEntries(Set<R_ACE> entries) {
        this.entries = entries;
    }

    /**
     * Add ACE entry.
     * <p>
     * Duplicated ACE are skipped.
     *
     * @param ace
     *            Non-<code>null</code> ACE to be added.
     * @throws IllegalUsageException
     *             If the ACE is bound to another ACL.
     */
    public boolean addACE(R_ACE ace) {
        if (ace == null)
            throw new NullPointerException("ace");

        R_ACL acl = ace.getAcl();
        if (acl == null)
            ace.setAcl(this);
        else if (acl != this)
            throw new IllegalUsageException("Add an ACE bound to another ACL: " + ace);

        return getEntries().add(ace);
    }

    /**
     * Add an Allow-ACE to this ACL.
     * <p>
     * Duplicated ACE are skipped.
     *
     * @param principal
     *            The principal for the new ACE.
     * @param permission
     *            The allowed permission for the new ACE.
     * @return The created allowed ACE, returns <code>null</code> if the ACE is already defined.
     */
    public R_ACE addAllowACE(Principal principal, Permission permission) {
        R_ACE ace = new R_ACE(this, principal, permission, true);
        if (!addACE(ace))
            return null;
        return ace;
    }

    /**
     * Add an Deny-ACE to this ACL.
     * <p>
     * Duplicated ACE are skipped.
     *
     * @param principal
     *            The principal for the new ACE.
     * @param permission
     *            The denied permission for the new ACE.
     * @return The created deny ACE, returns <code>null</code> if the ACE is already defined.
     */
    public R_ACE addDenyACE(Principal principal, Permission permission) {
        R_ACE ace = new R_ACE(this, principal, permission, false);
        if (!addACE(ace))
            return null;
        return ace;
    }

    /**
     * Remove an ACE entry from this ACL.
     *
     * If the ACE isn't existed in this ACL, it's just skipped.
     *
     * @param ace
     *            Non-<code>null</code> ACE entry to be removed.
     * @return <code>true</code> If the ACE is existed and have been successfully removed from this
     *         ACL.
     */
    public boolean removeACE(R_ACE ace) {
        if (ace == null)
            throw new NullPointerException("ace");

        if (entries == null)
            return false;

        return entries.remove(ace);
    }

    /**
     * Convert to the underlying ACL.
     *
     * @return Non-<code>null</code> underlying ACL.
     */
    public ACL toACL() {
        ACL inheritedACL = null;
        if (parent != null)
            inheritedACL = getParent().toACL();

        ACL acl = new ACL(inheritedACL);
        for (R_ACE r_ace : entries) {
            IACL.Entry ace = r_ace.toACE();
            acl.add(ace);
        }

        return acl;
    }

}
