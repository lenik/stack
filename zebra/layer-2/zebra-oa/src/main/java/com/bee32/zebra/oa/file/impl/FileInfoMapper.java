package com.bee32.zebra.oa.file.impl;

import java.util.List;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.sql.FooMapper;
import com.bee32.zebra.tk.util.F_YearCount;

public interface FileInfoMapper
        extends FooMapper<FileInfo, FileInfoCriteria> {

    List<F_YearCount> histoByYear();

}
