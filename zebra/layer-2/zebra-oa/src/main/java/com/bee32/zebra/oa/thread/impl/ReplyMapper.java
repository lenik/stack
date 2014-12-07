package com.bee32.zebra.oa.thread.impl;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import net.bodz.bas.db.batis.IMapper;

import com.bee32.zebra.oa.thread.Reply;

public interface ReplyMapper
        extends IMapper {

    List<Reply> all();

    List<Reply> filter(ReplyCriteria criteria);

    Reply select(int id);

    int insert(Reply reply);

    void update(Reply reply);

    @Delete("delete from reply where id=#{id}")
    boolean delete(int id);

}
