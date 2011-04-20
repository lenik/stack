package com.bee32.sem.mail.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.mail.MailFlags;
import com.bee32.sem.mail.MailType;
import com.bee32.sem.mail.util.EmailUtil;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Mail
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    protected MailType type = MailType.USER;

    protected MailBox mailBox;

    public static final byte PRIORITY_URGENT = 10;
    public static final byte PRIORITY_HIGH = 20;
    public static final byte PRIORITY_NORMAL = 30;
    public static final byte PRIORITY_LOW = 40;
    protected byte priority = PRIORITY_NORMAL;

    protected final MailFlags flags = new MailFlags();

    String from;
    String to;
    String replyTo;

    User fromUser;
    User toUser;
    User replyToUser;

    String cc;
    String bcc;

    String subject;
    String body;

    Mail referrer;

    Date sentDate;
    String sendError;

    Date receivedDate;
    Date receiptDate;

    @Basic(optional = false)
    @Column(nullable = false)
    public MailType getType() {
        return type;
    }

    public void setType(MailType type) {
        this.type = type;
    }

    @ManyToOne
    public MailBox getMailBox() {
        return mailBox;
    }

    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    @Column(nullable = false)
    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
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

    @Column(length = 50, nullable = false)
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Column(length = 50, nullable = false)
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Column(length = 50)
    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    @ManyToOne
    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
        this.from = EmailUtil.getFriendlyEmail(fromUser);
    }

    @ManyToOne
    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
        this.to = EmailUtil.getFriendlyEmail(toUser);
    }

    @ManyToOne
    public User getReplyToUser() {
        return replyToUser;
    }

    public void setReplyToUser(User replyToUser) {
        this.replyToUser = replyToUser;
        this.replyTo = EmailUtil.getFriendlyEmail(replyToUser);
    }

    @Column(length = 200)
    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    @Column(length = 200)
    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    @Column(length = 200)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Lob
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @ManyToOne
    public Mail getReferrer() {
        return referrer;
    }

    public void setReferrer(Mail referrer) {
        this.referrer = referrer;
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
