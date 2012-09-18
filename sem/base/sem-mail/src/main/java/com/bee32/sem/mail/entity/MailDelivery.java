package com.bee32.sem.mail.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.color.Pink;
import com.bee32.sem.mail.MailFlags;

/**
 * 邮件分发
 *
 * 分发邮件副本到相关用户。
 */
@Entity
@Pink
@SequenceGenerator(name = "idgen", sequenceName = "mail_delivery_seq", allocationSize = 1)
public class MailDelivery
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int SENDERROR_LENGTH = 200;

    Mail mail;
    MailOrientation orientation = MailOrientation.FROM;
    MailFolder folder;
    String sendError;
    public final MailFlags flags = new MailFlags();

    public MailDelivery() {
    }

    public MailDelivery(Mail mail, MailOrientation orientation) {
        this.mail = mail;
        this.orientation = orientation;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof MailDelivery)
            _populate((MailDelivery) source);
        else
            super.populate(source);
    }

    protected void _populate(MailDelivery o) {
        super._populate(o);
        mail = o.mail;
        orientation = o.orientation;
        folder = o.folder;
        sendError = o.sendError;
        flags.bits = o.flags.bits;
    }

    /**
     * 邮件
     *
     * 引用要分发的邮件。
     */
    @ManyToOne(fetch = FetchType.LAZY)
    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    /**
     * 方向
     *
     * 邮件的分发方向。
     */
    @Transient
    public MailOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(MailOrientation orientation) {
        if (orientation == null)
            throw new NullPointerException("orientation");
        this.orientation = orientation;
    }

    @Basic(optional = false)
    @Column(name = "orientation", nullable = false)
    int get_orientation() {
        return orientation.getValue();
    }

    void set_orientation(int _orientation) {
        orientation = MailOrientation.forValue(_orientation);
    }

    /**
     * 邮件夹
     *
     * 目标文件夹。
     */
    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    public MailFolder getFolder() {
        return folder;
    }

    public void setFolder(MailFolder folder) {
        this.folder = folder;
    }

    /**
     * 分发标志
     *
     * 描述分发特征的标志位组。
     */
    @Column(name = "flags", nullable = false)
    public int getFlags() {
        return flags.bits;
    }

    public void setFlags(int flags) {
        this.flags.bits = flags;
    }

    /**
     * 发送错误
     *
     * 记录发送相关的错误消息。
     */
    @Column(length = SENDERROR_LENGTH)
    public String getSendError() {
        return sendError;
    }

    public void setSendError(String sendError) {
        this.sendError = sendError;
    }

}
