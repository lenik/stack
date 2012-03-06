package com.bee32.plover.ox1.dict;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

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

X-Population

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
