package com.bee32.sem.mail.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.mail.MailType;
import com.bee32.sem.mail.util.EmailUtil;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Mail
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    MailType type;

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

    @Basic(optional = false)
    @Column(nullable = false)
    public MailType getType() {
        return type;
    }

    public void setType(MailType type) {
        this.type = type;
    }

    @Column(length = 40)
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
        this.from = EmailUtil.getFriendlyEmail(fromUser);
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
        this.to = EmailUtil.getFriendlyEmail(toUser);
    }

    public User getReplyToUser() {
        return replyToUser;
    }

    public void setReplyToUser(User replyToUser) {
        this.replyToUser = replyToUser;
        this.replyTo = EmailUtil.getFriendlyEmail(replyToUser);
    }

    @Column(length = 1000)
    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    @Column(length = 1000)
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

}
