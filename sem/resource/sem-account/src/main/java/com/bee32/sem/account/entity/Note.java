package com.bee32.sem.account.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.people.entity.Org;

/**
 * 票据基类
 *
 * @rewrite 票据日期:beginTime 票据到期日:endTime
 */
@Entity
@DiscriminatorValue("NOTE")
public class Note
        extends AccountEd {

    private static final long serialVersionUID = 1L;

    public static final int ACCEPT_BANK_LENGTH = 100;
    public static final int BILL_NO_LENGTH = 50;

    NoteBalancing noteBalancing;

    String acceptBank;
    Org acceptOrg;
    String billNo;
    BillType billType;

    /**
     * 对应的票据结算
     *
     * @return
     */
    @OneToOne(mappedBy = "note")
    @Cascade(CascadeType.ALL)
    public NoteBalancing getNoteBalancing() {
        return noteBalancing;
    }

    public void setNoteBalancing(NoteBalancing noteBalancing) {
        this.noteBalancing = noteBalancing;
    }

    /**
     * 承兑银行
     *
     * @return
     */
    @Column(length = ACCEPT_BANK_LENGTH)
    public String getAcceptBank() {
        return acceptBank;
    }

    public void setAcceptBank(String acceptBank) {
        this.acceptBank = acceptBank;
    }

    /**
     * 承兑商业机构
     *
     * @return
     */
    @ManyToOne
    public Org getAcceptOrg() {
        return acceptOrg;
    }

    public void setAcceptOrg(Org acceptOrg) {
        this.acceptOrg = acceptOrg;
    }

    /**
     * 承兑汇票号码
     */
    @Column(length = BILL_NO_LENGTH)
    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 承兑汇票类型
     */
    @ManyToOne
    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }

}
