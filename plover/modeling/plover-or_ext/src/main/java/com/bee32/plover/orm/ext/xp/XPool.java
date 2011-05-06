package com.bee32.plover.orm.ext.xp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntitySpec;

@MappedSuperclass
public abstract class XPool<Es extends Entity<?>>
        extends EntitySpec<Long> {

    private static final long serialVersionUID = 1L;

    Long id;

    Es source;

    XPoolModel model;

    @Id
    @GeneratedValue
    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Es getSource() {
        return source;
    }

    public void setSource(Es source) {
        this.source = source;
    }

}
