package com.bee32.zebra.oa.contact.impl;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import net.bodz.bas.db.batis.IMapper;

import com.bee32.zebra.oa.contact.OrgUnit;

public interface OrgUnitMapper
        extends IMapper {

    List<OrgUnit> all();

    OrgUnit select(int id);

    int insert(OrgUnit orgUnit);

    void update(OrgUnit orgUnit);

    @Delete("delete from orgunit where id=#{id}")
    boolean delete(int id);

}
