package com.bee32.sem.frame.menu;

import com.bee32.sem.frame.builtins.SEMFrameMenu;

public class MyMenu
        extends MenuComposite {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /**
     * 文件
     *
     * 文件管理
     *
     * <p lang="en">
     * File
     *
     * File operations
     */
    public MenuNode FILE = _frame_.SYSTEM;

    /**
     * 打开…
     *
     * 浏览并打开一个文件。
     *
     * <p lang="en">
     * Open File
     *
     * Browse and open a file
     */
    MenuNode OPEN = entry(FILE, 10, "open", WEB_APP.join("file/open.do"));

    /**
     * 保存
     *
     * 保存当前打开的文件。
     *
     * <p lang="en">
     * Save File
     *
     * Save the currently opened file.
     */
    MenuNode SAVE = entry(FILE, 20, "save", WEB_APP.join("file/save.do"));

    /**
     * 关闭
     *
     * 关闭当前文件。
     *
     * <p lang="en">
     * Close File
     *
     * Close the current file.
     */
    MenuNode CLOSE = entry(FILE, 30, "close", WEB_APP.join("file/close.do"));

}