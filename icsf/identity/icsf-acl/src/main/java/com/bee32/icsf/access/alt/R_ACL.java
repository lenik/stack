package com.bee32.icsf.access.alt;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class R_ACL
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    private R_ACL parent;

    private String objType;
    private long objId;

    private List<R_ACE> aceList;

    public R_ACL() {
        super();
    }

    public R_ACL(String name) {
        super(name);
    }

    @ManyToOne(optional = true)
    public R_ACL getParent() {
        return parent;
    }

    public void setParent(R_ACL parent) {
        this.parent = parent;
    }

    @NaturalId
    @Column(length = 30, nullable = false)
    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    @NaturalId
    @Column(nullable = false)
    public long getObjId() {
        return objId;
    }

    public void setObjId(long objId) {
        this.objId = objId;
    }

    @OneToMany
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<R_ACE> getAceList() {
        return aceList;
    }

    public void setAceList(List<R_ACE> aceList) {
        this.aceList = aceList;
    }

    public ACL toACL() {
        ACL inheritedACL = null;
        if (parent != null)
            inheritedACL = getParent().toACL();

        ACL acl = new ACL(inheritedACL);
        for (R_ACE r_ace : aceList) {
            IACL.Entry ace = r_ace.toACE();
            acl.add(ace);
        }

        return acl;
    }

}
