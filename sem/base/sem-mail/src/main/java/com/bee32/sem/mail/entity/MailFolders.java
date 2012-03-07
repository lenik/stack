package com.bee32.sem.mail.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class MailFolders
        extends StandardSamples {

    public MailFolder INBOX = new MailFolder(0, "收件箱");
    public MailFolder OUTBOX = new MailFolder(1, "发件箱");
    public MailFolder DRAFT = new MailFolder(2, "草稿");
    public MailFolder FAVORITES = new MailFolder(3, "收藏夹");
    public MailFolder SPAMBOX = new MailFolder(4, "垃圾邮件");
    public MailFolder TRASH = new MailFolder(5, "回收站");

}
