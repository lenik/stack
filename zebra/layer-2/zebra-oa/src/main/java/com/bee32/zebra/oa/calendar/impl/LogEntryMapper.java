package com.bee32.zebra.oa.calendar.impl;

import java.util.List;

import net.bodz.bas.db.ibatis.IMapper;

import com.bee32.zebra.oa.calendar.LogEntry;

public interface LogEntryMapper
        extends IMapper {

    List<LogEntry> all();

    List<LogEntry> filter(LogEntryCriteria criteria);

    List<Integer> years(LogEntryCriteria criteria);

}
