package com.bee32.zebra.oa.thread.impl;

import java.util.List;

import net.bodz.bas.db.batis.IMapperTemplate;

import com.bee32.zebra.oa.thread.Topic;

public interface TopicMapper
        extends IMapperTemplate<Topic, TopicCriteria> {

    List<F_YearCount> histoByYear();

}
