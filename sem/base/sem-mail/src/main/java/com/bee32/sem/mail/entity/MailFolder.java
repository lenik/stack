package com.bee32.sem.mail.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 邮箱文件夹
 *
 * 用于邮件分类的文件夹。
 *
 * <p lang="en">
 * Mail Folder
 */
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

    @Override
    public void populate(Object source) {
        if (source instanceof MailFolder)
            _populate((MailFolder) source);
        else
            super.populate(source);
    }

    protected void _populate(MailFolder o) {
        super._populate(o);
        shared = o.shared;
        order = o.order;
        color = o.color;
        mails = new ArrayList<MailDelivery>(o.mails);
    }

    /**
     * 优先级
     *
     * 说明此文件夹下所有邮件的优先级。
     */
    @Column(nullable = false)
    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    /**
     * 次序
     *
     * 用于对文件夹排序。
     */
    @Column(nullable = false)
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 颜色
     *
     * 文件夹的RGB颜色。
     */
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /**
     * 邮件集
     *
     * 文件夹下的邮件列表。
     */
    @OneToMany(mappedBy = "folder", orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
    public List<MailDelivery> getMails() {
        return mails;
    }

    public void setMails(List<MailDelivery> mails) {
        this.mails = mails;
    }

}
