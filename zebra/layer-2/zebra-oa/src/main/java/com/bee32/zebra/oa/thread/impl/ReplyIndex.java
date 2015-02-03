package com.bee32.zebra.oa.thread.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.thread.Reply;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 回复<br>
 * 消息的回复信息。
 * 
 * @label 回复
 * 
 * @rel tag/: 管理标签
 */
@ObjectType(Reply.class)
public class ReplyIndex
        extends QuickIndex {

    public ReplyIndex(IQueryable context) {
        super(context);
    }

}
