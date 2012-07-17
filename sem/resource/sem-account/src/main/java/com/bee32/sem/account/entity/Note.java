package com.bee32.sem.account.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.people.entity.Party;


/**
 * 票据基类
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("NOTE")
public class Note extends AccountEd {

    private static final long serialVersionUID = 1L;

    public static final int BANK_LENGTH = 100;
    public static final int BILL_NO_LENGTH = 50;

    NoteBalancing noteBalancing;

    String bank;
    Party party;
    String billNo;
    BillType billType;

    /**
     * 对应的票据结算
     * @return
     */
    @OneToOne(orphanRemoval = true, mappedBy = "noteBalancing")
    @Cascade(CascadeType.ALL)
    public NoteBalancing getNoteBalancing() {
        return noteBalancing;
    }

    public void setNoteBalancing(NoteBalancing noteBalancing) {
        this.noteBalancing = noteBalancing;
    }

    /**
     * 银行承兑汇票对应的银行
     * @return
     */
    @Column(length = BANK_LENGTH)
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }


    /**
     * 商业承兑汇票对应的商业机构
     */
    @ManyToOne
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
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
