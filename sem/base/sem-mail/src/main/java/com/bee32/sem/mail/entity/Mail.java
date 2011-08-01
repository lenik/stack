package com.bee32.sem.mail.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Green;
import com.bee32.sem.mail.MailType;
import com.bee32.sem.mail.util.EmailUtil;

@Entity
@Green
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("MAIL")
@SequenceGenerator(name = "idgen", sequenceName = "mail_seq", allocationSize = 1)
public class Mail
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    protected MailType type = MailType.USER;

    public static final byte PRIORITY_URGENT = 10;
    public static final byte PRIORITY_HIGH = 20;
    public static final byte PRIORITY_NORMAL = 30;
    public static final byte PRIORITY_LOW = 40;
    protected byte priority = PRIORITY_NORMAL;

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

    List<MailDelivery> deliveries = new ArrayList<MailDelivery>();

    /**
     * 邮件类型，如：公文、帖子、系统广播等。
     */
    @Basic(optional = false)
    @Column(nullable = false)
    public MailType getType() {
        return type;
    }

    /**
     * 邮件类型，如：公文、帖子、系统广播、E-Mail等。
     */
    public void setType(MailType type) {
        this.type = type;
    }

    /**
     * 优先级。一般用于排序。 E-mail中会形成相应的 Priority 域。
     */
    @Column(nullable = false)
    public byte getPriority() {
        return priority;
    }

    /**
     * 优先级。一般用于排序。E-mail中会形成相应的 Priority 域。
     */
    public void setPriority(byte priority) {
        this.priority = priority;
    }

    /**
     * 发送方。可能是 E-mail，用户名等，具体指涉对象由系统内部转换。
     */
    @Column(length = 50, nullable = false)
    public String getFrom() {
        return from;
    }

    /**
     * 发送方。可能是 E-mail，用户名等，具体指涉对象由系统内部转换。
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 接收方。可能是 E-mail，用户名等，具体指涉对象由系统内部转换。
     */
    @Column(length = 50, nullable = false)
    public String getTo() {
        return to;
    }

    /**
     * 接收方。可能是 E-mail，用户名等，具体指涉对象由系统内部转换。
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * 回复到方（可能不同于发送方）。可能是 E-mail，用户名等，具体指涉对象由系统内部转换。
     */
    @Column(length = 50)
    public String getReplyTo() {
        return replyTo;
    }

    /**
     * 回复到方（可能不同于发送方）。可能是 E-mail，用户名等，具体指涉对象由系统内部转换。
     */
    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    /**
     * 发送方用户。
     */
    @ManyToOne
    public User getFromUser() {
        return fromUser;
    }

    /**
     * 发送方用户。
     */
    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
        this.from = EmailUtil.getFriendlyEmail(fromUser);
    }

    /**
     * 接收方用户。
     */
    @ManyToOne
    public User getToUser() {
        return toUser;
    }

    /**
     * 接收方用户。
     */
    public void setToUser(User toUser) {
        this.toUser = toUser;
        this.to = EmailUtil.getFriendlyEmail(toUser);
    }

    /**
     * 回复到方用户。
     */
    @ManyToOne
    public User getReplyToUser() {
        return replyToUser;
    }

    /**
     * 回复到方用户。
     */
    public void setReplyToUser(User replyToUser) {
        this.replyToUser = replyToUser;
        this.replyTo = EmailUtil.getFriendlyEmail(replyToUser);
    }

    /**
     * 抄送列表。
     */
    @Column(length = 200)
    public String getCc() {
        return cc;
    }

    /**
     * 抄送列表。
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * 秘密抄送列表。
     */
    @Column(length = 200)
    public String getBcc() {
        return bcc;
    }

    /**
     * 秘密抄送列表。
     */
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    /**
     * 主题。
     */
    @Column(length = 200)
    public String getSubject() {
        return subject;
    }

    /**
     * 主题。
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 正文。
     */
    @Lob
    public String getBody() {
        return body;
    }

    /**
     * 正文。
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 引用的邮件（如回复、转发的邮件）。
     */
    @ManyToOne
    public Mail getReferrer() {
        return referrer;
    }

    /**
     * 引用的邮件（如回复、转发的邮件）。
     */
    public void setReferrer(Mail referrer) {
        this.referrer = referrer;
    }

    /**
     * 邮件递送副本列表。
     */
    @OneToMany(mappedBy = "mail")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<MailDelivery> getDeliveries() {
        return deliveries;
    }

    /**
     * 邮件递送副本列表。
     */
    public void setDeliveries(List<MailDelivery> deliveries) {
        if (deliveries == null)
            throw new NullPointerException("deliveries");
        this.deliveries = deliveries;
    }

}
