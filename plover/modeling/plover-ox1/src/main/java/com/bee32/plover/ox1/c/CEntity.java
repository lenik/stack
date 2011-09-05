package com.bee32.plover.ox1.c;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.ox1.principal.User;

@MappedSuperclass
public abstract class CEntity<K extends Serializable>
        extends Entity<K> {

    private static final long serialVersionUID = 1L;

    static final int KEYWORD_MAXLEN = 16;

    String keyword;
    boolean keywordUpdated;

    User owner;
    Integer aclId;

    public CEntity() {
        super();
    }

    public CEntity(String name) {
        super(name);
    }

    @Column(length = KEYWORD_MAXLEN)
    @Index(name = "##_keyword")
    protected String getKeyword() {
        if (!keywordUpdated) {
            keyword = buildKeyword();
            keywordUpdated = true;
        }
        return keyword;
    }

    protected void setKeyword(String keyword) {
        this.keyword = keyword;
        this.keywordUpdated = true;
    }

    protected void invalidate() {
        invalidateKeyword();
    }

    protected final void invalidateKeyword() {
        this.keywordUpdated = false;
    }

    /**
     * @see ZhUtil#getPinyinAbbreviation(String).
     */
    @Transient
    protected String buildKeyword() {
        return null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Column(name = "acl")
    @Index(name = "##_acl")
    public Integer getAclId() {
        return aclId;
    }

    void setAclId(Integer aclId) {
        this.aclId = aclId;
    }

}
