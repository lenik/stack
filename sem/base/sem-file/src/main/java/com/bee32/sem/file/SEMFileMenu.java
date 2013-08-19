package com.bee32.sem.file;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

/**
 * SEM 文件菜单
 *
 * <p lang="en">
 * SEM File Menu
 */
public class SEMFileMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMFileModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /**
     * 文件
     *
     * <p lang="en">
     * File
     */
    MenuNode FILE = menu(_frame_.OA, 10, "FILE");

    /**
     * 文件分类管理
     *
     * <p lang="en">
     * User Folder
     */
    /**/MenuNode folder = entry(FILE, 200, "folder", __.join("folder/"));

    /**
     * 文件管理
     *
     * <p lang="en">
     * Browser
     */
    /**/MenuNode userFile = entry(FILE, 300, "userFile", __.join("file/"));

    /**
     * 设置
     *
     * <p lang="en">
     * Settings
     */
    /**/MenuNode SETTINGS = menu(FILE, 1000, "SETTINGS");

    /**
     * 文件标签
     *
     * <p lang="en">
     * File Tag
     */
    /*    */MenuNode fileTag = entry(SETTINGS, 10, "fileTag", __.join("fileTag/"));

}
