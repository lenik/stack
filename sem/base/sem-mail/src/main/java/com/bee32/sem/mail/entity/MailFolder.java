package com.bee32.sem.mail.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;

@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "mail_folder_seq", allocationSize = 1)
public class MailFolder
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final byte PRIORITY_HIGH = 1;
    public static final byte PRIORITY_NORMAL = 5;
    public static final byte PRIORITY_LOW = 9;

    protected byte priority = PRIORITY_NORMAL;

    boolean shared;
    int order = 100;

    int color;

    List<MailDelivery> mails;

    public MailFolder() {
    }

    public MailFolder(int order, String label) {
        this.shared = true;
        this.order = order;
        setLabel(label);
    }

    @Column(nullable = false)
    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    @Column(nullable = false)
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @OneToMany(mappedBy = "folder")
    @Cascade({ CascadeType.ALL })
    public List<MailDelivery> getMails() {
        return mails;
    }

    public void setMails(List<MailDelivery> mails) {
        this.mails = mails;
    }

    public static MailFolder INBOX = new MailFolder(0, "收件箱");
    public static MailFolder OUTBOX = new MailFolder(1, "发件箱");
    public static MailFolder DRAFT = new MailFolder(2, "草稿");
    public static MailFolder FAVORITES = new MailFolder(3, "收藏夹");
    public static MailFolder SPAMBOX = new MailFolder(4, "垃圾邮件");
    public static MailFolder TRASH = new MailFolder(5, "回收站");

}
