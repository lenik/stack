package com.bee32.plover.orm.ext.dict;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class NumberDict
        extends DictEntity<Integer> {

    private static final long serialVersionUID = 1L;

    int number;

    public NumberDict() {
        super();
    }

    public NumberDict(int number, String alias, String description) {
        super(alias, description);
        this.number = number;
    }

    @Transient
    @Override
    public Integer getId() {
        return getNumber();
    }

    @Override
    protected void setId(Integer id) {
        setNumber(id);
    }

    @Id
    @Column(nullable = false)
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
