package com.bee32.icsf.principal;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.And;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Blue;

/**
 * 用户邮箱
 *
 * 管理用户的邮箱地址。
 *
 * <p lang="en">
 * User Email
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "user_email_seq", allocationSize = 1)
public class UserEmail
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final char EMAIL_INIT = '?';
    public static final char EMAIL_VERIFYING = '1';
    public static final char EMAIL_VERIFIED = 'V';

    User user;
    int rank;
    String address;
    char status = EMAIL_INIT;

    public UserEmail() {
    }

    public UserEmail(User user, String address) {
        this.user = user;
        this.address = address;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof UserEmail)
            _populate((UserEmail) source);
        else
            super.populate(source);
    }

    protected void _populate(UserEmail o) {
        super._populate(o);
        user = o.user;
        rank = o.rank;
        address = o.address;
        status = o.status;
    }

    /**
     * 用户
     *
     * 邮箱对应的用户。
     */
    @NaturalId
    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null)
            throw new NullPointerException("user");
        this.user = user;
    }

    /**
     * 级别
     *
     * 邮箱的级别，越小的数表示越高的级别。
     */
    @Column(nullable = false)
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * 邮箱地址
     *
     * 格式为 `a@b.c` 的邮箱地址。
     */
    @NaturalId
    @Column(length = 40, nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null)
            throw new NullPointerException("address");
        this.address = address;
    }

    /**
     * 邮箱状态
     *
     * 提示邮箱地址的状态（如无效、未验证、已验证等）。
     */
    @Basic(optional = false)
    @Column(nullable = false)
    public char getEmailStatus() {
        return status;
    }

    public void setEmailStatus(char emailStatus) {
        this.status = emailStatus;
    }

    @Transient
    public boolean isEmailVerified() {
        return status == EMAIL_VERIFIED;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalIdOpt(user), //
                address);
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return And.of(//
                new Equals(prefix + "user", user), //
                new Equals(prefix + "address", address));
    }

}
