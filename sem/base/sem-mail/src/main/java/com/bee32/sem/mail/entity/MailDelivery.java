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

X-Population

    @ManyToOne(fetch = FetchType.LAZY)
    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

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
        orientation = MailOrientation.valueOf(_orientation);
    }

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
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

    @Column(length = SENDERROR_LENGTH)
    public String getSendError() {
        return sendError;
    }

    public void setSendError(String sendError) {
        this.sendError = sendError;
    }

}
