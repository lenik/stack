package com.bee32.zebra.oa.calendar.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.calendar.Diary;
import com.bee32.zebra.tk.sea.FooIndex;

/**
 * 用于一般日志记录。
 * 
 * @label 日记
 * 
 * @rel topic/: 管理项目/机会
 */
@ObjectType(Diary.class)
public class DiaryIndex
        extends FooIndex {

    public DiaryIndex(IQueryable context) {
        super(context);
    }

}
