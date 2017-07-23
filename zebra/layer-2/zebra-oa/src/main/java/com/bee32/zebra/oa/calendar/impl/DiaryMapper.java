package com.bee32.zebra.oa.calendar.impl;

import java.util.List;

import net.bodz.lily.model.util.F_DateCount;
import net.bodz.lily.model.util.F_Log;
import net.bodz.lily.model.util.F_YearCount;

import com.bee32.zebra.oa.calendar.Diary;
import com.bee32.zebra.tk.sql.FooMapper;

public interface DiaryMapper
        extends FooMapper<Diary, DiaryMask> {

    List<F_YearCount> histoByYear();

    List<F_DateCount> logHisto(DiaryMask mask);

    List<F_Log> log(DiaryMask mask);

}
