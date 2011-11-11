package com.bee32.sem.asset.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.tree.TreeEntityAuto;

/**
 * 科目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "asset_subject_seq", allocationSize = 1)
public class AccountSubject
        extends TreeEntityAuto<Integer, AccountSubject> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 4;
    public static final int CODE_LENGTH = 10;
    public static final int TITLE_LENGTH = 30;

    boolean debitSign;
    boolean creditSign;

    public AccountSubject() {
        super();
    }

    public AccountSubject(String name) {
        super(name);
    }

    public AccountSubject(AccountSubject parent) {
        super(parent, null);
    }

    public AccountSubject(AccountSubject parent, String name) {
        super(parent, name);
    }

    /**
     * 科目代码
     */
    @NaturalId(mutable = true)
    @Column(length = NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 借方符号
     */
    @Column(nullable = false)
    public boolean isDebitSign() {
        return debitSign;
    }

    public void setDebitSign(boolean debitSign) {
        this.debitSign = debitSign;
    }

    /**
     * 贷方符号
     * @return
     */
    @Column(nullable = false)
    public boolean isCreditSign() {
        return creditSign;
    }

    public void setCreditSign(boolean creditSign) {
        this.creditSign = creditSign;
    }

    @Override
    protected Serializable naturalId() {
        if (name == null)
            return new DummyId(this);
        return name;
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }

}
