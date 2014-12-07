package com.bee32.zebra.erp.stock.impl;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import net.bodz.bas.db.batis.IMapper;

import com.bee32.zebra.erp.stock.Warehouse;

public interface WarehouseMapper
        extends IMapper {

    List<Warehouse> all();

    Warehouse select(int id);

    int insert(Warehouse warehouse);

    void update(Warehouse warehouse);

    @Delete("delete from warehouse where id=#{id}")
    boolean delete(int id);

}
