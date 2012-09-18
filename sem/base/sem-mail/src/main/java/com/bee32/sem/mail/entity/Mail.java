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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.User;
import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.color.Green;
import com.bee32.sem.mail.MailPriority;
import com.bee32.sem.mail.MailType;
import com.bee32.sem.mail.util.EmailUtil;

/**
 * 邮件
 *
 * 可用于用户、系统、E-mail间转发的消息。
 */
@Entity
@Green
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("MAIL")
@SequenceGenerator(name = "idgen", sequenceName = "mail_seq", allocationSize = 1)
public class Mail
        extends CEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int FROM_LENGTH = 40;
    public static final int RECIPIENT_LENGTH = 150;
    public static final int REPLY_TO_LENGTH = 150;
    public static final int CC_LENGTH = 200;
    public static final int BCC_LENGTH = 200;
    public static final int SUBJECT_LENGTH = 200;
    public static final int BODY_LENGTH = 100000;

    MailType type = MailType.USER;
    MailPriority priority = MailPriority.NORMAL;

    String from;
    String recipient;
    String replyTo;

    User fromUser;
    User replyToUser;
    List<User> recipientUsers = new ArrayList<User>();

    String cc;
    String bcc;

    String subject;
    String body;

    int theme;
    int footer;

    Mail referrer;

    List<MailDelivery> deliveries = new ArrayList<MailDelivery>();

    @Override
    public void populate(Object source) {
        if (source instanceof Mail)
            _populate((Mail) source);
        else
            super.populate(source);
    }

    protected void _populate(Mail o) {
        super._populate(o);
        type = o.type;
        priority = o.priority;
        from = o.from;
        recipient = o.recipient;
        replyTo = o.replyTo;
        fromUser = o.fromUser;
        replyToUser = o.replyToUser;
        recipientUsers = new ArrayList<User>(o.recipientUsers);
        cc = o.cc;
        bcc = o.bcc;
        subject = o.subject;
        body = o.body;
        theme = o.theme;
        footer = o.footer;
        referrer = o.referrer;
        deliveries = new ArrayList<MailDelivery>(o.deliveries);
    }

    /**
     * 邮件类型
     *
     * 如：公文、帖子、系统广播、E-Mail等。
     */
    @Transient
    public MailType getType() {
        return type;
    }

    public void setType(MailType type) {
        this.type = type;
    }

    @Basic(optional = false)
    @Column(name = "type", nullable = false)
    int get_type() {
        return type.getValue();
    }

    void set_type(int _type) {
        type = MailType.forValue(_type);
    }

    /**
     * 优先级
     *
     * 一般用于排序。 E-mail中会形成相应的 Priority 域。
     */
    @Transient
    public MailPriority getPriority() {
        return priority;
    }

    public void setPriority(MailPriority priority) {
        if (priority == null)
            throw new NullPointerException("priority");
        this.priority = priority;
    }

    @Column(name = "priority", nullable = false)
    int get_priority() {
        return priority.getValue();
    }

    void set_priority(int _priority) {
        this.priority = MailPriority.forValue(_priority);
    }

    /**
     * 发送方
     *
     * 可能是 E-mail，用户名等，具体指涉对象由系统内部转换。
     */
    @Column(length = FROM_LENGTH)
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 接收方
     *
     * 可能是 E-mail，用户名等，具体指涉对象由系统内部转换。
     */
    @Column(length = RECIPIENT_LENGTH)
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * 回复到方
     *
     * 如 E-mail，用户名等，具体指涉对象由系统内部转换。（可以不同于发送方）。
     */
    @Column(length = REPLY_TO_LENGTH)
    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    /**
     * 发送方用户
     *
     * 当发送方为本系统中的登录用户。
     */
    @ManyToOne
    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
        this.from = EmailUtil.getFriendlyEmail(fromUser);
    }

    /**
     * 接收方用户
     *
     * 当接受方为本系统中的登录用户。
     */
    @ManyToMany
    @JoinTable(name = "MailRecipient", //
    /*            */joinColumns = @JoinColumn(name = "mail"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "recipient"))
    public List<User> getRecipientUsers() {
        return recipientUsers;
    }

    public void setRecipientUsers(List<User> recipientUsers) {
        if (recipientUsers == null)
            throw new NullPointerException("recipientUsers");
        this.recipientUsers = recipientUsers;
    }

    public boolean addRecipientUser(User recipientUser) {
        if (recipientUser == null)
            throw new NullPointerException("recipientUser");
        if (!recipientUsers.contains(recipientUser)) {
            recipientUsers.add(recipientUser);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeRecipientUser(User recipientUser) {
        if (recipientUser == null)
            throw new NullPointerException("recipientUser");
        return recipientUsers.remove(recipientUser);
    }

    /**
     * 回复到方用户
     *
     * 当回复到方为本系统中的登录用户。
     */
    @ManyToOne
    public User getReplyToUser() {
        return replyToUser;
    }

    public void setReplyToUser(User replyToUser) {
        this.replyToUser = replyToUser;
        this.replyTo = EmailUtil.getFriendlyEmail(replyToUser);
    }

    /**
     * 抄送列表
     *
     * 要抄送的用户地址列表。
     */
    @Column(length = CC_LENGTH)
    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * 秘密抄送列表。
     */
    @Column(length = BCC_LENGTH)
    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    /**
     * 主题
     *
     * 邮件的标题。
     */
    @Column(length = SUBJECT_LENGTH)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 正文
     *
     * 邮件的主要内容。
     */
    @Column(length = BODY_LENGTH, nullable = false)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 引用
     *
     * 引用的邮件（如回复、转发的邮件）。
     */
    @ManyToOne
    public Mail getReferrer() {
        return referrer;
    }

    public void setReferrer(Mail referrer) {
        this.referrer = referrer;
    }

    /**
     * 递送列表
     *
     * 邮件递送副本列表。
     */
    @OneToMany(mappedBy = "mail", orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
    public List<MailDelivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<MailDelivery> deliveries) {
        if (deliveries == null)
            throw new NullPointerException("deliveries");
        this.deliveries = deliveries;
    }

}
