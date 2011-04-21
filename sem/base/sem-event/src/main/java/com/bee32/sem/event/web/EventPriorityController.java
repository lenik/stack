package com.bee32.sem.event.web;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.ext.dict.DictController;
import com.bee32.sem.event.SEMEventModule;
import com.bee32.sem.event.dao.EventPriorityDao;
import com.bee32.sem.event.entity.EventPriority;

@RequestMapping(EventPriorityController.PREFIX + "*")
public class EventPriorityController
        extends DictController<EventPriority, Integer> {

    public static final String PREFIX = SEMEventModule.PREFIX + "/priority/";

    @Inject
    EventPriorityDao eventPriorityDao;

    @Override
    protected void preamble(Map<String, Object> metaData) {
        metaData.put(ENTITY_TYPE_NAME, "事件优先级");
    }

}
