package com.bee32.zebra.oa.thread.impl;

import java.util.List;

import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.sql.FooMapper;
import com.bee32.zebra.tk.util.F_YearCount;

public interface TopicMapper
        extends FooMapper<Topic, TopicCriteria> {

    List<F_YearCount> histoByYear();

    int replyCount(int topicId);

}
