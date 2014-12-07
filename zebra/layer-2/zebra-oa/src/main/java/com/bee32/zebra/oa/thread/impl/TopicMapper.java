package com.bee32.zebra.oa.thread.impl;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import net.bodz.bas.db.batis.IMapper;

import com.bee32.zebra.oa.thread.Topic;

public interface TopicMapper
        extends IMapper {

    List<Topic> all();

    List<Topic> filter(TopicCriteria range);

    Topic select(int id);

    int insert(Topic topic);

    void update(Topic topic);

    @Delete("delete from topic where id=#{id}")
    boolean delete(int id);

}
