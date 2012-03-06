package com.bee32.sem.asset.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 科目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_subject_seq", allocationSize = 1)
@AttributeOverrides({//
        @AttributeOverride(name = "id", column = @Column(length = AccountSubject.NAME_LENGTH)), //
        @AttributeOverride(name = "label", column = @Column(length = AccountSubject.LABEL_LENGTH, unique = AccountSubject.LABEL_UNIQUE)) })
public class AccountSubject
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 10;
    public static final int LABEL_LENGTH = 50;
    public static final boolean LABEL_UNIQUE = false;

    public static final int DEBIT = 1;
    public static final int CREDIT = 2;
    public static final int BOTH = DEBIT | CREDIT;

    boolean debitSign = true;
    boolean creditSign;

    public AccountSubject() {
        super();
    }

    public AccountSubject(String name, String label) {
        super(name, label);
    }

    public AccountSubject(String name, String label, String description) {
        super(name, label, description);
    }

    public AccountSubject(String name, String label, int flags) {
        super(name, label);
        this.debitSign = (flags & DEBIT) != 0;
        this.creditSign = (flags & CREDIT) != 0;
    }

X-Population

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
     */
    @Column(nullable = false)
    public boolean isCreditSign() {
        return creditSign;
    }

    public void setCreditSign(boolean creditSign) {
        this.creditSign = creditSign;
    }

}
