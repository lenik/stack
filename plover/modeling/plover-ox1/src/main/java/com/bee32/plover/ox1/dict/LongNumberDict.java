package com.bee32.plover.ox1.dict;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.model.ModelTemplate;

@ModelTemplate
@MappedSuperclass
public abstract class LongNumberDict
        extends DictEntity<Long> {

    private static final long serialVersionUID = 1L;

    long number;

    public LongNumberDict() {
        super();
    }

    public LongNumberDict(long number, String alias, String description) {
        super(alias, description);
        this.number = number;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof LongNumberDict)
            _populate((LongNumberDict) source);
        else
            super.populate(source);
    }

    protected void _populate(LongNumberDict o) {
        super._populate(o);
        this.number = o.number;
    }

    @Transient
    @Override
    public Long getId() {
        return getNumber();
    }

    @Override
    protected void setId(Long id) {
        setNumber(id);
    }

    @Id
    @Column(nullable = false)
    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

}
