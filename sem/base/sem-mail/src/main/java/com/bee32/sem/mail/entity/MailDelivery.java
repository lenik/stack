package com.bee32.sem.mail.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bee32.plover.orm.ext.color.PinkEntity;
import com.bee32.sem.mail.MailFlags;

@Entity
public class MailDelivery
        extends PinkEntity<Long> {

    private static final long serialVersionUID = 1L;

    Mail mail;
    MailFolder folder;
    MailParty party;

    Date sentDate;
    String sendError;
    Date receivedDate;
    Date receiptDate;

    public final MailFlags flags = new MailFlags();

    public MailDelivery() {
    }

    public MailDelivery(Mail mail, MailParty party) {
        this.mail = mail;
        this.party = party;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    @Basic(optional = false)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public MailParty getParty() {
        return party;
    }

    public void setParty(MailParty party) {
        if (party == null)
            throw new NullPointerException("party");
        this.party = party;
    }

    @ManyToOne
    public MailFolder getFolder() {
        return folder;
    }

    public void setFolder(MailFolder folder) {
        this.folder = folder;
    }

    @Column(name = "flags", nullable = false)
    public int getFlags() {
        return flags.bits;
    }

    public void setFlags(int flags) {
        this.flags.bits = flags;
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
