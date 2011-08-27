package com.bee32.sem.mail.entity;

import java.io.Serializable;
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
import org.hibernate.annotations.NaturalId;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.ext.c.CEntityAuto;
import com.bee32.plover.orm.ext.color.Green;
import com.bee32.sem.mail.MailPriority;
import com.bee32.sem.mail.MailType;
import com.bee32.sem.mail.util.EmailUtil;

@Entity
@Green
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("MAIL")
@SequenceGenerator(name = "idgen", sequenceName = "mail_seq", allocationSize = 1)
public class Mail
        extends CEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    String serial;

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

    @NaturalId
    @Column(length = 30)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * 邮件类型，如：公文、帖子、系统广播等。
     */
    @Transient
    public MailType getType() {
        return type;
    }

    /**
     * 邮件类型，如：公文、帖子、系统广播、E-Mail等。
     */
    public void setType(MailType type) {
        this.type = type;
    }

    @Basic(optional = false)
    @Column(name = "type", nullable = false)
    int get_type() {
        return type.getValue();
    }

    void set_type(int _type) {
        type = MailType.valueOf(_type);
    }

    /**
     * 优先级。一般用于排序。 E-mail中会形成相应的 Priority 域。
     */
    @Transient
    public MailPriority getPriority() {
        return priority;
    }

    /**
     * 优先级。一般用于排序。E-mail中会形成相应的 Priority 域。
     */
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
        this.priority = MailPriority.valueOf(_priority);
    }

    /**
     * 发送方。可能是 E-mail，用户名等，具体指涉对象由系统内部转换。
     */
    @Column(length = 40)
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
    @Column(length = 150)
    public String getRecipient() {
        return recipient;
    }

    /**
     * 接收方。可能是 E-mail，用户名等，具体指涉对象由系统内部转换。
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * 回复到方（可能不同于发送方）。可能是 E-mail，用户名等，具体指涉对象由系统内部转换。
     */
    @Column(length = 150)
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
    @ManyToMany
    @JoinTable(name = "MailRecipient", //
    /*            */joinColumns = @JoinColumn(name = "mail"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "recipient"))
    public List<User> getRecipientUsers() {
        return recipientUsers;
    }

    /**
     * 接收方用户。
     */
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
    @Column(length = 100000, nullable = false)
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

    @Override
    protected Serializable naturalId() {
        if (serial == null)
            return new DummyId(this);
        else
            return serial;
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return new Equals(prefix + "serial", serial);
    }

}
