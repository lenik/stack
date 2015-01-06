package com.bee32.zebra.tk.sql;

import org.apache.ibatis.annotations.Param;

import net.bodz.bas.db.batis.IMapper;

import com.bee32.zebra.tk.util.PrevNext;

public interface FnMapper
        extends IMapper {

    PrevNext prevNext(@Param("schemaName") String schema, @Param("tableName") String table, @Param("id") long id);

}
