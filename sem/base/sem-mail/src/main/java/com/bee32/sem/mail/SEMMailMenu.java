package com.bee32.sem.mail;

import com.bee32.plover.rtx.location.ILocationContext;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

/**
 *
 *
 * <p lang="en">
 */
public class SEMMailMenu
        extends MenuComposite {

    static ILocationContext __ = WEB_APP.join(SEMMailModule.PREFIX + "/");
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /**
     * 邮件
     *
     * <p lang="en">
     * Mail
     */
    public MenuNode MAIL = menu(_frame_.OA, 20, "MAIL");

    /**
     * 设置
     *
     * <p lang="en">
     * Settings
     */
    public MenuNode SETTINGS = menu(MAIL, 100, "SETTINGS");

    /**
     * 文件夹
     *
     * <p lang="en">
     * Mail Folder
     */
    MenuNode folder = entry(SETTINGS, 10, "mailbox", __.join("folder/index.do"));

    /**
     * 邮件过滤器
     *
     * <p lang="en">
     * Mail Filter
     */
    MenuNode filter = entry(SETTINGS, 20, "filter", __.join("filter/index.do"));

    /**
     * 撰写
     *
     * <p lang="en">
     * Compose Mail
     */
    MenuNode compose = entry(MAIL, 0, "compose", __.join("mail/compose.do"));

    /**
     * 收件箱
     *
     * <p lang="en">
     * Inbox
     */
    MenuNode inbox = entry(MAIL, 100, "inbox", __.join("mailbox/inbox.do"));

    /**
     * 发件箱
     *
     * <p lang="en">
     * Outbox
     */
    MenuNode outbox = entry(MAIL, 200, "outbox", __.join("mailbox/outbox.do"));

    /**
     * 回收站
     *
     * <p lang="en">
     * Trash
     */
    MenuNode trash = entry(MAIL, 300, "trash", __.join("mailbox/trash.do"));

    @Override
    protected void preamble() {
        MAIL.setFlags(MenuNode.HIDDEN);
    }

}
