package com.bee32.icsf.principal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.EntityAuto;

/**
 * 用户配置项
 *
 * 每个用户独立设置的配置项。
 *
 * <p lang="en">
 * User Preference
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "user_preference_seq", allocationSize = 1)
public class UserPreference
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int KEY_LENGTH = 30;
    public static final int VALUE_LENGTH = 100;

    User user;
    String key;
    String value;

    @Override
    public void populate(Object source) {
        if (source instanceof UserPreference)
            _populate((UserPreference) source);
        else
            super.populate(source);
    }

    protected void _populate(UserPreference o) {
        super._populate(o);
        user = o.user;
        key = o.key;
        value = o.value;
    }

    /**
     * 用户
     *
     * 配置项对应的用户。
     */
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

    /**
     * 配置项
     *
     * 指定配置项的键值。
     */
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

    /**
     * 配置值
     *
     * 设置配置项的内容。
     */
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
        if (user == null)
            throw new NullPointerException("user");
        if (key == null)
            throw new NullPointerException("key");
        return selectors(//
                selector(prefix + "user", user), //
                new Equals(prefix + "key", key));
    }

}
