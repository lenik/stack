package com.bee32.zebra.oa.file.impl;

import java.util.List;

import net.bodz.lily.model.util.F_YearCount;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.sql.FooMapper;

public interface FileInfoMapper
        extends FooMapper<FileInfo, FileInfoMask> {

    List<F_YearCount> histoByYear();

}
