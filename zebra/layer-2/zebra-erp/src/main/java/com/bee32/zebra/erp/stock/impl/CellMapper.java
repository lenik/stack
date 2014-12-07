package com.bee32.zebra.erp.stock.impl;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import net.bodz.bas.db.batis.IMapper;

import com.bee32.zebra.erp.stock.Cell;

public interface CellMapper
        extends IMapper {

    List<Cell> all();

    Cell select(int id);

    int insert(Cell cell);

    void update(Cell cell);

    @Delete("delete from cell where id=#{id}")
    boolean delete(int id);

}
