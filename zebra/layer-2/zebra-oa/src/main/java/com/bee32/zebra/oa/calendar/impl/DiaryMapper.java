package com.bee32.zebra.oa.calendar.impl;

import java.util.List;

import com.bee32.zebra.oa.calendar.Diary;
import com.bee32.zebra.tk.sql.FooMapper;
import com.bee32.zebra.tk.util.F_DateCount;
import com.bee32.zebra.tk.util.F_Log;
import com.bee32.zebra.tk.util.F_YearCount;

public interface DiaryMapper
        extends FooMapper<Diary, DiaryCriteria> {

    List<F_YearCount> histoByYear();

    List<F_DateCount> logHisto(DiaryCriteria criteria);

    List<F_Log> log(DiaryCriteria criteria);

}
