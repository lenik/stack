package com.bee32.icsf.access.alt;

import java.io.Serializable;

import javax.free.IllegalUsageException;

import com.bee32.icsf.access.acl.legacy.LegacyACL;
import com.bee32.icsf.access.resource.Resource;

public class R_ACL
        extends LegacyACL
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private R_ACL parent;
    private Resource resource;

    public R_ACL() {
    }

    public R_ACL(R_ACL parent, Resource resource) {
        this.parent = parent;
        this.resource = resource;
    }

    public R_ACL getParent() {
        return parent;
    }

    public void setParent(R_ACL parent) {
        this.parent = parent;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
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
    public void addACE(R_ACE ace) {
        if (ace == null)
            throw new NullPointerException("ace");
        add(ace.getPrincipal(), ace.getPermission());
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
        return this.remove(ace.getPrincipal());
    }

    /**
     * Convert to the underlying ACL.
     *
     * @return Non-<code>null</code> underlying ACL.
     */
    public LegacyACL toACL() {
        return this;
    }

}
