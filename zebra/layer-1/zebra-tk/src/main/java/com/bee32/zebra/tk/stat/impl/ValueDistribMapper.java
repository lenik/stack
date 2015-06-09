package com.bee32.zebra.tk.stat.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.bodz.bas.db.ibatis.IMapper;

import com.bee32.zebra.tk.stat.ValueDistrib;

public interface ValueDistribMapper
        extends IMapper {

    List<ValueDistrib> userDistrib(@Param("table") String table);

    List<ValueDistrib> groupDistrib(@Param("table") String table);

    List<ValueDistrib> opDistrib(@Param("table") String table);

}
