package com.bee32.plover.ox1.xp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.ox1.c.CEntitySpec;

@MappedSuperclass
public abstract class XPool<Es extends Entity<?>>
        extends CEntitySpec<Long> {

    private static final long serialVersionUID = 1L;

    public static final int LEN_A = 32;
    public static final int LEN_AA = 64;
    public static final int LEN_AAAA = 250; // slightly smaller than 256.

    Long id;

    Es source;

    XPoolModel model;

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof XPool)
            _populate((XPool<? extends Entity<?>>) source);
        else
            super.populate(source);
    }

    @SuppressWarnings("unchecked")
    protected void _populate(XPool<? extends Entity<?>> o) {
        super._populate(o);
        source = (Es) o.source;
        model = o.model;
    }

    /**
     * 标识符
     *
     * 属性的主键。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected void setId(Long id) {
        this.id = id;
    }

    /**
     * 源数据
     *
     * 属性所在的对象。
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Es getSource() {
        return source;
    }

    public void setSource(Es source) {
        this.source = source;
    }

}
