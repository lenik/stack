package com.bee32.zebra.tk.stat.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.bodz.bas.db.ibatis.IMapper;

import com.bee32.zebra.tk.stat.MonthTrends;

public interface MonthTrendsMapper
        extends IMapper {

    List<MonthTrends> all(@Param("table") String table, @Param("field") String groupField);

    List<MonthTrends> filter(MonthTrendsCriteria criteria);

    List<MonthTrends> sum1(@Param("table") String table, @Param("field") String groupField,
            @Param("field_1") String field1);

}
