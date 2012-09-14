package com.bee32.plover.ox1.c;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.bee32.icsf.principal.User;
import com.bee32.plover.model.ModelTemplate;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.util.zh.ZhUtil;

@ModelTemplate
@MappedSuperclass
public abstract class CEntity<K extends Serializable>
        extends Entity<K> {

    private static final long serialVersionUID = 1L;

    static final int KEYWORD_MAXLEN = 200;

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

    @Override
    public void populate(Object source) {
        if (source instanceof CEntity<?>) {
            CEntity<?> o = (CEntity<?>) source;
            _populate(o);
        } else {
            super.populate(source);
        }
    }

    protected void _populate(CEntity<?> o) {
        super._populate(o);
        keyword = o.keyword;
        keywordUpdated = o.keywordUpdated;
        owner = o.owner;
        aclId = o.aclId;
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
        // List<String> keywords = new ArrayList<String>();
        KeywordBuilder keywords = new KeywordBuilder();
        populateKeywords(keywords);
        String mixed = keywords.toString();
        String pinyin = ZhUtil.pinyinAbbr(mixed).trim();

        String truncated = pinyin;
        if (truncated.length() > KEYWORD_MAXLEN)
            truncated = truncated.substring(0, KEYWORD_MAXLEN);

        truncated = truncated.trim();
        if (truncated.isEmpty())
            truncated = null;
        return truncated;
    }

    protected void populateKeywords(Collection<String> keywords) {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public User getOwner() {
        CEntity<?> owning = owningEntity();
        if (owning != null)
            return owning.getOwner();
        else
            return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Column(name = "acl")
    @Index(name = "##_acl")
    public Integer getAclId() {
        CEntity<?> owning = owningEntity();
        if (owning != null)
            return owning.getAclId();
        else
            return aclId;
    }

    void setAclId(Integer aclId) {
        this.aclId = aclId;
    }

    protected CEntity<?> owningEntity() {
        return null;
    }

}
