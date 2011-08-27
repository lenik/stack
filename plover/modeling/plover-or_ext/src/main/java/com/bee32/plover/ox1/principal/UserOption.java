package com.bee32.plover.ox1.principal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.And;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.EntityAuto;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "user_option_seq", allocationSize = 1)
public class UserOption
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int KEY_LENGTH = 30;
    public static final int VALUE_LENGTH = 100;

    User user;
    String key;
    String value;

    @NaturalId
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null)
            throw new NullPointerException("user");
        this.user = user;
    }

    @NaturalId
    @Column(length = KEY_LENGTH, nullable = false)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        if (key == null)
            throw new NullPointerException("key");
        this.key = key;
    }

    @Column(length = VALUE_LENGTH)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(user), //
                key);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return new And(//
                selector(prefix + "user", user), //
                new Equals(prefix + "key", key));
    }

}
