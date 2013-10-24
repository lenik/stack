package com.bee32.plover.ox1.dict;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;

@MappedSuperclass
public abstract class NumberDict
        extends DictEntity<Integer> {

    private static final long serialVersionUID = 1L;

    int number;

    public NumberDict() {
        super();
    }

    public NumberDict(int number, String label, String description) {
        super(label, description);
        this.number = number;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof NumberDict)
            _populate((NumberDict) source);
        else
            super.populate(source);
    }

    protected void _populate(NumberDict o) {
        super._populate(o);
        number = o.number;
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

    @Override
    protected Serializable naturalId() {
        return number;
    }

    @Override
    protected CriteriaElement selector(String prefix) {
        return new Equals(prefix + "number", number);
    }

}
