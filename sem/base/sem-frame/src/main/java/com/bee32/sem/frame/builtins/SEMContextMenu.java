package com.bee32.sem.frame.builtins;

import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMContextMenu
        extends MenuComposite {

    /**
     * 上下文菜单
     *
     * <p lang="en">
     * Context Menu
     */
    public MenuNode CONTEXT = menu("context");

    /**
     * 文件
     *
     * <p lang="en">
     * File File
     */
    public MenuNode FILE = menu(CONTEXT, 10, "file");

    /**
     * 视图
     *
     * <p lang="en">
     * View
     */
    public MenuNode VIEW = menu(CONTEXT, 10, "view");

    /**
     * 业务
     *
     * <p lang="en">
     * Business
     */
    public MenuNode BIZ = menu(CONTEXT, 10, "biz");

    /**
     * 网络
     *
     * <p lang="en">
     * Network
     */
    public MenuNode NETWORK = menu(CONTEXT, 10, "network");

    /**
     * 选项
     *
     * <p lang="en">
     * Options
     */
    public MenuNode OPTIONS = menu(CONTEXT, 10, "options");

    /**
     * 帮助
     *
     * <p lang="en">
     * Help
     */
    public MenuNode HELP = menu(CONTEXT, 10, "help");

    /**
     * 读取
     *
     * <p lang="en">
     * Read
     */
    public MenuNode FILE_READ = section(FILE, 10, ":read");

    /**
     * 写入
     *
     * <p lang="en">
     * Write
     */
    public MenuNode FILE_WRITE = section(FILE, 20, ":write");

    /**
     * 属性
     *
     * <p lang="en">
     * Attributes
     */
    public MenuNode FILE_ATTR = section(FILE, 30, ":attr");

    /**
     * 日志
     *
     * <p lang="en">
     * Logs
     */
    public MenuNode FILE_LOG = section(FILE, 40, ":log");

    /**
     * 风格
     *
     * <p lang="en">
     * Style
     */
    public MenuNode VIEW_STYLE = section(VIEW, 10, ":style");

    /**
     * 布局
     *
     * <p lang="en">
     * Layout
     */
    public MenuNode VIEW_LAYOUT = section(VIEW, 20, ":layout");

    /**
     * 语言
     *
     * <p lang="en">
     * Language
     */
    public MenuNode VIEW_LANG = section(VIEW, 30, ":lang");

    /**
     * 业票
     *
     * <p lang="en">
     * Ticket
     */
    public MenuNode BIZ_TICKET = section(BIZ, 10, ":ticket");

    /**
     * 参考
     *
     * <p lang="en">
     * References
     */
    public MenuNode BIZ_REF = section(BIZ, 20, ":ref");

    /**
     * 上层业务
     *
     * <p lang="en">
     * Parent
     */
    public MenuNode BIZ_PARENT = section(BIZ, 30, ":parent");

    /**
     * 下层业务
     *
     * <p lang="en">
     * Children
     */
    public MenuNode BIZ_CHILDREN = section(BIZ, 40, ":children");

    /**
     * 评论
     *
     * <p lang="en">
     * Comments
     */
    public MenuNode BIZ_COMMENTS = section(BIZ, 50, ":comments");

    /**
     * 属性
     *
     * <p lang="en">
     * Attributes
     */
    public MenuNode BIZ_ATTR = section(BIZ, 60, ":bizattr");

    /**
     * 发送
     *
     * <p lang="en">
     * Send
     */
    public MenuNode NET_SEND = section(NETWORK, 10, ":send");

    /**
     * 转发
     *
     * <p lang="en">
     * Forward
     */
    public MenuNode NET_FORWARD = section(NETWORK, 20, ":forward");

    /**
     * 搜索
     *
     * <p lang="en">
     * Search
     */
    public MenuNode NET_SEARCH = section(NETWORK, 30, ":search");

}
