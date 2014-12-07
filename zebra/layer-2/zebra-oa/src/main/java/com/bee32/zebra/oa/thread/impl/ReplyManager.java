package com.bee32.zebra.oa.thread.impl;

import com.bee32.zebra.tk.foo.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

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

    ReplyMapper mapper;

    public ReplyManager() {
        mapper = VhostDataService.getInstance().getMapper(ReplyMapper.class);
    }

    public ReplyMapper getMapper() {
        return mapper;
    }

}
