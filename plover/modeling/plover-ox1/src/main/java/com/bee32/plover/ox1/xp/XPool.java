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

X-Population

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

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public Es getSource() {
        return source;
    }

    public void setSource(Es source) {
        this.source = source;
    }

}
