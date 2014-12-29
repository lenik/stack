package com.bee32.zebra.oa.file.impl;

import java.util.List;

import net.bodz.bas.db.batis.IMapperTemplate;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.util.F_YearCount;

public interface FileInfoMapper
        extends IMapperTemplate<FileInfo, FileInfoCriteria> {

    List<F_YearCount> histoByYear();

}
