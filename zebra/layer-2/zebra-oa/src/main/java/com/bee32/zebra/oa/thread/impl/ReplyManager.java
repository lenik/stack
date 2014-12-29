package com.bee32.zebra.oa.thread.impl;

import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.thread.Reply;
import com.bee32.zebra.tk.sea.FooManager;

/**
 * 回复<br>
 * 消息的回复信息。
 * 
 * @label 回复
 * 
 * @rel tag/: 管理标签
 */
public class ReplyManager
        extends FooManager {

    public ReplyManager(IQueryable context) {
        super(Reply.class, context);
    }

}
