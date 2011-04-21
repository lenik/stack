package com.bee32.sem.mail.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.mail.MailFlags;
import com.bee32.sem.mail.MailOwnerType;

@Entity
public class MailCopy
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    Mail mail;

    User owner;
    MailOwnerType ownerType = MailOwnerType.FROM_USER;
    MailBox mailBox;

    final MailFlags flags = new MailFlags();

    Date sentDate;
    String sendError;
    Date receivedDate;
    Date receiptDate;

    public MailCopy() {
    }

    public MailCopy(Mail mail, MailOwnerType ownerType) {
        this.mail = mail;
        this.ownerType = ownerType;
        switch (ownerType) {
        case FROM_USER:
            owner = mail.getFromUser();
            break;

        case TO_USER:
            owner = mail.getToUser();
            break;

        case CC_USER:
        case BCC_USER:
            // You should set the owner explicitly.
        }
    }

    public MailCopy(Mail mail, MailOwnerType ownerType, User owner) {
        this.mail = mail;
        this.ownerType = ownerType;
        this.owner = owner;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Basic(optional = false)
    @Column(nullable = false)
    public MailOwnerType getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(MailOwnerType ownerType) {
        this.ownerType = ownerType;
    }

    @ManyToOne
    public MailBox getMailBox() {
        return mailBox;
    }

    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    @Column(name = "flags", nullable = false)
    public int getFlagBits() {
        return flags.bits;
    }

    public void setFlagBits(int flags) {
        this.flags.bits = flags;
    }

    /**
     * TODO: How to map value type MailFlags to SQL INT?
     */
    @Transient
    public MailFlags getFlags() {
        return flags;
    }

    @Transient
    public boolean isSent() {
        return sentDate != null;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Column(length = 200)
    public String getSendError() {
        return sendError;
    }

    public void setSendError(String sendError) {
        this.sendError = sendError;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

}
